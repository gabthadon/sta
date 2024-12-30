package com.softnet.sta.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softnet.sta.database.entity.Cohort;
import com.softnet.sta.dto.request.CohortRequest;
import com.softnet.sta.dto.request.CohortUpdateRequest;
import com.softnet.sta.dto.response.CohortResponse;
import com.softnet.sta.repository.CohortRepository;
import com.softnet.sta.service.interfaces.CohortService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class CohortServiceImpl implements CohortService {

    private final CohortRepository cohortRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Override
    public List<CohortResponse> createCohort(List<CohortRequest> cohortRequests) {
        // Convert request objects to entity, parse duration to LocalDate, save them and return response objects
        List<Cohort> cohorts = cohortRequests.stream()
                .map(request -> {
                    Cohort cohort = modelMapper.map(request, Cohort.class);
                    // Parse duration string to LocalDate
                    cohort.setStartDate(LocalDate.parse(request.getStartDate(), formatter));
                    cohort.setEndDate(LocalDate.parse(request.getEndDate(), formatter));
                    return cohort;
                })
                .collect(Collectors.toList());

        // Save the list of cohort entities
        List<Cohort> savedCohorts = cohortRepository.saveAll(cohorts);

        // Map saved entities back to response DTOs
        return savedCohorts.stream()
                .map(savedCohort -> modelMapper.map(savedCohort, CohortResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CohortResponse> getAllCohort() {
        // Fetch all records from the repository and return as response objects
        List<Cohort> cohorts = cohortRepository.findAll();

        return cohorts.stream()
                .map(cohort -> modelMapper.map(cohort, CohortResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CohortResponse> getAllActiveCohort() {
        // Fetch all records from the repository and return as response objects
        List<Cohort> cohorts = cohortRepository.findAllActiveCohorts();

        return cohorts.stream()
                .map(cohort -> modelMapper.map(cohort, CohortResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public CohortResponse getCohortById(Long id) {
        // Fetch by ID and return response
        Cohort cohort = cohortRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cohort with id " + id + " not found"));

        return modelMapper.map(cohort, CohortResponse.class);
    }

    @Override
    public List<CohortResponse> updateCohortById(List<CohortUpdateRequest> cohortUpdateRequests) {
        // Update existing records
        List<Cohort> updatedCohorts = cohortUpdateRequests.stream()
                .map(request -> {
                    Cohort existingCohorts = cohortRepository.findById(request.getId())
                            .orElseThrow(() -> new RuntimeException("Cohort with id " + request.getId() + " not found"));
                    // Map the updated fields from request to existing entity
                    existingCohorts.setName(request.getName());
                    existingCohorts.setStartDate(LocalDate.parse(request.getStartDate(), formatter));
                    existingCohorts.setEndDate(LocalDate.parse(request.getEndDate(), formatter));
                    existingCohorts.setIsActive(request.getIsActive());

                    return existingCohorts;
                })
                .collect(Collectors.toList());

        List<Cohort> savedCohorts = cohortRepository.saveAll(updatedCohorts);

        return savedCohorts.stream()
                .map(cohorts -> modelMapper.map(cohorts, CohortResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CohortResponse> deleteCohortById(Long id) {
        // Delete by ID and return the remaining HeroSliders
        Cohort cohort = cohortRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cohort with id " + id + " not found"));

        cohortRepository.delete(cohort);

        List<Cohort> remainingCohort = cohortRepository.findAll();
        return remainingCohort.stream()
                .map(remaining -> modelMapper.map(remaining, CohortResponse.class))
                .collect(Collectors.toList());
    }
}
