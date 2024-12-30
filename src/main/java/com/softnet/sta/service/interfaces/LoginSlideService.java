package com.softnet.sta.service.interfaces;

import com.softnet.sta.dto.request.HeroSliderRequest;
import com.softnet.sta.dto.response.HeroSliderResponse;

import java.util.List;

public interface LoginSlideService {
    List<HeroSliderResponse> createLoginSlide(List<HeroSliderRequest> loginSlideRequest);

    List<HeroSliderResponse> getAllLoginSlides();

    HeroSliderResponse getLoginSlideById(Long id);

    HeroSliderResponse updateLoginSlide(Long id, HeroSliderRequest heroSliderRequest);

    void deleteLoginSlide(Long id);
}
