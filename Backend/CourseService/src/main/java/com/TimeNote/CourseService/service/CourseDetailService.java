package com.TimeNote.CourseService.service;

import com.TimeNote.CourseService.dto.AddStudentToCourseWebClientRequest;
import com.TimeNote.CourseService.dto.CourseDetailRequest;
import com.TimeNote.CourseService.dto.CourseDetailResponse;
import com.TimeNote.CourseService.dto.GetAvaiableCourseRestTemplateResponse;
import com.TimeNote.CourseService.entities.*;
import com.TimeNote.CourseService.exceptions.AppException;
import com.TimeNote.CourseService.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CourseDetailService {
    private final CourseDetailRepository courseDetailRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;
    private final RestTemplate restTemplate;
    private final int DISTANCE_THRESHOLD = 50;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public CourseDetailService(CourseDetailRepository courseDetailRepository, CourseRepository courseRepository,
            StudentRepository studentRepository, LecturerRepository lecturerRepository, RestTemplate restTemplate) {
        this.courseDetailRepository = courseDetailRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.lecturerRepository = lecturerRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public CourseDetailResponse addCourseDetail(CourseDetailRequest courseDetailRequest)
            throws JsonProcessingException {
        CourseDetail courseDetailExist = courseDetailRepository.getCourseDetailByClassCodeAndCourseCode(
                courseDetailRequest.getClassCode(), courseDetailRequest.getCourseCode());
        if (courseDetailExist != null) {
            throw new AppException(409, "Course is exist");
        }
        Course courseExist = courseRepository.findByCourseCode(courseDetailRequest.getCourseCode());
        if (courseExist == null) {
            throw new AppException(404, "Course is not exist");
        }
        ArrayList<Student> students = new ArrayList<>();
        for (String studentCode : courseDetailRequest.getStudentCodes()) {
            Student student = studentRepository.findByStudentCode(studentCode);
            if (student == null)
                throw new AppException(404, "There is exist an nonexistent student");
            students.add(student);
        }
        ArrayList<Lecturer> lecturers = new ArrayList<>();
        for (String lectureCode : courseDetailRequest.getLectureCodes()) {
            Lecturer lecturer = lecturerRepository.findByLecturerCode(lectureCode);
            if (lecturer == null)
                throw new AppException(404, "There is exist an nonexistent lecturer");
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
                AddStudentToCourseWebClientRequest.builder()
                        .course_id(String.valueOf(newCourseDetail.getCourseDetailID()))
                        .student_codes(courseDetailRequest.getStudentCodes())
                        .build()),
                headers);
        System.out.println(request);
        String personResultAsJsonStr = restTemplate.postForObject(
                "lb://attendance-service/attendance_api/student_management/",
                request, String.class);
        System.out.println(personResultAsJsonStr);
        return mapToCourseDetailResponse(newCourseDetail);
    }

    private List<TimeStart> stringToTimeStart(List<String> timeStarts) {
        ArrayList<TimeStart> timeStartArrayList = new ArrayList<>();
        for (int i = 0; i < timeStarts.size(); i++) {
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
                .courseClass(newCourseDetail.getClassCode())
                .numOfStudent(newCourseDetail.getStudents().size())
                .build();
    }

    public List<CourseDetailResponse> getCourseDetailByTeacherCode(String userCode) {
        Lecturer lecturer = lecturerRepository.findByLecturerCode(userCode);
        if (lecturer == null) {
            throw new AppException(404, "Teacher not exist");
        }
        List<CourseDetail> courseByTeacherCode = new ArrayList<>();
        List<CourseDetail> allCourse = courseDetailRepository.findAll();
        for (int i = 0; i < allCourse.size(); i++) {
            if (allCourse.get(i).getLecturers().contains(lecturer)) {
                courseByTeacherCode.add(allCourse.get(i));
            }
        }
        return courseByTeacherCode.stream().map(e -> mapToCourseDetailResponse(e)).toList();
    }

    public List<CourseDetailResponse> getCourseDetailByStudentCode(String userCode, Double longitude, Double latitude)
            throws JsonMappingException, JsonProcessingException {
        Student student = studentRepository.findByStudentCode(userCode);
        if (student == null) {
            throw new AppException(404, "Student not exist");
        }
        List<CourseDetail> courseByStudentCode = new ArrayList<>();
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity("lb://attendance-service/attendance_api/course_activation/course_avaiable", String.class);
        List<GetAvaiableCourseRestTemplateResponse> avaiableCourse = objectMapper.readValue(responseEntity.getBody(),
                new TypeReference<List<GetAvaiableCourseRestTemplateResponse>>() {
                });
        System.out.println("InfoDebug:" + avaiableCourse.toString());
        List<String> courseIdAvaiableList = avaiableCourse.stream().map(e -> e.getCourse_id()).toList();
        List<String> coordList = avaiableCourse.stream().map(e -> e.getCoord()).toList();
        List<CourseDetail> allCourse = courseDetailRepository.findAll();
        for (int i = 0; i < allCourse.size(); i++) {
            if (allCourse.get(i).getStudents().contains(student)
                    && courseIdAvaiableList.contains(allCourse.get(i).getCourseDetailID().toString()) && distance(latitude.doubleValue(), longitude.doubleValue(), 
                    Double.parseDouble(Arrays.asList(coordList.get(courseIdAvaiableList.indexOf(allCourse.get(i).getCourseDetailID().toString())).split("-")).get(1))
                    ,Double.parseDouble(Arrays.asList(coordList.get(courseIdAvaiableList.indexOf(allCourse.get(i).getCourseDetailID().toString())).split("-")).get(0)), 'K')*1000 <= DISTANCE_THRESHOLD) {
                courseByStudentCode.add(allCourse.get(i));
                System.out.println("INFO GPS"+String.valueOf(distance(latitude.doubleValue(), longitude.doubleValue(), 
                Double.parseDouble(Arrays.asList(coordList.get(courseIdAvaiableList.indexOf(allCourse.get(i).getCourseDetailID().toString())).split("-")).get(1))
                ,Double.parseDouble(Arrays.asList(coordList.get(courseIdAvaiableList.indexOf(allCourse.get(i).getCourseDetailID().toString())).split("-")).get(0)), 'K')*1000));
            }
        }
        return courseByStudentCode.stream().map(e -> mapToCourseDetailResponse(e)).toList();
    }

    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
    /* :: This function converts decimal degrees to radians : */
    /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
    /* :: This function converts radians to decimal degrees : */
    /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
