package com.TimeNote.CourseService.controller;

import com.TimeNote.CourseService.dto.CourseRequest;
import com.TimeNote.CourseService.dto.CourseResponse;
import com.TimeNote.CourseService.dto.LecturerRequest;
import com.TimeNote.CourseService.dto.LecturerResponse;
import com.TimeNote.CourseService.exceptions.AppException;
import com.TimeNote.CourseService.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course_api/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseResponse> addLecturer(@RequestBody CourseRequest courseRequest, @RequestHeader("role") String role){
        if (role.equals("teacher")) {
            return ResponseEntity.status(HttpStatus.OK).body(courseService.addCourse(courseRequest));
        }
        throw new AppException(403, "You are not authorized");
    }
}
