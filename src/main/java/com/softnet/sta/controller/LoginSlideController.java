package com.softnet.sta.controller;

import com.softnet.sta.dto.request.HeroSliderRequest;
import com.softnet.sta.dto.response.ApiResponse;
import com.softnet.sta.dto.response.HeroSliderResponse;
import com.softnet.sta.service.interfaces.LoginSlideService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Slf4j
public class LoginSlideController {

    private final LoginSlideService loginSlideService;

    @PostMapping("/create/login-slide")
    public ResponseEntity<ApiResponse<List<HeroSliderResponse>>> createLoginSlide(@RequestBody List<HeroSliderRequest> heroSliderRequests) {


       List<HeroSliderResponse> response = loginSlideService.createLoginSlide(heroSliderRequests);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(200, "Login Slide Created Successfully", response));
    }


    // Update login slide by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<HeroSliderResponse>> updateLoginSlide(@PathVariable Long id, @RequestBody HeroSliderRequest heroSliderRequest) {
        HeroSliderResponse response = loginSlideService.updateLoginSlide(id, heroSliderRequest);
        return ResponseEntity.ok(new ApiResponse<>(200, "Login Slide Updated Successfully", response));
    }

    // Delete login slide by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteLoginSlide(@PathVariable Long id) {
        loginSlideService.deleteLoginSlide(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Login Slide Deleted Successfully", null));
    }
}
