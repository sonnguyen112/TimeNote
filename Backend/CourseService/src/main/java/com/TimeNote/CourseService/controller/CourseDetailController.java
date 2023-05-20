package com.TimeNote.CourseService.controller;

import com.TimeNote.CourseService.dto.CourseDetailRequest;
import com.TimeNote.CourseService.dto.CourseDetailResponse;
import com.TimeNote.CourseService.exceptions.AppException;
import com.TimeNote.CourseService.service.CourseDetailService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course_api/course_detail")
public class CourseDetailController {
    private final CourseDetailService courseDetailService;

    @Autowired
    public CourseDetailController(CourseDetailService courseDetailService) {
        this.courseDetailService = courseDetailService;
    }

    @PostMapping
    public ResponseEntity<CourseDetailResponse> addCourseDetail(@RequestBody CourseDetailRequest courseDetailRequest,
                                                                @RequestHeader String role){
        if (role.equals("teacher")){
            return ResponseEntity.status(HttpStatus.OK).body(courseDetailService.addCourseDetail(courseDetailRequest));
        }
        throw new AppException(403, "You are not authorized");
    }
}
