package com.TimeNote.CourseService.repository;

import com.TimeNote.CourseService.entities.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    Lecturer findByLecturerCode(String lecturerCode);
}
