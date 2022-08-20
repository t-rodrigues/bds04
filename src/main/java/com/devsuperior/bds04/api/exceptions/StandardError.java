package com.devsuperior.bds04.api.exceptions;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    private List<ValidationError> errors = new ArrayList<>();

    public StandardError() {
    }

    public StandardError(Integer status, String error, String message, String path) {
        timestamp = Instant.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public static class ValidationError implements Serializable {
        private final String fieldName;
        private final String fieldMessage;

        public ValidationError(String fieldName, String fieldMessage) {
            this.fieldName = fieldName;
            this.fieldMessage = fieldMessage;
        }

        public String getfieldName() {
            return fieldName;
        }

        public String getMessage() {
            return fieldMessage;
        }
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public void addValidationError(String fieldName, String fieldMessage) {
        errors.add(new ValidationError(fieldName, fieldMessage));
    }
}
