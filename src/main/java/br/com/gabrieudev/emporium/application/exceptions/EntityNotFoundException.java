package br.com.gabrieudev.emporium.application.exceptions;

import java.time.LocalDateTime;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public StandardException toStandardException() {
        return new StandardException(404, this.getMessage(), LocalDateTime.now());
    }
}
