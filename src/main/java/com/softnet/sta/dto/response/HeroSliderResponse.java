package com.softnet.sta.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HeroSliderResponse {
    private Long id;
    private String header;
    private String description;
    private String imageUrl;
    private String linkText;
    private String linkUrl;
}
