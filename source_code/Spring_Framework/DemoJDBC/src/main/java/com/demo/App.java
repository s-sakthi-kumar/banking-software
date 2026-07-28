package com.demo;

import java.sql.SQLException;
import java.lang.ClassNotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * Hello world!
 *
 */
public class App 
{
     public static void main(String[] args) throws SQLException, ClassNotFoundException {
        
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        // Retrieve bean
        CustomerDAO customerDAO = context.getBean("customerDAO", CustomerDAO.class);

        // Call method to fetch cust records
        customerDAO.selectAllRows();
    }
}

