package com.example.ehunter01.controller;

import com.example.ehunter01.entity.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserTokenController {
    final static Object obj = new Object();
    @RequestMapping("/getToken")
    public String getToken(@RequestBody User user){

        return "ok";
    }
}
