package dev.alejandro.jenkinsproject.exceptions;

public class OrderNotCreatedException extends RuntimeException {
    public OrderNotCreatedException(String message) {
        super(message);
    }
}
