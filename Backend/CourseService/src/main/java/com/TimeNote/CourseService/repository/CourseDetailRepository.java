package com.TimeNote.CourseService.repository;

import com.TimeNote.CourseService.entities.CourseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseDetailRepository extends JpaRepository<CourseDetail, Long> {
    @Query(
            nativeQuery = true,
            value = """
                    select T1.*
                    from course_detail_table as T1
                    inner join course_table as T2 on T2.courseid = T1.course_id
                    where T1.class_code = ?1 and T2.course_code= ?2
                    """
    )
    CourseDetail getCourseDetailByClassCodeAndCourseCode(String classCode, String courseCode);
}
