package com.TimeNote.CourseService.service;

import com.TimeNote.CourseService.dto.AddStudentToCourseWebClientRequest;
import com.TimeNote.CourseService.dto.CourseDetailRequest;
import com.TimeNote.CourseService.dto.CourseDetailResponse;
import com.TimeNote.CourseService.entities.*;
import com.TimeNote.CourseService.exceptions.AppException;
import com.TimeNote.CourseService.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseDetailService {
    private final CourseDetailRepository courseDetailRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;
    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper= new ObjectMapper();


    @Autowired
    public CourseDetailService(CourseDetailRepository courseDetailRepository, CourseRepository courseRepository, StudentRepository studentRepository, LecturerRepository lecturerRepository, RestTemplate restTemplate) {
        this.courseDetailRepository = courseDetailRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.lecturerRepository = lecturerRepository;
        this.restTemplate = restTemplate;
    }

    public CourseDetailResponse addCourseDetail(CourseDetailRequest courseDetailRequest) throws JsonProcessingException {
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(objectMapper.writeValueAsString(
                AddStudentToCourseWebClientRequest.builder().course_id(String.valueOf(newCourseDetail.getCourseDetailID()))
                        .student_codes(courseDetailRequest.getStudentCodes())
                        .build()
        ), headers);
        System.out.println(request);
        String personResultAsJsonStr =
                restTemplate.postForObject("lb://attendance-service/attendance_api/student_management/",
                        request, String.class);
        System.out.println(personResultAsJsonStr);
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
