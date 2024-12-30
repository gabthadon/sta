package com.softnet.sta.controller;

import com.softnet.sta.dto.request.DiscountRequest;
import com.softnet.sta.dto.response.ApiResponse;
import com.softnet.sta.dto.response.DiscountResponse;
import com.softnet.sta.service.interfaces.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/discount")
@Slf4j
public class DiscountController {
    private final DiscountService discountService;


    @Operation(
            summary = "CREATE DISCOUNT  API",
            description = "This REST API CREATE DISCOUNT"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "HTTP Status 400 BAD REQUEST")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "HTTP Status 500 INTERNAL SERVER ERROR")

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<DiscountResponse>> CreateDiscount(@RequestBody DiscountRequest discountRequest) {
        DiscountResponse response = discountService.createDiscount(discountRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(200, "Discount Created Successfully", response));
    }



    @Operation(
            summary = "GET ALL DISCOUNTS API",
            description = "This REST API fetches all discounts"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<DiscountResponse>>> getAllDiscounts() {
        List<DiscountResponse> discounts = discountService.getAllDiscounts();
        return ResponseEntity.ok(new ApiResponse<>(200, "Discounts fetched successfully", discounts));
    }

    @Operation(
            summary = "GET DISCOUNT BY ID API",
            description = "This REST API fetches a discount by ID"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "HTTP Status 404 NOT FOUND")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DiscountResponse>> getDiscountById(@PathVariable Long id) {
        DiscountResponse discount = discountService.getDiscountById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Discount fetched successfully", discount));
    }

    @Operation(
            summary = "UPDATE DISCOUNT API",
            description = "This REST API updates a discount by ID"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<DiscountResponse>> updateDiscount(
            @PathVariable Long id,
            @RequestBody DiscountRequest discountRequest) {
        DiscountResponse updatedDiscount = discountService.updateDiscount(id, discountRequest);
        return ResponseEntity.ok(new ApiResponse<>(200, "Discount updated successfully", updatedDiscount));
    }

    @Operation(
            summary = "DELETE DISCOUNT BY ID API",
            description = "This REST API deletes a discount by ID"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "HTTP Status 404 NOT FOUND")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscountById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Discount deleted successfully", "Deleted successfully"));
    }

}
