package com.softnet.sta.controller;

import com.softnet.sta.dto.request.PackageRequest;
import com.softnet.sta.dto.response.ApiResponse;
import com.softnet.sta.dto.response.PackageResponse;
import com.softnet.sta.service.interfaces.PackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(
        name = "Package Controller",
        description = "Exposes APIs to manage packages like creation, update, deletion, and retrieval"
)
@RequiredArgsConstructor
@RestController
@RequestMapping("/package")
@Slf4j
public class PackageController {

    private final PackageService packageService;

    @Operation(
            summary = "Create Package REST API",
            description = "This REST API is used to create a new package"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Learner Details Fetched Successfully")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PackageResponse>> createPackage(@RequestBody PackageRequest packageRequest) {

        PackageResponse response = packageService.createPackage(packageRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(200, "Package Created Successfully", response));
    }

    @Operation(
            summary = "Update Package REST API",
            description = "This REST API is used to update an existing package by its ID"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Learner Details Fetched Successfully")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<PackageResponse>> updatePackage(@RequestBody PackageRequest packageRequest, @PathVariable("id") Long id) {

        PackageResponse response = packageService.updatePackage(packageRequest, id);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Package Updated Successfully", response));
    }

    @Operation(
            summary = "Delete Package REST API",
            description = "This REST API is used to delete an existing package by its ID"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Learner Details Fetched Successfully")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deletePackage(@PathVariable("id") Long id) {
        packageService.deletePackage(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Package Deleted Successfully", "Package with id " + id + " deleted"));
    }

    @Operation(
            summary = "Get Packages By Category Id REST API",
            description = "This REST API is used to retrieve all packages by a specific category ID"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Learner Details Fetched Successfully")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<PackageResponse>>> getPackagesByCategoryId(@PathVariable Long categoryId) {
        List<PackageResponse> response = packageService.getPackagesByCategoryId(categoryId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Packages Retrieved Successfully", response));
    }

}
