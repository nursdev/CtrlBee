package kz.ctrlbee.handler;

import kz.ctrlbee.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.AlreadyBoundException;

@RestControllerAdvice
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


    @ExceptionHandler(PasswordNotDeclaredException.class)
    public ResponseEntity<?> handlePasswordNotDeclaredException(PasswordNotDeclaredException e){
        HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                e.getMessage(),
                HttpStatus.NOT_ACCEPTABLE.value()
        );
        return new ResponseEntity<>(apiExceptionResponse, httpStatus);
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> handleAlreadyExistsException(AlreadyExistsException e){
        HttpStatus httpStatus = HttpStatus.ALREADY_REPORTED;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                e.getMessage(),
                HttpStatus.ALREADY_REPORTED.value()
        );
        return new ResponseEntity<>(apiExceptionResponse, httpStatus);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e){
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value()
        );
        return new ResponseEntity<>(apiExceptionResponse, httpStatus);
    }

    @ExceptionHandler(UserInputException.class)
    public ResponseEntity<?> handleUserInputException(UserInputException e){
        HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                e.getMessage(),
                HttpStatus.NOT_ACCEPTABLE.value()
        );
        return new ResponseEntity<>(apiExceptionResponse, httpStatus);
    }



}