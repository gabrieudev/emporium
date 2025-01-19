package br.com.gabrieudev.emporium.application.exceptions;

import java.time.LocalDateTime;

public class TransactionFailedException extends RuntimeException {
    public TransactionFailedException(String message) {
        super(message);
    }

    public StandardException toStandardException() {
        return new StandardException(409, this.getMessage(), LocalDateTime.now());
    }
}
