package br.com.gabrieudev.emporium.application.exceptions;

import java.time.LocalDateTime;

public class StandardException {
    private int status;
    private String details;
    private LocalDateTime timestamp;
    
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public StandardException(int status, String details, LocalDateTime timestamp) {
        this.status = status;
        this.details = details;
        this.timestamp = timestamp;
    }
}
