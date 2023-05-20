package com.TimeNote.CourseService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "CourseDetailTable",uniqueConstraints = {@UniqueConstraint(columnNames = {"course_id"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseDetailID;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "course_id",
            referencedColumnName = "courseID"
    )
    private Course course;
    @Column(nullable = false)
    private String classCode;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "CourseDetailTimeStartMap",
            joinColumns = @JoinColumn(
                    name = "course_detail_id",
                    referencedColumnName = "courseDetailID"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "time_start_id",
                    referencedColumnName = "id"
            )
    )
    private List<TimeStart> timeStarts;
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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "CourseDetailLecturerMap",
            joinColumns = @JoinColumn(
                    name = "course_detail_id",
                    referencedColumnName = "courseDetailID"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "lecturer_id",
                    referencedColumnName = "lecturerID"
            )
    )
    private List<Lecturer> lecturers;
}
