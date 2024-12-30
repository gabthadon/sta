package com.softnet.sta.dto.request;

import com.softnet.sta.database.entity.Course;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class CategoryRequest {

    private String name;
    private String description;
    private String icon;
    private String image;

}
