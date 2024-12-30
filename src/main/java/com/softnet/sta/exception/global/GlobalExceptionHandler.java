package com.softnet.sta.exception.global;

import com.softnet.sta.dto.response.ApiResponse;
import com.softnet.sta.exception.DataIntegrityViolationException;
import com.softnet.sta.exception.InvalidRequestException;
import com.softnet.sta.exception.NotFoundException;
import com.softnet.sta.exception.UnauthorizedAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{


    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleServiceException(InvalidRequestException ex) {
        ApiResponse<String> response = new ApiResponse<>(ex.getStatusCode(), ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleServiceException(NotFoundException ex) {
        ApiResponse<String> response = new ApiResponse<>(ex.getStatusCode(), ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ApiResponse<String>> handleServiceException(UnauthorizedAccessException ex) {
        ApiResponse<String> response = new ApiResponse<>(ex.getStatusCode(), ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleServiceException(DataIntegrityViolationException ex) {
        ApiResponse<String> response = new ApiResponse<>(ex.getStatusCode(), ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getStatusCode()));
    }

    // This method handles field validation exceptions
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        Map<String, Object> fieldErrors = new HashMap<>();

        // Collect all field-specific validation errors
        List<Map<String, String>> errorsList = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "errorMessage", error.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        // Structure the error response
        fieldErrors.put("timestamp", Instant.now().atOffset(ZoneOffset.UTC).toString());
        fieldErrors.put("status", status.value());
        fieldErrors.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        fieldErrors.put("message", "Validation failure");
        fieldErrors.put("details", errorsList); // Include detailed validation error list
        fieldErrors.put("path", request.getDescription(false).split("=")[1]);

        return new ResponseEntity<>(fieldErrors, HttpStatus.BAD_REQUEST);
    }


    // Add more exception handlers as needed

}