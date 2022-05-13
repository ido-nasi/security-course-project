package com.example.securitycourseproject.student;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v2/student")
public class StudentController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "ariel"),
            new Student(2, "yoav"),
            new Student(3, "linoy"),
            new Student(4, "derimer"),
            new Student(5, "liron")
    );


    @GetMapping(path = "{studentId}")
    public Student getStudents(@PathVariable Integer studentId) {
        return STUDENTS.stream()
                .filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Student with studentId: " + studentId + " doesn't exist."
                ));

    }

}
