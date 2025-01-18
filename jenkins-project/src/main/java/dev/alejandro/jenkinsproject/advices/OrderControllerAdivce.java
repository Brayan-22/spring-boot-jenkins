package dev.alejandro.jenkinsproject.advices;

import dev.alejandro.jenkinsproject.exceptions.OrderNotCreatedException;
import dev.alejandro.jenkinsproject.exceptions.OrderNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderControllerAdivce {

    @ExceptionHandler(OrderNotCreatedException.class)
    public ResponseEntity<?> handleOrderNotCreatedException(OrderNotCreatedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleOrderNotFoundException(OrderNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}
