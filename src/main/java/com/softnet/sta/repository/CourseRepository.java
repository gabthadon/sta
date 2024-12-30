package com.softnet.sta.repository;

import com.softnet.sta.database.entity.Category;
import com.softnet.sta.database.entity.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository  extends JpaRepository<Course, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM course_package WHERE course_id = :courseId; DELETE FROM course WHERE id = :courseId", nativeQuery = true)
    void deleteCourseWithRelations(@Param("courseId") Long courseId);


    @Query(value = "SELECT * FROM course WHERE category_id = :categoryId", nativeQuery = true)
    List<Course> findByCategory(@Param("categoryId") Long categoryId);

}