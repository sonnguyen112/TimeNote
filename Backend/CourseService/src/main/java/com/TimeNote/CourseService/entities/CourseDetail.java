package com.TimeNote.CourseService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "CourseDetailTable")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long courseDetailID;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "course_id",
            referencedColumnName = "courseID"
    )
    private Course course;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "class_id",
            referencedColumnName = "classID"
    )
    private Class aClass;
    private List<LocalDateTime> timeStarts;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "CourseDetailStudentMap",
            joinColumns = @JoinColumn(
                    name = "course_detail_id",
                    referencedColumnName = "courseDetailID"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "student_id",
                    referencedColumnName = "studentID"
            )
    )
    private List<Student> students;
}
