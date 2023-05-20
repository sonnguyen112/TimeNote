package com.TimeNote.CourseService.service;

import com.TimeNote.CourseService.dto.CourseRequest;
import com.TimeNote.CourseService.dto.CourseResponse;
import com.TimeNote.CourseService.entities.Course;
import com.TimeNote.CourseService.exceptions.AppException;
import com.TimeNote.CourseService.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public CourseResponse addCourse(CourseRequest courseRequest) {
        Course courseExist = courseRepository.findByCourseCode(courseRequest.getCourseCode());
        if (courseExist != null){
            throw new AppException(409, "Course is exist");
        }
        Course newCourse = Course.builder()
                .courseCode(courseRequest.getCourseCode())
                .courseName(courseRequest.getCourseName())
                .build();
        courseRepository.save(newCourse);
        return courseToCourseResponse(newCourse);
    }

    private CourseResponse courseToCourseResponse(Course newCourse) {
        return CourseResponse.builder()
                .courseCode(newCourse.getCourseCode())
                .courseName(newCourse.getCourseName())
                .courseID(newCourse.getCourseID())
                .build();
    }
}
