package com.TimeNote.CourseService.kafka;
import java.time.Instant;

import jakarta.servlet.FilterConfig;
import lombok.Data;


@Data
public class Message {
    // private byte byteArr[] ;
    
    private Instant time;
    private byte[] byteArray;
    private String studentCode;

    public void setTime(Instant time) {
        this.time = time;
    }
    public void setImage(byte[] byteArray){
        this.byteArray = byteArray;
    }
    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }
    

}
