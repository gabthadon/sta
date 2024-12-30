package com.softnet.sta.service.interfaces;

import com.softnet.sta.dto.request.HeroSliderRequest;
import com.softnet.sta.dto.request.UpdateHeroSliderRequest;
import com.softnet.sta.dto.response.HeroSliderResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface HeroSliderService {
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    List<HeroSliderResponse> createHeroSlider(List<HeroSliderRequest> heroSliderRequests);
    List<HeroSliderResponse> getAllHeroSlider();
    HeroSliderResponse getHeroSliderById(Long id);
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    List<HeroSliderResponse> updateHeroSliderById(List<UpdateHeroSliderRequest> heroSliderRequests);
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    List<HeroSliderResponse> deleteHeroSliderById(Long id);


}
