package com.TimeNote.CourseService.service;

import java.util.Optional;

import org.aspectj.apache.bcel.util.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.TimeNote.CourseService.dto.StudentRequest;
import com.TimeNote.CourseService.dto.StudentResponse;
import com.TimeNote.CourseService.entities.Student;
import com.TimeNote.CourseService.respository.StudentRespository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private final   StudentRespository studentRespository;

    public void addStudent(StudentRequest studentRequest){
        
        Student student = Student.builder()
                .studentName(studentRequest.getStudentName())
                .studentCode(studentRequest.getStudentCode())
                .studentImageUrl(studentRequest.getStudentImageUrl())
                .build();
        studentRespository.save(student);
        log.info("Student" + student.getStudentID() +"is saved");
    }

    public ResponseEntity<StudentResponse> getOneStudent(String id){  
        Student student = studentRespository.findById(id).get();
        if (student == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapToStudentResponse(student)) ;
    }
   
    public boolean editOneStudent( StudentRequest studentRequest, String id)
    {   
        Student student = studentRespository.findByStudentCode(id);
        if (student == null)
        {
            return false;
        }
        student.setStudentName(studentRequest.getStudentName());
        student.setStudentCode(studentRequest.getStudentCode());
        student.setStudentImageUrl(studentRequest.getStudentImageUrl());
        studentRespository.save(student);
        return true;
    }

    public boolean deleteOneStudent( String id) {
        Student student = studentRespository.findByStudentCode(id);
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
