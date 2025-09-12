package io.github.matheusplaza.fitlogtds.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> resourceNotFound(NotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(Instant.now(), HttpStatus.NOT_FOUND.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValid(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<FieldMessage> fieldMessages = fieldErrors.stream().map(fe ->
                new FieldMessage(fe.getField(), fe.getDefaultMessage())).toList();
        ErrorAPI err = new ErrorAPI(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validacao", fieldMessages);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY.value()).body(err);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Object>  duplicateResourceException(DuplicateResourceException e) {
        ErrorAPI err = new ErrorAPI(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(err);
    }

}
