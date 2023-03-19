package com.TimeNote.CourseService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "ClassTable")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long classID;
    @Column(unique = true, nullable = false)
    private String classCode;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "class_id",
            referencedColumnName = "classID"
    )
    private List<CourseDetail> courseDetails;
}
