package com.TimeNote.CourseService.controller;

import com.TimeNote.CourseService.dto.LecturerRequest;
import com.TimeNote.CourseService.dto.LecturerResponse;
import com.TimeNote.CourseService.exceptions.AppException;
import com.TimeNote.CourseService.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course_api/lecturer")
public class LecturerController {

    private final LecturerService lecturerService;

    @Autowired
    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @PostMapping
    public ResponseEntity<LecturerResponse> addLecturer(@RequestBody LecturerRequest lecturerRequest,
                                                        @RequestHeader String role){
        if (role.equals("teacher")){
            return ResponseEntity.status(HttpStatus.OK).body(lecturerService.addLecturer(lecturerRequest));
        }
        throw new AppException(403, "You are not authorized");
    }
}
