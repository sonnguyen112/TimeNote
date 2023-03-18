package com.TimeNote.CourseService.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.TimeNote.CourseService.dto.StudentRequest;
import com.TimeNote.CourseService.dto.StudentResponse;
import com.TimeNote.CourseService.entities.Student;
import com.TimeNote.CourseService.respository.StudentRepository;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentService {
    private final   StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentResponse> studentResponses = students.stream().map(student -> mapToStudentResponse(student)).toList();
        return studentResponses;
    }

    public void addStudent(StudentRequest studentRequest){
        
        Student student = Student.builder()
                .studentName(studentRequest.getStudentName())
                .studentCode(studentRequest.getStudentCode())
                .studentImageUrl(studentRequest.getStudentImageUrl())
                .build();
        studentRepository.save(student);
        log.info("Student" + student.getStudentID() +"is saved");
    }

    public ResponseEntity<StudentResponse> getOneStudent(String id){  
        Student student = studentRepository.findById(id).get();
        if (student == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapToStudentResponse(student)) ;
    }
   
    public boolean editOneStudent( StudentRequest studentRequest, String id)
    {   
        Student student = studentRepository.findByStudentCode(id);
        if (student == null)
        {
            return false;
        }
        student.setStudentName(studentRequest.getStudentName());
        student.setStudentCode(studentRequest.getStudentCode());
        student.setStudentImageUrl(studentRequest.getStudentImageUrl());
        studentRepository.save(student);
        return true;
    }

    public boolean deleteOneStudent( String id) {
        Student student = studentRepository.findByStudentCode(id);
        if (student == null)
        {
            return false;
        }
        student.setDelete(true);
        return true;
      }

    private StudentResponse mapToStudentResponse(Student student){
        return StudentResponse.builder()
                .studentID(student.getStudentID())
                .studentName(student.getStudentName())
                .studentCode(student.getStudentCode())
                .studentImageUrl(student.getStudentImageUrl())
                .build();
    }
}
