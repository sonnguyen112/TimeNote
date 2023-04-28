package com.TimeNote.CourseService.repository;

import com.TimeNote.CourseService.entities.RegClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegClassRepository extends JpaRepository<RegClass, Long> {
    RegClass findByClassCode(String classCode);
}
