package com.mongodb.springmongodb.Repository;

import com.mongodb.springmongodb.Model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student,Integer> {
}
