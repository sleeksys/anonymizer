package com.sleeksys.app.anonymizer.service;

import com.sleeksys.app.anonymizer.entity.Cell;
import com.sleeksys.app.anonymizer.entity.Token;
import com.sleeksys.app.anonymizer.repository.CellRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@AllArgsConstructor
public class CellService {

    private CellRepository cellRepository;
    private EntityService entityService;
    private TokenService tokenService;

    public Map<Integer, List<String>> findByToken(HttpSession session, String tokenValue) {
        Token token = this.tokenService.findByValue(session, tokenValue);
        Map<Integer, List<String>> map = new HashMap<>();

        this.entityService.findCells().forEach((cell -> {
            if (cell.getTokenId().equals(token.getId())) {
                Integer key = cell.getRowIndex();
                if (!map.containsKey(key)) {
                    List<String> list = new ArrayList<>();
                    list.add(cell.getText());
                    map.put(key, list);
                } else {
                    map.get(key).add(cell.getText());
                }
            }
        }));
        return map;
    }

    public Map<Integer, List<String>> insert(HttpSession session, String tokenValue, MultipartFile file) throws Exception {
        if (hasExcelFormat(file)) {
            Token token = this.tokenService.findByValue(session, tokenValue);
            List<Cell> cells = excelToList(file.getInputStream(), token.getId());
            cells.forEach(cell -> {
                this.cellRepository.save(cell);
            });
            return this.findByToken(session, tokenValue);
        }
        throw new Exception("Please upload an excel file!");
    }

    private boolean hasExcelFormat(MultipartFile file) {
        String excelApplicationType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        if (!excelApplicationType.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    private List<Cell> excelToList(InputStream is, Long tokenId) {
        List<Cell> cells = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Iterator<org.apache.poi.ss.usermodel.Cell> cellsInRow = currentRow.iterator();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    org.apache.poi.ss.usermodel.Cell sheetCell = cellsInRow.next();

                    Cell obj = new Cell();
                    obj.setTokenId(tokenId);
                    obj.setRowIndex(rowNumber);
                    obj.setColumnIndex(cellIdx);
                    obj.setText(sheetCell.getStringCellValue());

                    cells.add(obj);

                    cellIdx++;
                }
                rowNumber++;
            }
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }
        return cells;
    }
}
