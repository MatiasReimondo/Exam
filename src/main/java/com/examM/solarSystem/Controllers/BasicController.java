package com.examM.solarSystem.Controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @RequestMapping("/")
    public String hello(){
        return "Hello World";
    }
}
