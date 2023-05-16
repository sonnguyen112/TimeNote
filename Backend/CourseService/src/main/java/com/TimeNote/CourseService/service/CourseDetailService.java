package com.TimeNote.CourseService.service;

import com.TimeNote.CourseService.dto.CourseDetailRequest;
import com.TimeNote.CourseService.dto.CourseDetailResponse;
import com.TimeNote.CourseService.entities.*;
import com.TimeNote.CourseService.exceptions.AppException;
import com.TimeNote.CourseService.repository.*;
import com.google.api.client.util.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseDetailService {
    private final CourseDetailRepository courseDetailRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;

    @Autowired
    public CourseDetailService(CourseDetailRepository courseDetailRepository, CourseRepository courseRepository, StudentRepository studentRepository, LecturerRepository lecturerRepository) {
        this.courseDetailRepository = courseDetailRepository;
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
                .classCode(courseDetailRequest.getClassCode())
                .timeStarts(stringToTimeStart(courseDetailRequest.getTimeStarts()))
                .students(students)
                .lecturers(lecturers)
                .build();
        courseDetailRepository.save(newCourseDetail);
        return mapToCourseDetailResponse(newCourseDetail);
    }

    private List<TimeStart> stringToTimeStart(List<String> timeStarts) {
        ArrayList<TimeStart> timeStartArrayList = new ArrayList<>();
        for (int i = 0; i < timeStarts.size(); i++){
            String[] splitTimeStart = timeStarts.get(i).split(" ");
            TimeStart timeStart = TimeStart.builder()
                    .startCourseTime(LocalTime.parse(splitTimeStart[1], DateTimeFormatter.ofPattern("H:m")))
                    .dayOfWeek(Integer.valueOf(splitTimeStart[0]))
                    .build();
            timeStartArrayList.add(timeStart);
        }
        return timeStartArrayList;
    }

    private CourseDetailResponse mapToCourseDetailResponse(CourseDetail newCourseDetail) {
        return CourseDetailResponse.builder()
                .courseDetailId(newCourseDetail.getCourseDetailID())
                .courseCode(newCourseDetail.getCourse().getCourseCode())
                .courseName(newCourseDetail.getCourse().getCourseName())
                .build();
    }
}
