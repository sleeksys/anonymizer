package com.sleeksys.app.anonymizer.service.Impl;

import com.sleeksys.app.anonymizer.entity.Cell;
import com.sleeksys.app.anonymizer.entity.Label;
import com.sleeksys.app.anonymizer.entity.Token;
import com.sleeksys.app.anonymizer.repository.CellRepository;
import com.sleeksys.app.anonymizer.service.ICellService;
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
public class CellService implements ICellService {

    private CellRepository cellRepository;
    private EntityService entityService;
    private TokenService tokenService;

    /*
     * Find Cell by Token
     *
     * */
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



    /*
     * Saves Cells in the database
     *
     * */
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

    public List<Cell> excelToList(InputStream is, Long tokenId) {
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
            is.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }
        return cells;
    }

    /*
    * Extracts Labels from Excel Sheet
    *
    * */
    public List<Label> extractLabelsFromExcelSheet(InputStream is, Long tokenId)  {

        // List to save the labels
        List<Label> labels = new ArrayList<>();

        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheetAt(0);

            // search for the first non empty row

            int firstRowIndex = sheet.getFirstRowNum();

            // search for the last non empty row
            int lastRowIndex = sheet.getLastRowNum();

            for (int rowIndex = firstRowIndex; rowIndex == firstRowIndex; rowIndex++) {

                // Get the column number of a row
                int colNumber = sheet.getRow(rowIndex).getPhysicalNumberOfCells();

                //Get the first column index of a row
                int firstColIndex = sheet.getRow(rowIndex).getFirstCellNum();

                while (colNumber > 0) {

                    String value = sheet.getRow(rowIndex).getCell(firstColIndex).getStringCellValue();

                    if (value != null) {

                        Label label = new Label(value, (long) firstColIndex);
                        List<Cell> cells = new ArrayList<>();

                        // Extract cells for every label
                        for(int rowCellIndex = sheet.getFirstRowNum()+1; rowCellIndex <= lastRowIndex; rowCellIndex++) {

                            String valueCell = sheet.getRow(rowCellIndex).getCell(Math.toIntExact(label.getCellId())).getStringCellValue();

                            if (valueCell != null) {
                                Cell cell = new Cell();
                                cell.setTokenId(tokenId);
                                cell.setRowIndex(rowCellIndex);
                                cell.setColumnIndex(Math.toIntExact(label.getCellId()));
                                cell.setText(valueCell);
                                cell.setLabel(label);
                                cells.add(cell);
                            }
                        }

                        label.setCells(cells);
                        labels.add(label);
                    }

                    firstColIndex++;

                    colNumber--;
                }

            }
            workbook.close();
            is.close();
        } catch (IOException e){

            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }

        return  labels;
    }

    /*
     * Extracts Cells from Excel Sheet without Labels
     *
     * */
    public List<Cell> extractDataFromExcelWithoutLabel(InputStream is, Long tokenId){

        List<Cell> cells = new ArrayList<>();

/*        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);

            int firstRowIndex = sheet.getFirstRowNum();

            int lastRowIndex = sheet.getLastRowNum();

            for (int rowIndex = firstRowIndex+1; rowIndex <= lastRowIndex; rowIndex++) {

                // Get the column number of a row
                int colNumber = sheet.getRow(rowIndex).getPhysicalNumberOfCells();

                //Get the first column index of a row
                int colIndex = sheet.getRow(rowIndex).getFirstCellNum();

                while (colNumber > 0) {

                    String value = sheet.getRow(rowIndex).getCell(colIndex).getStringCellValue();

                    if (value != null) {
                        Cell obj = new Cell();
                        obj.setTokenId(tokenId);
                        obj.setRowIndex(rowIndex);
                        obj.setColumnIndex(colIndex);
                        obj.setText(value);

                        cells.add(obj);
                    }

                    colIndex++;

                    colNumber--;
                }

            }

            workbook.close();
            is.close();
        }catch (IOException e){

            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }*/


        return cells;
    }

    /*
     * Anonymizes Data
     *
     * */
    public String anonymizeData(String data){
        String anonymizedData = "";


        return anonymizedData;
    }

    @Override
    public void saveCells(List<Cell> cells) {
        if(cells.size() >= 1){
            for (Cell cell: cells) {
                cellRepository.save(cell);
            }
        }
    }
}
