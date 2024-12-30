package com.softnet.sta.controller;


import com.softnet.sta.dto.request.HeroSliderRequest;
import com.softnet.sta.dto.request.UpdateHeroSliderRequest;
import com.softnet.sta.dto.response.ApiResponse;
import com.softnet.sta.dto.response.HeroSliderResponse;
import com.softnet.sta.service.interfaces.HeroSliderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "HeroSlider Controller",
        description = "Exposes APIs to manage HeroSlider Operations"
)

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/heroSlider")
public class HeroSliderController {
    private final HeroSliderService heroSliderService;

    @Operation(
            summary = "Create Hero Sliders REST API",
            description = "This REST API is used to create new HeroSliders"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HeroSlider Created Successfully")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<List<HeroSliderResponse>>> createHeroSlider(@Valid @RequestBody List<HeroSliderRequest> heroSliderRequests) {
        try {
            List<HeroSliderResponse> response = heroSliderService.createHeroSlider(heroSliderRequests);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "Hero Slider Created successfully", response));
        } catch (Exception e) {
            log.error("Error Creating Hero Slider", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to Create Hero Slider", null));
        }
    }


    @Operation(
            summary = "Update Hero Sliders by Id REST API",
            description = "This REST API is used to Update an existing HeroSlider by Id"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HeroSlider Updated Successfully")
    @PatchMapping("/update/by/id")
    public ResponseEntity<ApiResponse<List<HeroSliderResponse>>> updateHeroSliderById(@RequestBody List<UpdateHeroSliderRequest> heroSliderRequests) {
        try {
            List<HeroSliderResponse> response = heroSliderService.updateHeroSliderById(heroSliderRequests);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "Hero Slider Updated successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error updating Hero Slider", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to Updated Hero Slider", null));
        }
    }


    @Operation(
            summary = "Delete Hero Sliders by Id REST API",
            description = "This REST API is used to Delete an existing HeroSlider By Id"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HeroSlider Deleted Successfully")
    @DeleteMapping("/delete/by/id/{id}")
    public ResponseEntity<ApiResponse<List<HeroSliderResponse>>> deleteHeroSliderById(@PathVariable Long id) {
        try {
            List<HeroSliderResponse> response = heroSliderService.deleteHeroSliderById(id);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "Hero Slider with Id: " + id + " Deleted successfully, Remaining Hero Sliders below", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error deleting Hero Slider", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete Hero Slider", null));
        }
    }

}
