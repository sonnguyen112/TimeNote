package com.TimeNote.CourseService.service;

import com.TimeNote.CourseService.dto.StudentResponse;
import com.TimeNote.CourseService.entities.Student;
import com.TimeNote.CourseService.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentResponse> studentResponses = students.stream().map(student -> mapToStudentResponse(student)).toList();
        return studentResponses;
    }

    private StudentResponse mapToStudentResponse(Student student) {
        return StudentResponse.builder()
                .studentID(student.getStudentID())
                .studentName(student.getStudentName())
                .studentCode(student.getStudentCode())
                .build();
    }
}
