package org.example.exception;

public class BusServiceException extends RuntimeException {

    public BusServiceException(String message) {
        super(message);
    }
}
