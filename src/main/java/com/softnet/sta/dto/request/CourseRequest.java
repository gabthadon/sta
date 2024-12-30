package com.softnet.sta.dto.request;

import com.softnet.sta.database.entity.Category;
import com.softnet.sta.database.entity.Instructor;
import com.softnet.sta.database.entity.Packages;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class CourseRequest {

    private String courseName;
    private Long categoryId;
    private String courseDescription;




}
