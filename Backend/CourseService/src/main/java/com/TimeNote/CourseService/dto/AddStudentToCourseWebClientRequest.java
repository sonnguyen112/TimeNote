package com.TimeNote.CourseService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddStudentToCourseWebClientRequest {
    private String course_id;
    private List<String> student_codes;
}
