package com.softnet.sta.service.impl;

import com.softnet.sta.database.entity.Category;
import com.softnet.sta.dto.request.CategoryRequest;
import com.softnet.sta.dto.response.CategoryResponse;
import com.softnet.sta.exception.NotFoundException;
import com.softnet.sta.mapper.CategoryMapper;
import com.softnet.sta.repository.CategoryRepository;
import com.softnet.sta.service.interfaces.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {

        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        category.setIcon(categoryRequest.getIcon());
        category.setImage(categoryRequest.getImage());

      Category savedCategory =  categoryRepository.save(category);
      return CategoryMapper.toCategoryResponse(savedCategory);

    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper::toCategoryResponse) // Map each Category to CategoryResponse
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category not found", 404);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Optional<Category> cat = categoryRepository.findById(id);

        if(cat.isEmpty()){
            throw new NotFoundException("Category with id " + id +  "not found", 404);
        }

          Category category =   cat.get();

        // Update the category details
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        category.setIcon(categoryRequest.getIcon());
        category.setImage(categoryRequest.getImage());

        // Save the updated category
        Category updatedCategory = categoryRepository.save(category);
        return CategoryMapper.toCategoryResponse(updatedCategory);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Optional<Category> cat = categoryRepository.findById(id);

        if(cat.isEmpty()){
            throw  new NotFoundException("Category with id " + id + "Not Found", 404);
        }

        Category category = cat.get();

        return CategoryMapper.toCategoryResponse(category);
    }

}
