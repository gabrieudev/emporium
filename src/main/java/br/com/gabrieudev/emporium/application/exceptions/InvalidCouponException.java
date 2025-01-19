package br.com.gabrieudev.emporium.application.exceptions;

import java.time.LocalDateTime;

public class InvalidCouponException extends RuntimeException {
    public InvalidCouponException(String message) {
        super(message);
    }

    public StandardException toStandardException() {
        return new StandardException(409, this.getMessage(), LocalDateTime.now());
    }
}
