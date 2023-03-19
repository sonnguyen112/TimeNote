package com.TimeNote.CourseService.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.TimeNote.CourseService.entities.Student;

import java.util.List;

@Repository

public interface StudentRepository extends JpaRepository<Student, String>{
	//todo
	Student findByStudentCode(String studentCode);

	@Query(
			nativeQuery = true,
			value = "SELECT * FROM student_table WHERE is_delete = false"
	)
	List<Student> getAllStudents();
}
