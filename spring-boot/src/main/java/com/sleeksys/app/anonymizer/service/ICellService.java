package com.sleeksys.app.anonymizer.service;

import com.sleeksys.app.anonymizer.entity.Cell;
import com.sleeksys.app.anonymizer.entity.Label;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ICellService {

    /*
     * Find Cell by Token
     *
     * */
    public Map<Integer, List<String>> findByToken(HttpSession session, String tokenValue);

    /*
     * Saves Cells in the database
     *
     * */
    public Map<Integer, List<String>> insert(HttpSession session, String tokenValue, MultipartFile file) throws Exception;

    public List<Cell> excelToList(InputStream is, Long tokenId);


    /*
     * Extracts Labels from Excel Sheet
     *
     * */
    public List<Label> extractLabelsFromExcelSheet(InputStream is, Long tokenId);


    /*
     * Extracts Data from Excel Sheet without Labels
     *
     * */
    public List<Cell> extractDataFromExcelWithoutLabel(InputStream is, Long tokenId);

    /*
     * Anonymize Data
     *
     * */
    public String anonymizeData(String data);

    public void saveCells(List<Cell> cells);

}
