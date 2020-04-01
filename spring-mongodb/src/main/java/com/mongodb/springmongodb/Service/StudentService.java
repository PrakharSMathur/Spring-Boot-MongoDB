package com.mongodb.springmongodb.Service;

import com.mongodb.springmongodb.Model.Student;
import com.mongodb.springmongodb.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired

    StudentRepository repository;

    public String updateStudentService(Student student, String id) {
        if (!repository.findById(id).isPresent()) {
            return "ID does not exist";
        } else {
            Student stu = new Student();
            stu.setId(id);
            stu.setStName(student.getStName());
            stu.setStStream(student.getStStream());
            repository.deleteById(student.getId());
            repository.insert(stu);
            return "Updated :" + stu;
        }
    }

    public String deleteStudentService(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Student deleted : " + id;
        } else {
            return "Entry does not exist";
        }
    }


    public Optional<Student> getOne(String id) {
        return repository.findById(id);
    }

    public Serializable addStudentService(Student student) {
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
        count.setStName(String.valueOf(value + 1));
        repository.save(count);
        return "Added Student-\n" + "ID :" + String.valueOf(st1.getId()) + "\nName :" + String.valueOf(st1.getStName());
        // }
    }

    public List<Student> getAll() {
        return repository.findAll();
    }
}
