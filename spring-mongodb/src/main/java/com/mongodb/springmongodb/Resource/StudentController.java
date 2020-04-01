package com.mongodb.springmongodb.Resource;

import com.mongodb.springmongodb.Model.Student;
import com.mongodb.springmongodb.Repository.StudentRepository;
//import com.mongodb.springmongodb.Service.StudentService;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {
    @Autowired
    private StudentRepository repository;

    @RequestMapping("/")
    String home() {
        return "Student Data Home Page";
    }

    //POST method to add student
    @PostMapping("/addStudent")
    public Serializable saveStudent(@RequestBody Student student) {
        /*String j = student.getId();                           //for checking if ID has been already assigned
        if (repository.findById(j).isPresent() || j==null) {    // in case of manual entry of ID. Requires init
            return "ID already exists";                         // with a Entry in DB with  {"id":0,"stName":0"}
        } else {*/                                              // then it auto-increments by 1.

        Student count = repository.findById("0").get();         //Auto-Insertion of ID for new Student
        int value = Integer.parseInt(count.getStName());
        Student st1 = new Student();
        String val = "A" + String.valueOf(value + 1);
        st1.setId(val);
        st1.setStName(student.getStName());
        st1.setStStream(student.getStStream());
        repository.insert(st1);
        count.setStName(String.valueOf(value+1));
        repository.save(count);
        return "Added Student - \n" + "ID :" + String.valueOf(st1.getId()) + "\nName :" + String.valueOf(st1.getStName());
        // }
    }

    //GET method to fetch and display all students
    @GetMapping("/findAllStudents")
       public List<Student> getStudents() {
        return repository.findAll();
    }

    //GET method to fetch and display student by ID
    @GetMapping("/findAllStudents/{id}")
    public Optional<Student> getStudent(@PathVariable String id) {
        return repository.findById(id);
    }

    //PUT method to update Student data by ID
    @PutMapping("/updateStudent/{id}")
    public Student updateStudent(@RequestBody Student student, @PathVariable String id) {
        Student stu = new Student();
        stu.setId(student.getId());
        stu.setStName(student.getStName());
        stu.setStStream(student.getStStream());
        repository.deleteById(student.getId());
        repository.insert(stu);
        return stu;
    }

    //DELETE method for deleting student entry by ID
    @DeleteMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Student deleted : " + id;
        } else {
            return "Entry does not exist";
        }
    }
}
