package com.TimeNote.CourseService.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {
    
    @Column(nullable = false)
    private String studentName;
    @Column(nullable = false)
    private String studentCode;
    private String studentImageUrl;

  

}
