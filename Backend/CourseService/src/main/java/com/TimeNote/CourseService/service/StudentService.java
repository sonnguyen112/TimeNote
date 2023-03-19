package com.TimeNote.CourseService.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;

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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Channels.Stop;
import com.google.api.services.drive.model.About.StorageQuota;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentService {
    private final StudentRepository studentRepository;

    private final DriveConfig googleDrive;

    @Autowired
    public StudentService(StudentRepository studentRepository, DriveConfig googleDrive) {
        this.studentRepository = studentRepository;
        this.googleDrive = googleDrive;
    }

    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentRepository.getAllStudents();
        List<StudentResponse> studentResponses = students.stream().map(student -> mapToStudentResponse(student))
                .toList();
        return studentResponses;
    }

    public StudentResponse addStudent(String studentRequestString, MultipartFile file)
            throws IOException, GeneralSecurityException {
        File converFile = convert(file);
        com.google.api.services.drive.model.File newGGDriveFile = new com.google.api.services.drive.model.File();
        newGGDriveFile.setParents(Collections.singletonList("1Hhxm5kjSu0L9wgfDTR67oxTAPUJH-wIS"))
                .setName(file.getOriginalFilename());
        FileContent mediaContent = new FileContent("image/jpeg", converFile);
        com.google.api.services.drive.model.File fileW = googleDrive.getService().files()
                .create(newGGDriveFile, mediaContent).setFields("id,webViewLink,webContentLink").execute();
        ObjectMapper objectMapper = new ObjectMapper();
        StudentRequest studentRequest = objectMapper.readValue(studentRequestString, StudentRequest.class);
        Student existStudent = studentRepository.findByStudentCode(studentRequest.getStudentCode());
        if (existStudent == null) {
            Student student = Student.builder()
                    .studentName(studentRequest.getStudentName())
                    .studentCode(studentRequest.getStudentCode())
                    .studentImageUrl(fileW.getWebContentLink())
                    .build();
            studentRepository.save(student);
            log.info("Student" + student.getStudentID() + "is saved");
            return mapToStudentResponse(student);
        } else {
            if (existStudent.isDelete() == true) {
                existStudent.setDelete(false);
                return editOneStudent(studentRequestString, existStudent.getStudentCode(), file);
            } else {
                throw new RuntimeException("student is still exist");
            }
        }

    }

    public ResponseEntity<StudentResponse> getOneStudent(String id) {
        Student student = studentRepository.findById(id).get();
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapToStudentResponse(student));
    }

    public StudentResponse editOneStudent(String studentRequestString, String id, MultipartFile file)
            throws IOException, GeneralSecurityException {
        Student student = studentRepository.findByStudentCode(id);
        if (student == null) {
            // return mapToStudentResponse(student);
            throw new RuntimeException("student is Null");
        }
        if (!file.isEmpty()) {
            File converFile = convert(file);
            com.google.api.services.drive.model.File newGGDriveFile = new com.google.api.services.drive.model.File();
            newGGDriveFile.setParents(Collections.singletonList("1Hhxm5kjSu0L9wgfDTR67oxTAPUJH-wIS"))
                    .setName(file.getOriginalFilename());
            FileContent mediaContent = new FileContent("image/jpeg", converFile);
            com.google.api.services.drive.model.File fileW = googleDrive.getService().files()
                    .create(newGGDriveFile, mediaContent).setFields("id,webViewLink,webContentLink").execute();
            ObjectMapper objectMapper = new ObjectMapper();
            StudentRequest studentRequest = objectMapper.readValue(studentRequestString, StudentRequest.class);
            student.setStudentName(studentRequest.getStudentName());
            student.setStudentCode(studentRequest.getStudentCode());
            studentRepository.save(student);
        } 
        else {
            ObjectMapper objectMapper = new ObjectMapper();
            StudentRequest studentRequest = objectMapper.readValue(studentRequestString, StudentRequest.class);
            
            student.setStudentName(studentRequest.getStudentName());
            student.setStudentCode(studentRequest.getStudentCode());
            studentRepository.save(student);
        }
        return mapToStudentResponse(student);
    }

    public StudentResponse deleteOneStudent(String id) {
        System.out.println("khoi" + id + "lllll");
        Student student = studentRepository.findByStudentCode(id);
        if (student == null) {
            throw new RuntimeException("student is null");
        }
        student.setDelete(true);
        studentRepository.save(student);
        return mapToStudentResponse(student);
    }

    private StudentResponse mapToStudentResponse(Student student) {
        return StudentResponse.builder()
                .studentID(student.getStudentID())
                .studentName(student.getStudentName())
                .studentCode(student.getStudentCode())
                .studentImageUrl(student.getStudentImageUrl())
                .build();
    }
    public File convert(MultipartFile file) {
        File convFile = new File(file.getOriginalFilename());
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close(); // IOUtils.closeQuietly(fos);
        } catch (IOException e) {
            convFile = null;
        }

        return convFile;
    }
}
