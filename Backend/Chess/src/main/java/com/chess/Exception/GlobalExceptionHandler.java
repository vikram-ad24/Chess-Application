package com.chess.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ChessException.class)
    public ResponseEntity<ResponseDetails<String>> chessExceptionHandler(ChessException ex){

        ResponseDetails<String> details=new ResponseDetails<>();
        details.setMessage(ex.getMessage());
        details.setStatus(HttpStatus.BAD_REQUEST.value());


        return new ResponseEntity<>(details,HttpStatus.BAD_REQUEST);
    }
}
