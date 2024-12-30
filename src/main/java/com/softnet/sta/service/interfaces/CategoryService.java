package com.softnet.sta.service.interfaces;

import com.softnet.sta.dto.request.CategoryRequest;
import com.softnet.sta.dto.response.CategoryResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface CategoryService {
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    CategoryResponse createCategory(CategoryRequest categoryRequest);

    List<CategoryResponse> getAllCategories();

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    void deleteCategoryById(Long id);

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest);

    CategoryResponse getCategoryById(Long id);


}
