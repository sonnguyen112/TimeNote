package com.TimeNote.CourseService.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.TimeNote.CourseService.exceptions.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import com.TimeNote.CourseService.dto.StudentResponse;
import com.TimeNote.CourseService.service.StudentService;

@RequestMapping("/course_api/student")
@RestController
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping(path="/all")
    public ResponseEntity<List<StudentResponse>> getAllStudents(@RequestHeader String role){
        if (role.equals("teacher")){
            return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllStudents());
        }
        throw new AppException(403, "You are not authorized");
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudentsOfCourse(@RequestParam("course_detail_id") Long id,
                                                                        @RequestHeader String role){
        if (role.equals("teacher")){
            return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllStudentsOfCourse(id));
        }
        throw new AppException(403, "You are not authorized");
    }

    @PostMapping
    public ResponseEntity<StudentResponse> addStudent(@RequestParam("body") String studentRequest,
            @RequestParam("image") MultipartFile file, @RequestHeader String role) throws IOException, GeneralSecurityException {
        if (role.equals("teacher"))
            return ResponseEntity.status(HttpStatus.OK).body(studentService.addStudent(studentRequest, file));
        throw new AppException(403, "You are not authorized");
    }

    // @GetMapping
    // @ResponseStatus(HttpStatus.OK)
    // public StudentResponse getOneStudent(@PathVariable String studentID){
    //    return studentService.getOneStudent(studentID);
    // }

    @PutMapping
    public ResponseEntity<StudentResponse> editOneStudent(@RequestParam("body") String studentRequest,
            @RequestParam("code") String id, @RequestParam("image") MultipartFile file, @RequestHeader String role)
            throws IOException, GeneralSecurityException {
        if (role.equals("teacher"))
            return ResponseEntity.status(HttpStatus.OK).body(studentService.editOneStudent(studentRequest, id, file));
        throw new AppException(403, "You are not authorized");
    }

    @DeleteMapping
    public ResponseEntity<StudentResponse> deleteOneStudent(@RequestParam("code") String id,
                                                            @RequestHeader String role) {
        if (role.equals("teacher"))
            return ResponseEntity.status(HttpStatus.OK).body(studentService.deleteOneStudent(id));
        throw new AppException(403, "You are not authorized");
    }
}
