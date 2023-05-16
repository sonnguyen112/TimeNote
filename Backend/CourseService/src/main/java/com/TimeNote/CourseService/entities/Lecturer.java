package com.TimeNote.CourseService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lecturer_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lecturerID;
    @Column(nullable = false)
    private String lecturerName;
    @Column(nullable = false, unique = true)
    private String lecturerCode;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isDelete ;
}
