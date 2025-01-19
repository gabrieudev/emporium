package br.com.gabrieudev.emporium.application.exceptions;

import java.time.LocalDateTime;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }

    public StandardException toStandardException() {
        return new StandardException(401, this.getMessage(), LocalDateTime.now());
    }
}
