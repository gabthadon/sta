package com.softnet.sta.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotFoundException extends RuntimeException {
    private final int statusCode;


    public NotFoundException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}