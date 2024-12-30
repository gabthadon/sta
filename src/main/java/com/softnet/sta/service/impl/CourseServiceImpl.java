package com.softnet.sta.service.impl;

import com.softnet.sta.constant.IdValues;
import com.softnet.sta.database.entity.Category;
import com.softnet.sta.database.entity.Course;
import com.softnet.sta.database.entity.Instructor;
import com.softnet.sta.dto.request.CourseRequest;
import com.softnet.sta.dto.response.CourseResponse;
import com.softnet.sta.exception.InvalidRequestException;
import com.softnet.sta.exception.NotFoundException;
import com.softnet.sta.mapper.CourseMapper;
import com.softnet.sta.repository.CategoryRepository;
import com.softnet.sta.repository.CourseRepository;
import com.softnet.sta.repository.InstructorRepository;
import com.softnet.sta.service.interfaces.CourseService;
import com.softnet.sta.util.IDGenerator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Override
    public CourseResponse createCourse(CourseRequest courseRequest) {


            String courseCode = IDGenerator.generateIDForCourseCode(IdValues.COURSECODE);

            // Create a new Course entity
            Course course = new Course();
            course.setCourseName(courseRequest.getCourseName());
            course.setCourseDescription(courseRequest.getCourseDescription());
            course.setCourseCode(courseCode);


            // Retrieve Category by ID
            Optional<Category> category = categoryRepository.findById(courseRequest.getCategoryId());

            course.setCategory(category.get());


            log.debug("--------------------Saving course: {}", course);
            Course savedCourse = courseRepository.save(course);
            log.debug("---------------Course saved: {}", savedCourse);

            // Return the response
            return CourseMapper.toCourseResponse(savedCourse);



    }




    @Override
    public CourseResponse updateCourse(CourseRequest courseRequest, Long id) {
      Optional<Course> course = courseRepository.findById(id);

      if(course.isEmpty()){
          throw new NotFoundException("Course not found", 404);
      }
      course.get().setCourseName(courseRequest.getCourseName());
      course.get().setCourseDescription(courseRequest.getCourseDescription());


        Optional<Category> category = categoryRepository.findById(courseRequest.getCategoryId());

        if (category.isEmpty()){
            throw new NotFoundException("Category with id "+courseRequest.getCategoryId() + "not found", 404);
        }

        Category cat = category.get();

      course.get().setCategory(cat);

    ;

      Course savedCourse = courseRepository.save(course.get());

     return CourseMapper.toCourseResponse(savedCourse);
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        // Retrieve all courses from the repository
        List<Course> courses = courseRepository.findAll();

        // Initialize the list that will hold the CourseResponse objects
        List<CourseResponse> courseResponseList = new ArrayList<>();

        // Iterate over each course and convert it into CourseResponse
        for (Course course : courses) {
            // Create a new CourseResponse and populate it with data from the Course entity
            CourseResponse courseResponse = CourseMapper.toCourseResponse(course);

            // Add the CourseResponse to the response list
            courseResponseList.add(courseResponse);
        }

        // Return the list of CourseResponse objects
        return courseResponseList;
    }




    @Override
    public CourseResponse getCourseById(Long id) {
Optional<Course> course = courseRepository.findById(id);

if(course.isEmpty()){
    throw new NotFoundException("Course not found wih id "+id, 404);
}
return CourseMapper.toCourseResponse(course.get());

    }




    @Transactional
    @Override
    public String deleteCourse(Long id) {
        // Check if the course exists
        Optional<Course> course = courseRepository.findById(id);

        if (course.isEmpty()) {
            throw new NotFoundException("Course not found with id " + id, 404);
        }

        // First, delete any related entries in the packages_course table
     courseRepository.deleteCourseWithRelations(id);





        return "Course with id " + id + " deleted successfully.";
    }


    @Transactional
    @Override
    public List<CourseResponse> getCoursesByCategoryId(Long categoryId) {
        List<Course> courses = courseRepository.findByCategory(categoryId);

        return courses.stream()
                .map(CourseMapper::toCourseResponse) // Convert each Course to CourseResponse using a mapper
                .collect(Collectors.toList()); // Collect the results as a list
    }


}
