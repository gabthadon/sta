package com.softnet.sta.service.impl;

import com.softnet.sta.database.entity.LoginSlide;
import com.softnet.sta.dto.request.HeroSliderRequest;
import com.softnet.sta.dto.response.HeroSliderResponse;
import com.softnet.sta.repository.LoginSlideRepository;
import com.softnet.sta.service.interfaces.LoginSlideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginSlideServiceImpl implements LoginSlideService {
    private final ModelMapper modelMapper;
    private final LoginSlideRepository loginSlideRepository;



    @Override
    public List<HeroSliderResponse> createLoginSlide(List<HeroSliderRequest> loginSlideRequest) {

        List<LoginSlide> loginSlides = loginSlideRequest.stream()
                .map(req -> modelMapper.map(req, LoginSlide.class))
                .collect(Collectors.toList());

        List<LoginSlide> loginSlidesData = loginSlideRepository.saveAll(loginSlides);

        return loginSlidesData.stream()
                .map(loginSlide -> modelMapper.map(loginSlide, HeroSliderResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<HeroSliderResponse> getAllLoginSlides() {
        List<LoginSlide> loginSlides = loginSlideRepository.findAll();
        return loginSlides.stream()
                .map(slide -> modelMapper.map(slide, HeroSliderResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public HeroSliderResponse getLoginSlideById(Long id) {
        LoginSlide loginSlide = loginSlideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LoginSlide not found"));
        return modelMapper.map(loginSlide, HeroSliderResponse.class);
    }

    @Override
    public HeroSliderResponse updateLoginSlide(Long id, HeroSliderRequest loginSlideRequest) {
        LoginSlide loginSlide = loginSlideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LoginSlide not found"));

        // Update fields
        modelMapper.map(loginSlideRequest, loginSlide);

        LoginSlide updatedSlide = loginSlideRepository.save(loginSlide);
        return modelMapper.map(updatedSlide, HeroSliderResponse.class);
    }




    @Override
    public void deleteLoginSlide(Long id) {
        LoginSlide loginSlide = loginSlideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LoginSlide not found"));
        loginSlideRepository.delete(loginSlide);
    }


}
