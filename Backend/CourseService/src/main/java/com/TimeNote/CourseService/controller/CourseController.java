package com.TimeNote.CourseService.controller;

import com.TimeNote.CourseService.dto.CourseRequest;
import com.TimeNote.CourseService.dto.CourseResponse;
import com.TimeNote.CourseService.dto.LecturerRequest;
import com.TimeNote.CourseService.dto.LecturerResponse;
import com.TimeNote.CourseService.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course_api/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseResponse> addLecturer(@RequestBody CourseRequest courseRequest){
        return ResponseEntity.status(HttpStatus.OK).body(courseService.addCourse(courseRequest));
    }
}
