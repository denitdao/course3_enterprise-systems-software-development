package ua.kpi.tef.essd.backend.exception;

import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Log4j2
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({WrongValueException.class, ResourceNotFoundException.class})
    public ResponseEntity<Object> createdException(RuntimeException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        log.warn(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> constraintException(ConstraintViolationException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("description", "Wrong field values");
        log.warn(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /*@ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> unknownException(DataIntegrityViolationException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("description", "Data is not acceptable");
        log.warn(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.FAILED_DEPENDENCY);
    }*/

    /*@ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> unknownException(RuntimeException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        log.warn(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.FAILED_DEPENDENCY);
    }*/
}
