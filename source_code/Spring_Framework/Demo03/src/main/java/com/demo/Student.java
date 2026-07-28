package com.demo;

import org.springframework.beans.factory.annotation.Autowired;

public class Student {
    String name;
    
   
    String id;

     public Student(String name, String age) {
    this.name = name;
    this.id = age;
  }

  // Method inside POJO class
  @Override
  public String toString() {

    // Print student class attributes
    return "Student{" + "name='" + name + '\'' + ", age='" + id + '\'' + '}';
  }


}
