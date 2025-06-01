package com.borisov.DZ_4.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserErrorResponse {
    @Schema(description = "Сообщение об ошибке", example = "Error")
    private String message;
    @Schema(description = "Timestamp", example = "1748252820103")
    private long timestamp;

    public UserErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
