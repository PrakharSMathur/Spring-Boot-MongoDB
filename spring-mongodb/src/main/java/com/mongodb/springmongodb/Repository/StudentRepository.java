package com.mongodb.springmongodb.Repository;

import com.mongodb.springmongodb.Model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student,Integer> {

}
