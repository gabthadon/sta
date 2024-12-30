package com.softnet.sta.util;

import com.softnet.sta.dto.response.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponseUtil {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Successful", data);
    }

    public static ApiResponse<String> error(int status, String message) {
        return new ApiResponse<>(status, message, null);
    }
}
