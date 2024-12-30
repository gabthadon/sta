package com.softnet.sta.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softnet.sta.database.entity.HeroSlider;
import com.softnet.sta.dto.request.HeroSliderRequest;
import com.softnet.sta.dto.request.UpdateHeroSliderRequest;
import com.softnet.sta.dto.response.HeroSliderResponse;
import com.softnet.sta.repository.HeroSliderRepository;
import com.softnet.sta.service.interfaces.HeroSliderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class HeroSliderServiceImpl implements HeroSliderService {

    private final HeroSliderRepository heroSliderRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;



    @Override
    public List<HeroSliderResponse> createHeroSlider(List<HeroSliderRequest> heroSliderRequests) {
        // Convert request objects to entity, save them and return the response objects
        List<HeroSlider> heroSliders = heroSliderRequests.stream()
                .map(request -> modelMapper.map(request, HeroSlider.class))
                .collect(Collectors.toList());

        List<HeroSlider> savedHeroSliders = heroSliderRepository.saveAll(heroSliders);

        return savedHeroSliders.stream()
                .map(savedHeroSlider -> modelMapper.map(savedHeroSlider, HeroSliderResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<HeroSliderResponse> getAllHeroSlider() {
        // Fetch all records from the repository and return as response objects
        List<HeroSlider> heroSliders = heroSliderRepository.findAll();

        return heroSliders.stream()
                .map(heroSlider -> modelMapper.map(heroSlider, HeroSliderResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public HeroSliderResponse getHeroSliderById(Long id) {
        // Fetch by ID and return response
        HeroSlider heroSlider = heroSliderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HeroSlider with id " + id + " not found"));

        return modelMapper.map(heroSlider, HeroSliderResponse.class);
    }

    @Override
    public List<HeroSliderResponse> updateHeroSliderById(List<UpdateHeroSliderRequest> heroSliderRequests) {
        // Update existing records
        List<HeroSlider> updatedHeroSliders = heroSliderRequests.stream()
                .map(request -> {
                    HeroSlider existingHeroSlider = heroSliderRepository.findById(request.getId())
                            .orElseThrow(() -> new RuntimeException("HeroSlider with id " + request.getId() + " not found"));

                    // Map the updated fields from request to existing entity
                    existingHeroSlider.setHeader(request.getHeader());
                    existingHeroSlider.setDescription(request.getDescription());
                    existingHeroSlider.setImageUrl(request.getImageUrl());
                    existingHeroSlider.setLinkText(request.getLinkText());
                    existingHeroSlider.setLinkUrl(request.getLinkUrl());

                    return existingHeroSlider;
                })
                .collect(Collectors.toList());

        List<HeroSlider> savedHeroSliders = heroSliderRepository.saveAll(updatedHeroSliders);

        return savedHeroSliders.stream()
                .map(heroSlider -> modelMapper.map(heroSlider, HeroSliderResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<HeroSliderResponse> deleteHeroSliderById(Long id) {
        // Delete by ID and return the remaining HeroSliders
        HeroSlider heroSlider = heroSliderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HeroSlider with id " + id + " not found"));

        heroSliderRepository.delete(heroSlider);

        List<HeroSlider> remainingHeroSliders = heroSliderRepository.findAll();
        return remainingHeroSliders.stream()
                .map(remainingHeroSlider -> modelMapper.map(remainingHeroSlider, HeroSliderResponse.class))
                .collect(Collectors.toList());
    }
}
