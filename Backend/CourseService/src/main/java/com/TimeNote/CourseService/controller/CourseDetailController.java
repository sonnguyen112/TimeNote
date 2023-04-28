package com.TimeNote.CourseService.controller;

import com.TimeNote.CourseService.dto.CourseDetailRequest;
import com.TimeNote.CourseService.dto.CourseDetailResponse;
import com.TimeNote.CourseService.service.CourseDetailService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course_detail")
public class CourseDetailController {
    private final CourseDetailService courseDetailService;

    @Autowired
    public CourseDetailController(CourseDetailService courseDetailService) {
        this.courseDetailService = courseDetailService;
    }

    @PostMapping
    public ResponseEntity<CourseDetailResponse> addCourseDetail(@RequestBody CourseDetailRequest courseDetailRequest){
        return ResponseEntity.status(HttpStatus.OK).body(courseDetailService.addCourseDetail(courseDetailRequest));
    }
}
