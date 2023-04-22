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

import com.TimeNote.CourseService.entities.CourseDetail;
import com.TimeNote.CourseService.exceptions.AppException;
import com.TimeNote.CourseService.kafka.KafkaProducer;
import com.TimeNote.CourseService.kafka.Message;
import com.TimeNote.CourseService.repository.CourseDetailRepository;
import com.TimeNote.CourseService.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
    
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.TimeNote.CourseService.config.DriveConfig;
import com.TimeNote.CourseService.dto.StudentRequest;
import com.TimeNote.CourseService.dto.StudentResponse;
import com.TimeNote.CourseService.entities.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.FileContent;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Channels.Stop;
import com.google.api.services.drive.model.About.StorageQuota;



@Service
// @Slf4j
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseDetailRepository courseDetailRepository;

    private final DriveConfig googleDrive;
    private final KafkaProducer kafkaProducer;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseDetailRepository courseDetailRepository, DriveConfig googleDrive, KafkaProducer kafkaProducer) {
        this.studentRepository = studentRepository;
        this.courseDetailRepository = courseDetailRepository;
        this.googleDrive = googleDrive;
        this.kafkaProducer = kafkaProducer;
    }

    public List<StudentResponse> getAllStudentsOfCourse(Long id) {
        CourseDetail courseDetail = courseDetailRepository.findById(id).orElse(null);
        if (courseDetail == null){
            throw new AppException(404, "Course not found");
        }
        List<Student> students = courseDetail.getStudents();
        List<StudentResponse> studentResponses = students.stream().map(student -> mapToStudentResponse(student))
                .toList();
        return studentResponses;
    }

    public StudentResponse addStudent(String studentRequestString, MultipartFile file)
            throws IOException, GeneralSecurityException {

        ObjectMapper objectMapper = new ObjectMapper();
        StudentRequest studentRequest = objectMapper.readValue(studentRequestString, StudentRequest.class);
        Student existStudent = studentRepository.findByStudentCode(studentRequest.getStudentCode());
        if (existStudent == null) {
            byte [] byteArr=file.getBytes();
            kafkaProducer.send(new Message(),byteArr);
            File converFile = convert(file);
            com.google.api.services.drive.model.File newGGDriveFile = new com.google.api.services.drive.model.File();
            newGGDriveFile.setParents(Collections.singletonList("1Hhxm5kjSu0L9wgfDTR67oxTAPUJH-wIS"))
                    .setName(file.getOriginalFilename());
            FileContent mediaContent = new FileContent("image/jpeg", converFile);
            com.google.api.services.drive.model.File fileW = googleDrive.getService().files()
                    .create(newGGDriveFile, mediaContent).setFields("id,webViewLink,webContentLink").execute();
            String reduceLink = fileW.getWebContentLink().replace("&export=download", "");
            Student student = Student.builder()
                    .studentName(studentRequest.getStudentName()) 
                    .studentCode(studentRequest.getStudentCode())
                    .studentImageUrl(reduceLink)
                    .build();
            studentRepository.save(student);
            // log.info("Student" + student.getStudentID() + "is saved");
            return mapToStudentResponse(student);
        } else {
            if (existStudent.isDelete() == true) {
                existStudent.setDelete(false);
                return editOneStudent(studentRequestString, existStudent.getStudentCode(), file);
            } else {
                throw new AppException(409, "Student is still exist");
            }
        }

    }

    public StudentResponse editOneStudent(String studentRequestString, String id, MultipartFile file)
            throws IOException, GeneralSecurityException {
        Student student = studentRepository.findByStudentCode(id);
        if (student == null) {
            // return mapToStudentResponse(student);
            throw new AppException(404, "Student not found");
        }
        if (!file.isEmpty()) {

            File converFile = convert(file);
            com.google.api.services.drive.model.File newGGDriveFile = new com.google.api.services.drive.model.File();
            newGGDriveFile.setParents(Collections.singletonList("1Hhxm5kjSu0L9wgfDTR67oxTAPUJH-wIS"))
                    .setName(file.getOriginalFilename());
            FileContent mediaContent = new FileContent("image/jpeg", converFile);
            com.google.api.services.drive.model.File fileW = googleDrive.getService().files()
                    .create(newGGDriveFile, mediaContent).setFields("id,webViewLink,webContentLink").execute();
            DeleteFolder(converFile);
            String reduceLink = fileW.getWebContentLink().replace("&export=download", "");
            ObjectMapper objectMapper = new ObjectMapper();
            StudentRequest studentRequest = objectMapper.readValue(studentRequestString, StudentRequest.class);
            student.setStudentName(studentRequest.getStudentName());
            student.setStudentCode(studentRequest.getStudentCode());
            student.setStudentImageUrl(reduceLink);
            studentRepository.save(student);

        } else {

            ObjectMapper objectMapper = new ObjectMapper();
            StudentRequest studentRequest = objectMapper.readValue(studentRequestString, StudentRequest.class);

            student.setStudentName(studentRequest.getStudentName());
            student.setStudentCode(studentRequest.getStudentCode());
            studentRepository.save(student);
        }
        return mapToStudentResponse(student);
    }

    public StudentResponse deleteOneStudent(String id) {
        Student student = studentRepository.findByStudentCode(id);
        if (student == null) {
            throw new AppException(404, "Student not found");
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
            fos.close();
        } catch (IOException e) {
            convFile = null;
        }

        return convFile;
    }

    public List<StudentResponse> getAllStudents() {
      
        List<Student> students = studentRepository.findAll();
        return students.stream().map(student -> mapToStudentResponse(student)).toList();
    }

    public void DeleteFolder(File myObj) {
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }
}
