package com.softnet.sta.service.interfaces;

import com.softnet.sta.dto.request.PackageRequest;
import com.softnet.sta.dto.response.PackageResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface PackageService {


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    PackageResponse createPackage(PackageRequest packageRequest);
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    PackageResponse updatePackage(PackageRequest packageRequest, Long id);
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    void deletePackage(Long id);

    PackageResponse getPackageById(Long id);

    List<PackageResponse> getAllPackages();

    List<PackageResponse> getPackagesByCategoryId(Long categoryId);
}
