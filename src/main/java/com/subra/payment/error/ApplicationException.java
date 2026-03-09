package com.subra.payment.error;

public class ApplicationException extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }
    public ApplicationException(String message, Exception e) {
        super(message, e);
    }
}
