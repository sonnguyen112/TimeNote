package com.TimeNote.CourseService.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.TimeNote.CourseService.dto.StudentResponse;
import com.TimeNote.CourseService.service.StudentService;

@RequestMapping("/api/student")
@RestController
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllStudents());
    }

    @PostMapping
    public ResponseEntity<StudentResponse> addStudent(@RequestParam("body") String studentRequest,
            @RequestParam("image") MultipartFile file) throws IOException, GeneralSecurityException {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.addStudent(studentRequest, file));
    }

    @PutMapping
    public ResponseEntity<StudentResponse> editOneStudent(@RequestParam("body") String studentRequest,
            @RequestParam("code") String id, @RequestParam("image") MultipartFile file)
            throws IOException, GeneralSecurityException {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.editOneStudent(studentRequest, id, file));
    }

    @DeleteMapping
    public ResponseEntity<StudentResponse> deleteOneStudent(@RequestParam("code") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.deleteOneStudent(id));
    }
}
