package com.sleeksys.app.anonymizer.error;

import com.sleeksys.app.anonymizer.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class ResponseEntityError {

    public String timestamp;
    public Integer status;
    public String error;
    public String message;
    public String path;

    public ResponseEntityError(HttpServletRequest servletRequest,
                               HttpStatus status,
                               Exception exception) {
        this.timestamp = LocalDateTime.now().toString();
        this.status = status.value();
        this.error = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        this.path = servletRequest.getServletPath();
    }

    public static ResponseEntity<?> getResponse(HttpServletRequest servletRequest, Exception exception) {
        if (exception instanceof ResourceNotFoundException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseEntityError(servletRequest, HttpStatus.NOT_FOUND, exception));
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseEntityError(servletRequest, HttpStatus.BAD_REQUEST, exception));
    }
}
