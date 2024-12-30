package com.softnet.sta.controller;

import com.softnet.sta.dto.request.CategoryRequest;
import com.softnet.sta.dto.response.ApiResponse;
import com.softnet.sta.dto.response.CategoryResponse;
import com.softnet.sta.service.interfaces.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "Category Controller",
        description = "Exposes APIs to manage Category Operations"
)

@AllArgsConstructor
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;


    @Operation(
            summary = "Create Category REST API",
            description = "This REST API is used to create a new category"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category Created Successfully")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody CategoryRequest categoryRequest) {

      CategoryResponse response =  categoryService.createCategory(categoryRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(200, "Category Created Successfully", response));
    }



    @Operation(
            summary = "Delete Category By Id REST API",
            description = "This REST API is used to delete an existing category by its ID"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category Deleted Successfully")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Category Deleted Successfully", null));
    }


    @Operation(
            summary = "Update Category By Id REST API",
            description = "This REST API is used to update an existing category by its ID"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category Update Successfully")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse updatedCategory = categoryService.updateCategory(id, categoryRequest);
        return ResponseEntity.ok(new ApiResponse<>(200, "Category Updated Successfully", updatedCategory));
        //
    }








}