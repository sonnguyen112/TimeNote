package com.TimeNote.CourseService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "StudentTable")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private Long studentID;
    @Column(nullable = false)
    private String studentName;
    @Column(nullable = false, unique = true)
    private String studentCode;
    @Column(nullable = false)
    private String studentImageUrl;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isDelete;

}
