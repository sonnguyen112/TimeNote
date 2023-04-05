package com.TimeNote.CourseService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LecturerRequest {
    private String lecturerName;
    private String lecturerCode;
}
