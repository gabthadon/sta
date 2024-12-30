package com.softnet.sta.service.impl;

import com.softnet.sta.database.entity.Testimonia;
import com.softnet.sta.dto.request.TestimonialRequest;
import com.softnet.sta.dto.response.TestimonialResponse;
import com.softnet.sta.mapper.TestimoniaMapper;
import com.softnet.sta.repository.TestimoniaRepository;
import com.softnet.sta.service.interfaces.TestimoniaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestimoniaServiceImpl implements TestimoniaService {

    private final TestimoniaRepository testimoniaRepository;

    @Override
    public TestimonialResponse createTestimonial(TestimonialRequest testimonialRequest) {
        log.info("===============================================" + testimonialRequest.toString());
        Testimonia testimonia = new Testimonia();
        testimonia.setTitle(testimonialRequest.getTitle());
        testimonia.setBody(testimonialRequest.getBody());
       Testimonia testimonialData = testimoniaRepository.save(testimonia);

       return TestimoniaMapper.toTestimoniaResponse(testimonialData);

    }

    @Override
    public TestimonialResponse getTestimonialById(Long id) {
        Testimonia testimonia = testimoniaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Testimony not found with id: " + id));
        return TestimoniaMapper.toTestimoniaResponse(testimonia);
    }

    @Override
    public List<TestimonialResponse> getAllTestimonial() {
        List<Testimonia> testimoniaList = testimoniaRepository.findAll();
        return testimoniaList.stream()
                .map(TestimoniaMapper::toTestimoniaResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TestimonialResponse updateTestimonia(Long id, TestimonialRequest testimoniaRequest) {
        Testimonia testimonia = testimoniaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Testimony not found with id: " + id));
        testimonia.setTitle(testimoniaRequest.getTitle());
        testimonia.setBody(testimoniaRequest.getBody());
        Testimonia updatedTestimonia = testimoniaRepository.save(testimonia);

        return TestimoniaMapper.toTestimoniaResponse(updatedTestimonia);
    }

    @Override
    public void deleteTestimonial(Long id) {
        Testimonia testimonia = testimoniaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Testimony not found with id: " + id));
        testimoniaRepository.delete(testimonia);
    }


}
