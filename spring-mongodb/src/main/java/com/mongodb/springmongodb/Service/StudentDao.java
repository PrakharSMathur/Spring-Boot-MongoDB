package com.mongodb.springmongodb.Service;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;

@Repository
public interface StudentDao { 
	
}
    @DeleteMapping("/deleteStudent/{id}")
    public default String deleteStudent(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Student deleted : " + id;
        } else {
            return "Entry does not exist";
        }
}
