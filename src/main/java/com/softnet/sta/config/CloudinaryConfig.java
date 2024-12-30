package com.softnet.sta.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Configuration
@Slf4j
public class CloudinaryConfig {

    Cloudinary cloudinary;

    public CloudinaryConfig() {
        cloudinary = new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name","dbh9smpma",
                        "api_key", "992999421733238",
                        "api_secret", "mQ419nHl7KpS9yLEqy7iZKmB7nU"
                )
        );
    }

    private File convert(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public String uploadFile(MultipartFile file) {
        String photoUrl = "";
        Map uploadResult = null;

        try {
            File imageFile = convert(file);
            if (imageFile != null) {
                uploadResult = cloudinary.uploader().upload(imageFile, ObjectUtils.asMap("resource_type", "auto"));
                if (!uploadResult.isEmpty()) {
                    log.info("Result is not empty");
                    photoUrl = uploadResult.get("secure_url").toString();
                }  else {
                    log.info("Result is empty");
                }
            }
            return photoUrl;
        } catch (IOException e) {
            log.info("Cloudinary error:::::{}", e.getMessage());
            return "error Occurred While Uploading";
        }

    }
}
