package com.TimeNote.CourseService.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TimeNote.CourseService.entities.Student;

@Repository

public interface StudentRespository extends JpaRepository<Student, String>{
	//todo
	Student findByStudentCode(String studentCode);

} 
