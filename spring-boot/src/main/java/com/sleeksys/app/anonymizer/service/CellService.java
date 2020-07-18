package com.sleeksys.app.anonymizer.service;

import com.sleeksys.app.anonymizer.entity.Cell;
import com.sleeksys.app.anonymizer.entity.Label;
import com.sleeksys.app.anonymizer.entity.SessionContext;
import com.sleeksys.app.anonymizer.repository.CellRepository;
import com.sleeksys.app.anonymizer.repository.LabelRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
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
    private LabelRepository labelRepository;

    private EntityService entityService;
    private SessionContextService sessionContextService;

    public Optional<Cell> findById(Long id) {
        return this.cellRepository.findById(id);
    }

    public Map<Integer, List<String>> findByContext(HttpSession session, String contextId) {
        SessionContext context = this.sessionContextService.findById(session, contextId);
        Map<Integer, List<String>> map = new HashMap<>();

        this.entityService.findCells().forEach((cell -> {
            if (cell.getContextId().equals(context.getId())) {
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

    public ResponseEntity<?> download(HttpSession session, String contextId) {
        // this call throws an exception if context's id don't match session
        this.sessionContextService.findById(session, contextId);

        return null;
    }

    public List<Label> insert(HttpSession session, String contextId, MultipartFile file) throws Exception {
        if (hasExcelFormat(file)) {
            List<Label> labels = new ArrayList<>();
            SessionContext context = this.sessionContextService.findById(session, contextId);
            List<Cell> cells = excelToList(file.getInputStream(), context.getId());
            cells.forEach(cell -> {
                Cell tmp = this.cellRepository.save(cell);

                // add label
                if (cell.getRowIndex() == 0) {
                    Label label = this.labelRepository.save(new Label(tmp.getText(), tmp.getId()));
                    labels.add(label);
                }
            });
            return labels;
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

    private List<Cell> excelToList(InputStream is, String contextId) {
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
                    obj.setContextId(contextId);
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
