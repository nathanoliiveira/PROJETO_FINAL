package com.noliveira.projeto_final.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import feign.FeignException;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorMessage> handleFeignException(FeignException ex) {
        String message = ex.getMessage();
        String cleanMessage = extractMessage(message);

        HttpStatus status = getHttpStatus(ex.status());

        ErrorMessage errorMessage = new ErrorMessage(cleanMessage);

        return ResponseEntity.status(status).body(errorMessage);
    }

    private String extractMessage(String message) {
        int startIndex = message.indexOf("\"message\":") + 11;
        int endIndex = message.indexOf("\"", startIndex);
        
        if (startIndex != -1 && endIndex != -1) {
            return message.substring(startIndex, endIndex);
        }

        return message;
    }

    private HttpStatus getHttpStatus(int statusCode) {
        switch (statusCode) {
            case 404:
                return HttpStatus.NOT_FOUND;
            case 409:
                return HttpStatus.CONFLICT;
            case 400:
                return HttpStatus.BAD_REQUEST;
            case 500:
                return HttpStatus.INTERNAL_SERVER_ERROR;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR; 
        }
    }
}