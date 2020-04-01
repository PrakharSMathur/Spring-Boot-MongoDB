package com.mongodb.springmongodb.Resource;

import com.mongodb.springmongodb.Model.Student;
import com.mongodb.springmongodb.Repository.StudentRepository;
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

    /*
         int sequence() {
            int init = 6;
            int s_init = init;
            init += 1;
            return s_init;
        }
    */
    @RequestMapping("/home")
    String home2() {
        return "Hello World Home Page";
    }

    //POST method to add student
    @PostMapping("/addStudent")
    public Serializable saveStudent(@RequestBody Student student) {
        /*String j = student.getId();                               //for checking if ID has been already assigned 
        if (repository.findById(j).isPresent() || j==null) {        //in case of manual entry of ID 
            return "ID already exists";
        } else {*/
            int p = Math.toIntExact(repository.count());            //Auto-Insertion of ID for new Student 
            Student st1 = new Student();
            String val="A"+ String.valueOf(p+1);
            st1.setId(val);
            st1.setStName(student.getStName());
            st1.setStStream(student.getStStream());
            repository.insert(st1);
            return "Added Student : " + st1.getId();
       // }
    }

    //GET method to fetch and display all students
    @GetMapping("/findAllStudents")
    public List<Student> getBooks() {
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
        Student stu =new Student();
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
