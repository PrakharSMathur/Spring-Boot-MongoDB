package com.mongodb.springmongodb.Service;

import com.mongodb.springmongodb.Model.Student;
import com.mongodb.springmongodb.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository repository;

    public Student updateStudentService(Student student, String id){

        //This Code Snippet can do the same job which you might be trying to do
        //Check this out once if it works
        /*student.setId(id);
        repository.save(student);
        return student;*/

        Student stu = new Student();
        stu.setId(student.getId());
        stu.setStName(student.getStName());
        stu.setStStream(student.getStStream());
        repository.deleteById(student.getId());
        repository.insert(stu);
        return stu;


    }

}
