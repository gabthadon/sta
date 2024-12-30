package com.softnet.sta.controller;

import com.softnet.sta.dto.request.TestimonialRequest;
import com.softnet.sta.dto.response.ApiResponse;
import com.softnet.sta.dto.response.TestimonialResponse;
import com.softnet.sta.service.interfaces.TestimoniaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/testimonial")
public class TestimonialController {

    private final TestimoniaService testimoniaService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<TestimonialResponse>> createTestimony(@RequestBody TestimonialRequest testimonialRequest) {
        TestimonialResponse response = testimoniaService.createTestimonial(testimonialRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Testimony Created successfully", response));
    }


    @GetMapping("get/{id}")
    public ResponseEntity<ApiResponse<TestimonialResponse>> getTestimonyById(@PathVariable Long id) {
        TestimonialResponse response = testimoniaService.getTestimonialById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Testimony fetched successfully", response));
    }

    @GetMapping("get/all")
    public ResponseEntity<ApiResponse<List<TestimonialResponse>>> getAllTestimonies() {
        List<TestimonialResponse> responses = testimoniaService.getAllTestimonial();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "All testimonies fetched successfully", responses));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<TestimonialResponse>> updateTestimony(@PathVariable Long id, @RequestBody TestimonialRequest testimoniaRequest) {
        TestimonialResponse response = testimoniaService.updateTestimonia(id, testimoniaRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Testimony updated successfully", response));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTestimonial(@PathVariable Long id) {
        testimoniaService.deleteTestimonial(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Testimony deleted successfully", null));
    }
}
