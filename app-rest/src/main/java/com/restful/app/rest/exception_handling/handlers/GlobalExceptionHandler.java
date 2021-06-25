package com.restful.app.rest.exception_handling.handlers;

import com.restful.app.api.dto.ErrorDto;

import com.restful.app.api.exceptions.IncorrectDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.restful.app.rest")
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleIncorrectDataException(IncorrectDataException exception) {
        ErrorDto error = new ErrorDto();
        error.setInfo(exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        ErrorDto error = new ErrorDto();
        error.setInfo(exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
