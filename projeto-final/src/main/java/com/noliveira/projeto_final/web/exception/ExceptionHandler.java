package com.noliveira.projeto_final.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.noliveira.projeto_final.exception.NotCorrentistaException;
import com.noliveira.projeto_final.exception.NotFoundException;
import com.noliveira.projeto_final.exception.TelefoneUniqueViolationException;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleClientNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(new ErrorMessage(ex.getMessage()));
    }
    
    @org.springframework.web.bind.annotation.ExceptionHandler(TelefoneUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> handleTelefoneUniqueViolationException(TelefoneUniqueViolationException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }
    
    @org.springframework.web.bind.annotation.ExceptionHandler(NotCorrentistaException.class)
    public ResponseEntity<ErrorMessage> handleNotCorrentistaException(NotCorrentistaException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    
}
