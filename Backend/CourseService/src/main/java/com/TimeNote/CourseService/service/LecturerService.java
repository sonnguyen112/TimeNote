package com.TimeNote.CourseService.service;

import com.TimeNote.CourseService.dto.LecturerRequest;
import com.TimeNote.CourseService.dto.LecturerResponse;
import com.TimeNote.CourseService.entities.Lecturer;
import com.TimeNote.CourseService.exceptions.AppException;
import com.TimeNote.CourseService.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LecturerService {

    private final LecturerRepository lecturerRepository;

    @Autowired
    public LecturerService(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    public LecturerResponse addLecturer(LecturerRequest lecturerRequest) {
        Lecturer existedLecturer = lecturerRepository.findByLecturerCode(lecturerRequest.getLecturerCode());
        if (existedLecturer != null){
            throw new AppException(409, "Lecturer is existed");
        }
        Lecturer lecturer = Lecturer.builder()
                .lecturerName(lecturerRequest.getLecturerName())
                .lecturerCode(lecturerRequest.getLecturerCode())
                .build();
        lecturerRepository.save(lecturer);
        return mapToLecturerResponse(lecturer);
    }

    private LecturerResponse mapToLecturerResponse(Lecturer lecturer) {
        return LecturerResponse.builder()
                .lecturerName(lecturer.getLecturerName())
                .lecturerID(lecturer.getLecturerID())
                .lecturerCode(lecturer.getLecturerCode())
                .build();
    }
}
