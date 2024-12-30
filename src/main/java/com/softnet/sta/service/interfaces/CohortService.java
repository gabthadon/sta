package com.softnet.sta.service.interfaces;

import com.softnet.sta.dto.request.CohortRequest;
import com.softnet.sta.dto.request.CohortUpdateRequest;
import com.softnet.sta.dto.request.HeroSliderRequest;
import com.softnet.sta.dto.request.UpdateHeroSliderRequest;
import com.softnet.sta.dto.response.CohortResponse;
import com.softnet.sta.dto.response.HeroSliderResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface CohortService {
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    List<CohortResponse> createCohort(List<CohortRequest> cohortRequests);
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    List<CohortResponse> getAllCohort();

    List<CohortResponse> getAllActiveCohort();
    CohortResponse getCohortById(Long id);
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    List<CohortResponse> updateCohortById(List<CohortUpdateRequest> cohortUpdateRequests);
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    List<CohortResponse> deleteCohortById(Long id);
}
