package com.softnet.sta.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MaximumLimitException extends RuntimeException {
    private final int statusCode;

    public MaximumLimitException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
