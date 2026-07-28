package com.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
@RequestMapping("/api/usersApi")
public class MyController{
    
    private List<String> users = List.of("Prasunamba", "Meher", "Kom");

    @GetMapping
    public List<String> getUsers(){
        
        // this is returning JSON response
        return users; 
    }
}
