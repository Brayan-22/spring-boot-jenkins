package dev.alejandro.jenkinsproject.advices;

import dev.alejandro.jenkinsproject.exceptions.OrderNotCreatedException;
import dev.alejandro.jenkinsproject.exceptions.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class OrderControllerAdivce {

    @ExceptionHandler(OrderNotCreatedException.class)
    public ResponseEntity<?> handleOrderNotCreatedException(OrderNotCreatedException ex) {
        log.error("Order not created", ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleOrderNotFoundException(OrderNotFoundException ex) {
        log.error("Order not found", ex);
        return ResponseEntity.notFound().build();
    }
}
