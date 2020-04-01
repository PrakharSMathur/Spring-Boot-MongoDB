package com.mongodb.springmongodb.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Student")   //DB collection name
public class Student {
    @Id
    private String id;
    private String stName;
    private String stStream;

//    //Constructor
//    public Student(String id, String stName, String stStream) {
//        this.id = id;
//        this.stName = stName;
//        this.stStream = stStream;
//    }

    public Student() {

    }

    //Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getStStream() {
        return stStream;
    }

    public void setStStream(String stStream) {
        this.stStream = stStream;
    }

    //toString
    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", stName='" + stName + '\'' +
                ", stStream='" + stStream + '\'' +
                '}';
    }
}