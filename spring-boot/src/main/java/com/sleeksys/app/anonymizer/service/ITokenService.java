package com.sleeksys.app.anonymizer.service;

import com.sleeksys.app.anonymizer.entity.Token;
import com.sleeksys.app.anonymizer.exception.ResourceNotFoundException;

import javax.servlet.http.HttpSession;

public interface ITokenService {

    /*
     * Finds Token from database with the same value
     *
     * */
    public Token findByValue(HttpSession session, String value) throws ResourceNotFoundException;

    /*
     * Saves Token in the database
     *
     * */
    public String insert(HttpSession session);
}
