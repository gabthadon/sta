package com.softnet.sta.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UnauthorizedAccessException extends RuntimeException {
    private final int statusCode;


    public UnauthorizedAccessException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}