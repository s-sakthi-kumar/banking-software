package com.demo;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        BeanFactory factory = new ClassPathXmlApplicationContext("bean-factory-demo.xml");
        Student newStudent = factory.getBean(Student.class);
        System.out.println(newStudent);
    }
}
