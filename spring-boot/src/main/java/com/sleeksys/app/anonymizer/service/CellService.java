package com.sleeksys.app.anonymizer.service;

import com.sleeksys.app.anonymizer.entity.Cell;
import com.sleeksys.app.anonymizer.entity.Label;
import com.sleeksys.app.anonymizer.entity.SessionContext;
import com.sleeksys.app.anonymizer.enumeration.PrivacyLevel;
import com.sleeksys.app.anonymizer.repository.CellRepository;
import com.sleeksys.app.anonymizer.repository.LabelRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CellService {

    private Logger logger = LoggerFactory.getLogger(CellService.class);

    private CellRepository cellRepository;
    private LabelRepository labelRepository;

    private EntityService entityService;
    private SessionContextService sessionContextService;

    public Optional<Cell> findById(Long id) {
        return this.cellRepository.findById(id);
    }

    public List<Cell> findByContextId(String contextId) {
        return this.entityService.findCells()
                .stream()
                .filter(cell -> (cell.getContextId().equals(contextId)))
                .collect(Collectors.toList());
    }

    public List<Cell> findByRowIndex(String contextId, Integer rowIndex) {
        return this.findByContextId(contextId)
                .stream()
                .filter(cell -> (cell.getRowIndex() == rowIndex))
                .collect(Collectors.toList());
    }

    public List<Cell> findByColumnIndex(String contextId, Integer columnIndex) {
        return this.findByContextId(contextId)
                .stream()
                .filter(cell -> (cell.getColumnIndex() == columnIndex))
                .collect(Collectors.toList());
    }

    public Map<Integer, List<String>> findByContext(HttpSession session, String contextId) {
        SessionContext context = this.sessionContextService.findById(session, contextId);
        Map<Integer, List<String>> map = new HashMap<>();

        this.entityService.findCells().forEach((cell -> {
            if (cell.getContextId().equals(context.getContextId())) {
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
            List<Cell> cells = excelToList(file.getInputStream(), context.getContextId());
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

    public boolean anonymizeCellText(String contextId, Long cellId, PrivacyLevel privacyLevel) {
        if (privacyLevel == PrivacyLevel.HIGH || privacyLevel == PrivacyLevel.MEDIUM) {
            Optional<Cell> optional = this.findById(cellId);
            if (optional.isPresent()) {
                List<Cell> cellsInColumn = this.findByColumnIndex(contextId, optional.get().getColumnIndex());
                cellsInColumn.forEach(cell -> {
                    String text = cell.getText();

                    if (privacyLevel == PrivacyLevel.HIGH) {
                        text = "---";
                    } else {
                        text = (text.length() > 5) ? text.substring(0, 5) : "---";
                    }

                    // override cell's text
                    cell.setText(text);
                });
                return true;
            }
        }
        logger.warn("Nothing to anonymize.");
        return true;
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
