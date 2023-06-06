package com.TimeNote.CourseService.controller;

import com.TimeNote.CourseService.dto.CourseDetailRequest;
import com.TimeNote.CourseService.dto.CourseDetailResponse;
import com.TimeNote.CourseService.exceptions.AppException;
import com.TimeNote.CourseService.service.CourseDetailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.List;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
                                                                @RequestHeader String role) throws JsonProcessingException {
        if (role.equals("teacher")){
            return ResponseEntity.status(HttpStatus.OK).body(courseDetailService.addCourseDetail(courseDetailRequest));
        }
        throw new AppException(403, "You are not authorized");
    }

    @GetMapping
    public ResponseEntity<List<CourseDetailResponse>> getCourseDetailByUserCode(@RequestParam("userCode") String userCode,
    @RequestParam("longitude") Double longitude, @RequestParam("latitude") Double latitude, @RequestHeader String role) throws JsonMappingException, JsonProcessingException{
        if (role.equals("teacher")){
            return ResponseEntity.status(HttpStatus.OK).body(courseDetailService.getCourseDetailByTeacherCode(userCode));
        }
        else if (role.equals("student")){
            return ResponseEntity.status(HttpStatus.OK).body(courseDetailService.getCourseDetailByStudentCode(userCode, longitude, latitude));
        }
        throw new AppException(403, "You are not authorized");
    }
}
