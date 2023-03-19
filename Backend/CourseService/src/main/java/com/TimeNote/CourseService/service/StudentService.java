package com.TimeNote.CourseService.service;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.io.File ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.TimeNote.CourseService.config.DriveConfig;
import com.TimeNote.CourseService.dto.StudentRequest;
import com.TimeNote.CourseService.dto.StudentResponse;
import com.TimeNote.CourseService.entities.Student;
import com.TimeNote.CourseService.respository.StudentRepository;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentService {
    private final   StudentRepository studentRepository;
  
    private final DriveConfig googleDrive; 

    @Autowired
    public StudentService(StudentRepository studentRepository, DriveConfig googleDrive) {
        this.studentRepository = studentRepository;
        this.googleDrive = googleDrive;
    }
    
    

    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentRepository.findAllCustom();
        List<StudentResponse> studentResponses = students.stream().map(student -> mapToStudentResponse(student)).toList();
        return studentResponses;
    }

    public StudentResponse addStudent(MultipartFile file) throws IOException, GeneralSecurityException {
        File converFile = convertToFile(file);
        com.google.api.services.drive.model.File newGGDriveFile = new com.google.api.services.drive.model.File();
        newGGDriveFile.setParents(Collections.singletonList("1Hhxm5kjSu0L9wgfDTR67oxTAPUJH-wIS")).setName(file.getOriginalFilename());
        FileContent mediaContent = new FileContent("application/zip", converFile);
         com.google.api.services.drive.model.File fileW = googleDrive.getService().files().create(newGGDriveFile, mediaContent).setFields("id,webViewLink").execute() ;
        Student student = Student.builder()
                .studentName("AAAA")
                .studentCode("AAAA")
                .build();
        // studentRepository.save(student);
        log.info("Student" + student.getStudentID() +"is saved");
        return mapToStudentResponse(student);
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

    private File convertToFile(MultipartFile multipartFile) throws IOException {
        File file = new File("targetFile.txt");
        multipartFile.transferTo(file);
        return file; 
     }
}
