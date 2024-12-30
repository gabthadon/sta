package com.softnet.sta.dto.request;

import lombok.Data;

@Data
public class UpdateHeroSliderRequest {
    private Long id;
    private String header;
    private String description;
    private String imageUrl;
    private String linkText;
    private String linkUrl;
}
