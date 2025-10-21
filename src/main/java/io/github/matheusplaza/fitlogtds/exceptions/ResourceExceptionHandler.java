package io.github.matheusplaza.fitlogtds.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorAPI> resourceNotFound(NotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorAPI err = new ErrorAPI(status.value(), e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorAPI> methodArgumentNotValid(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<FieldMessage> fieldMessages = fieldErrors.stream().map(fe ->
                new FieldMessage(fe.getField(), fe.getDefaultMessage())).toList();
        ErrorAPI err = new ErrorAPI(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validacao", fieldMessages);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY.value()).body(err);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorAPI>  duplicateResourceException(DuplicateResourceException e) {
        ErrorAPI err = new ErrorAPI(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(err);
    }

}
