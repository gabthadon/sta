package com.softnet.sta.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HeroSliderRequest {
    @NotBlank(message = "Header must be provided")
    private String header;
    @NotBlank(message = "Description must be provided")
    private String description;
    @NotBlank(message = "Image URL must be provided")
    private String imageUrl;
    @NotBlank(message = "Link text must be provided")
    private String linkText;
    @NotBlank(message = "Link URL must be provided")
    private String linkUrl;
}
