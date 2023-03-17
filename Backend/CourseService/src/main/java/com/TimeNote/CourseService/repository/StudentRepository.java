package com.TimeNote.CourseService.repository;

import com.TimeNote.CourseService.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
