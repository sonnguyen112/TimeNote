package com.TimeNote.CourseService.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.TimeNote.CourseService.dto.StudentRequest;
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
    public ResponseEntity<List<StudentResponse>> getAllStudents(){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllStudents());
    }

    @PostMapping
    public ResponseEntity<StudentResponse> addStudent(@RequestParam("image") MultipartFile file) throws IOException, GeneralSecurityException {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.addStudent(file));
    }

    // @GetMapping
    // @ResponseStatus(HttpStatus.OK)
    // public StudentResponse getOneStudent(@PathVariable String studentID){
    //    return studentService.getOneStudent(studentID);
    // }

    @PutMapping
    public ResponseEntity<StudentResponse> editOneStudent(@RequestBody StudentRequest studentRequest, @RequestParam String id)
    {
        if(studentService.editOneStudent(studentRequest,id)){
            return ResponseEntity.status(HttpStatus.OK).build();
        };
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteOneStudent( @RequestParam String id)
    {
        if(studentService.deleteOneStudent(id)){
            return ResponseEntity.status(HttpStatus.OK).build();
        };
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    
}
