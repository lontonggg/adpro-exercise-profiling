package com.advpro.profiling.tutorial.service;

import com.advpro.profiling.tutorial.model.Student;
import com.advpro.profiling.tutorial.model.StudentCourse;
import com.advpro.profiling.tutorial.repository.StudentCourseRepository;
import com.advpro.profiling.tutorial.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author muhammad.khadafi
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public List<StudentCourse> getAllStudentsWithCourses() {
        List<StudentCourse> studentCourses = studentCourseRepository.findAll();
        Map<Long, Student> studentMap = new HashMap<>();

        List<Student> students = studentRepository.findAll();
        for (Student student : students) {
            studentMap.put(student.getId(), student);
        }

        List<StudentCourse> result = new ArrayList<>();
        for (StudentCourse studentCourse : studentCourses) {
            Student student = studentMap.get(studentCourse.getStudent().getId());
            StudentCourse newStudentCourse = new StudentCourse();
            newStudentCourse.setStudent(student);
            newStudentCourse.setCourse(studentCourse.getCourse());
            result.add(newStudentCourse);
        }
        return result;
    }

    public Optional<Student> findStudentWithHighestGpa() {
        return studentRepository.findStudentWithHighestGpa();
    }

    public String joinStudentNames() {
        return studentRepository.findAll().stream().map(Student::getName).collect(Collectors.joining(", "));
    }
}

