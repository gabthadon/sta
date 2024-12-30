package com.softnet.sta.service.impl;

import com.softnet.sta.config.CloudinaryConfig;
import com.softnet.sta.dto.response.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
@Slf4j
public class FileUploadService {

    private final CloudinaryConfig cloudinaryConfig;


    public ApiResponse<String> uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return new ApiResponse<>(HttpStatus.OK.value(), "File is empty", null);
        }
        try {
            String fileUrl = cloudinaryConfig.uploadFile(file);
            return new ApiResponse<>(HttpStatus.OK.value(), "Image URL", fileUrl);
        } catch (Exception e) {
            log.info("Error::::{}", e.getMessage());
            return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "File upload error", null);
        }
    }
}
