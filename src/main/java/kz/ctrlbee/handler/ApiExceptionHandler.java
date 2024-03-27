package kz.ctrlbee.handler;

import kz.ctrlbee.exception.AuthenticationException;
import kz.ctrlbee.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(apiExceptionResponse, httpStatus);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e){
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(apiExceptionResponse, httpStatus);
    }


}