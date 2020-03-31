package com.mongodb.springmongodb.Resource;

import com.mongodb.springmongodb.Model.Student;
import com.mongodb.springmongodb.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {
    @Autowired
    private StudentRepository repository;
/*
    static String sequence() {
        int init = 1;
        String s_init = String.valueOf(init);
        String val = "A" + s_init;
        init += 1;
        return val;
    }
*/
    //POST method to add student
    @PostMapping("/addStudent")
    public String saveStudent(@RequestBody Student student) {
        int j = student.getId();
        if (repository.existsById(j) == false) {
            //student.setId(sequence());
            repository.save(student);
            return "Added Student : " + student.getId();
        } else {
            return "ID already exists";
        }
    }

    //GET method to fetch and display all students
    @GetMapping("/findAllStudents")
    public List<Student> getBooks() {
        return repository.findAll();
    }

    //GET method to fetch and display student by ID
    @GetMapping("/findAllStudents/{id}")
    public Optional<Student> getStudent(@PathVariable int id) {
        return repository.findById(id);
    }

    //DELETE method for deleting student entry by ID
    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Student deleted : " + id;
        } else {
            return "Entry does not exist";
        }
    }
}
