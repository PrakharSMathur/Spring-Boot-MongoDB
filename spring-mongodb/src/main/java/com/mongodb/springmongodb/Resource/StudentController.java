package com.mongodb.springmongodb.Resource;

import com.mongodb.springmongodb.Model.Student;
import com.mongodb.springmongodb.Repository.StudentRepository;
//import com.mongodb.springmongodb.Service.StudentService;
import com.mongodb.springmongodb.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {
    @Autowired
    private StudentRepository repository;
    @Autowired
    StudentService studentService;

    @RequestMapping("/")
    String home() {
        return "Student Data Home Page";
    }

    //POST method to add student
    @PostMapping("/addStudent")
    public Serializable saveStudent(@RequestBody Student student) {
        return studentService.addStudentService(student);
    }

    //GET method to fetch and display all students
    @GetMapping("/findAllStudents")
    public List<Student> getStudents() {
        return studentService.getAll();
    }

    //GET method to fetch and display student by ID
    @GetMapping("/findAllStudents/{id}")
    public Optional<Student> getStudent(@PathVariable String id) {
        return studentService.getOne(id);
    }

    //PUT method to update Student data by ID
    @PutMapping("/updateStudent/{id}")
    public String updateStudent(@RequestBody Student student, @PathVariable String id) {
        return studentService.updateStudentService(student, id);
    }

    //DELETE method for deleting student entry by ID
    @DeleteMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable String id) {
        return studentService.deleteStudentService(id);
    }
}
