package com.softnet.sta.service.interfaces;

import com.softnet.sta.dto.request.TestimonialRequest;
import com.softnet.sta.dto.response.TestimonialResponse;

import java.util.List;

public interface TestimoniaService {
    TestimonialResponse createTestimonial(TestimonialRequest testimoniaRequest);

    TestimonialResponse getTestimonialById(Long id);

    List<TestimonialResponse> getAllTestimonial();

    TestimonialResponse updateTestimonia(Long id, TestimonialRequest testimoniaRequest);

    void deleteTestimonial(Long id);


}
