package com.TimeNote.CourseService.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppException extends RuntimeException{
    private int code;
    private String message;
}
