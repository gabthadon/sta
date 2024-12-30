package com.softnet.sta.service.impl;

import com.softnet.sta.database.entity.GetInTouch;
import com.softnet.sta.dto.request.GetInTouchRequest;
import com.softnet.sta.dto.response.GetInTouchResponse;
import com.softnet.sta.mapper.GetInTouchMapper;
import com.softnet.sta.repository.GetInTouchRepository;
import com.softnet.sta.service.interfaces.GetInTouchService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class GetInTouchServiceImpl  implements GetInTouchService {


    private final GetInTouchRepository getInTouchRepository;

    @Override
    public GetInTouchResponse getInTouch(GetInTouchRequest request) {
        GetInTouch getInTouch = new GetInTouch();

        getInTouch.setEmail(request.getEmail());
        getInTouch.setFirstName(request.getFirstName());
        getInTouch.setLastName(request.getLastName());
        getInTouch.setPhone(request.getPhone());
        getInTouch.setMessage(request.getMessage());

        GetInTouch getInTouchData = getInTouchRepository.save(getInTouch);

        return GetInTouchMapper.toGetInTouchResponse(getInTouchData);

    }

    @Override
    public GetInTouchResponse getInTouchById(Long id) {
        GetInTouch getInTouch = getInTouchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GetInTouch record not found with ID: " + id));
        return GetInTouchMapper.toGetInTouchResponse(getInTouch);
    }

    @Override
    public GetInTouchResponse updateInTouch(Long id, GetInTouchRequest request) {
        GetInTouch getInTouch = getInTouchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GetInTouch record not found with ID: " + id));

        getInTouch.setEmail(request.getEmail());
        getInTouch.setFirstName(request.getFirstName());
        getInTouch.setLastName(request.getLastName());
        getInTouch.setPhone(request.getPhone());
        getInTouch.setMessage(request.getMessage());

        GetInTouch updatedInTouch = getInTouchRepository.save(getInTouch);
        return GetInTouchMapper.toGetInTouchResponse(updatedInTouch);
    }

    @Override
    public void deleteInTouch(Long id) {

        GetInTouch getInTouch = getInTouchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GetInTouch record not found with ID: " + id));
        getInTouchRepository.delete(getInTouch);
    }

    @Override
    public List<GetInTouchResponse> getAllInTouch() {
        List<GetInTouch> getInTouchList = getInTouchRepository.findAll();
        return getInTouchList.stream()
                .map(GetInTouchMapper::toGetInTouchResponse)
                .collect(Collectors.toList());
    }


}