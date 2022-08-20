package com.devsuperior.bds04.api.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.devsuperior.bds04.exceptions.BadRequestException;
import com.devsuperior.bds04.exceptions.ObjectNotFoundException;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> handleBadRequestException(BadRequestException e, HttpServletRequest request) {
        return generateStandardError(HttpStatus.BAD_REQUEST, e.getClass().getSimpleName(), e.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> handleObjectNotFoundException(ObjectNotFoundException e,
            HttpServletRequest request) {
        return generateStandardError(HttpStatus.NOT_FOUND, e.getClass().getSimpleName(), e.getLocalizedMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
            HttpServletRequest request) {
        var error = getStandarError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation Error", null,
                request.getRequestURI());
        e.getFieldErrors()
                .forEach(fieldError -> error.addValidationError(fieldError.getField(), fieldError.getDefaultMessage()));
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    private StandardError getStandarError(Integer status, String error, String message, String path) {
        return new StandardError(status, error, message, path);
    }

    private ResponseEntity<StandardError> generateStandardError(HttpStatus status, String error, String message,
            String path) {
        var err = getStandarError(status.value(), error, message, path);
        return ResponseEntity.status(status).body(err);
    }

}
