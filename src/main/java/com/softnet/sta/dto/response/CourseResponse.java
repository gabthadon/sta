package com.softnet.sta.dto.response;

import com.softnet.sta.database.entity.Category;
import com.softnet.sta.database.entity.Instructor;
import com.softnet.sta.database.entity.Packages;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CourseResponse {

    private Long id;
    private String courseName;
    private String courseCode;
    private String courseDescription;
    private String categoryName;
    private Long categoryId;



}
