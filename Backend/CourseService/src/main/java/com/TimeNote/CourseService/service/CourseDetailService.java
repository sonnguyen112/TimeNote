package com.TimeNote.CourseService.service;

import com.TimeNote.CourseService.dto.CourseDetailRequest;
import com.TimeNote.CourseService.dto.CourseDetailResponse;
import com.TimeNote.CourseService.entities.*;
import com.TimeNote.CourseService.exceptions.AppException;
import com.TimeNote.CourseService.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CourseDetailService {
    private final CourseDetailRepository courseDetailRepository;
    private final RegClassRepository regClassRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;

    @Autowired
    public CourseDetailService(CourseDetailRepository courseDetailRepository, RegClassRepository regClassRepository, CourseRepository courseRepository, StudentRepository studentRepository, LecturerRepository lecturerRepository) {
        this.courseDetailRepository = courseDetailRepository;
        this.regClassRepository = regClassRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.lecturerRepository = lecturerRepository;
    }

    public CourseDetailResponse addCourseDetail(CourseDetailRequest courseDetailRequest) {
        CourseDetail courseDetailExist = courseDetailRepository.getCourseDetailByClassCodeAndCourseCode(
                courseDetailRequest.getClassCode(), courseDetailRequest.getCourseCode());
        if (courseDetailExist != null){
            throw new AppException(409, "Course is exist");
        }
        RegClass classExist = regClassRepository.findByClassCode(courseDetailRequest.getClassCode());
        if (classExist == null){
            throw new AppException(404, "Class is not exist");
        }
        Course courseExist = courseRepository.findByCourseCode(courseDetailRequest.getCourseCode());
        if (courseExist == null){
            throw new AppException(404, "Course is not exist");
        }
        ArrayList<Student> students = new ArrayList<>();
        for (String studentCode : courseDetailRequest.getStudentCodes()){
            Student student = studentRepository.findByStudentCode(studentCode);
            if (student == null) throw new AppException(404, "There is exist an nonexistent student");
            students.add(student);
        }
        ArrayList<Lecturer> lecturers = new ArrayList<>();
        for (String lectureCode: courseDetailRequest.getLectureCodes()){
            Lecturer lecturer = lecturerRepository.findByLecturerCode(lectureCode);
            if (lecturer == null) throw new AppException(404, "There is exist an nonexistent lecturer");
            lecturers.add(lecturer);
        }
        CourseDetail newCourseDetail = CourseDetail.builder()
                .course(courseExist)
                .regClass(classExist)
                .timeStarts(courseDetailRequest.getTimeStarts())
                .students(students)
                .lecturers(lecturers)
                .build();
        courseDetailRepository.save(newCourseDetail);
        return mapToCourseDetailResponse(newCourseDetail);
    }

    private CourseDetailResponse mapToCourseDetailResponse(CourseDetail newCourseDetail) {
        return CourseDetailResponse.builder()
                .courseDetailId(newCourseDetail.getCourseDetailID())
                .courseCode(newCourseDetail.getCourse().getCourseCode())
                .courseName(newCourseDetail.getCourse().getCourseName())
                .build();
    }
}
