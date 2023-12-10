// Write your code here
package com.example.school.repository;

import java.util.*;
import com.example.school.model.Student;

public interface StudentRepository {
    ArrayList<Student> getAllStudents();

    Student getStudentById(int studentId);

    Student addStudent(Student student);

    Student updateStudent(int studentId, Student student);

    void deleteStudent(int studentId);
}
