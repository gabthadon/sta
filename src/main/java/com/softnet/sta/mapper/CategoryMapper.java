package com.softnet.sta.mapper;

import com.softnet.sta.database.entity.Category;
import com.softnet.sta.dto.response.CategoryResponse;

public class CategoryMapper {

    public static CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getIcon(),
                category.getImage()
        );
    }
}
