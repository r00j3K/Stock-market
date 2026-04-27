package com.github.r00j3k.simplified_stock_market.exceptions;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({StockNotFoundException.class, WalletNotFoundException.class})
    public ResponseEntity<Object> handleNotFound(RuntimeException e){
        return createResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({StockNotAvailableException.class, WrongTransactionTypeException.class})
    public ResponseEntity<Object> handleBadRequest(RuntimeException e){
        return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // for handling validation errors in DTOs
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException e){
        String detailedErrors = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(err -> err.getField() + " (" + err.getDefaultMessage() + ")")
            .collect(java.util.stream.Collectors.joining(", "));

        return createResponse(HttpStatus.BAD_REQUEST, "Validation failed: " + detailedErrors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
    }

    private ResponseEntity<Object> createResponse(HttpStatus status, String message){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}
