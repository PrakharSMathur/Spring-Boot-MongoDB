package com.mongodb.springmongodb.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Student")
public class Student {
    @Id //unique value
    private int id;
    private String stName;
    private String stStream;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}