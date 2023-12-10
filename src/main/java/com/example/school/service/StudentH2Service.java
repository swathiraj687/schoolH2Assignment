/*
 * You can use the following import statements
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.jdbc.core.JdbcTemplate;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 *
 */

// Write your code here
package com.example.school.service;

import java.util.*;

import com.example.school.model.Student;
import com.example.school.model.StudentRowMapper;
import com.example.school.repository.StudentRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StudentH2Service implements StudentRepository {
    @Autowired
    private JdbcTemplate db;

    @Override
    public ArrayList<Student> getAllStudents() {
        List<Student> studentList = db.query("SELECT * FROM student", new StudentRowMapper());
        ArrayList<Student> students = new ArrayList<>(studentList);
        return students;
    }

    @Override
    public Student getStudentById(int studentId) {
        try {
            Student student = db.queryForObject("SELECT * FROM student WHERE studentId =?", new StudentRowMapper(),
                    studentId);
            return student;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Student addStudent(Student student) {
        db.update("INSERT INTO student(studentName, gender, standard) VALUES (?, ?, ?)", student.getStudentName(),
                student.getGender(), student.getStandard());
        Student savedStudent = db.queryForObject(
                "SELECT * FROM student WHERE studentName=? and gender=? and standard=?",
                new StudentRowMapper(), student.getStudentName(), student.getGender(), student.getStandard());
        return savedStudent;
    }

    @Override
    public Student updateStudent(int studentId, Student student) {
        if (student.getStudentName() != null) {
            db.update("UPDATE student SET studentName = ? WHERE studentId = ?", student.getStudentName(), studentId);
        }
        if (student.getGender() != null) {
            db.update("UPDATE student SET gender = ? WHERE studentId = ?", student.getGender(), studentId);
        }
        if (student.getStandard() != 0) {
            db.update("UPDATE student SET standard = ? WHERE studentId = ?", student.getStandard(), studentId);
        }

        return getStudentById(studentId);
    }

    @Override
    public void deleteStudent(int studentId) {
        db.update("DELETE FROM student WHERE studentId = ?", studentId);
    }

}

