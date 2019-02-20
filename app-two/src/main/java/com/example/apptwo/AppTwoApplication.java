package com.example.apptwo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AppTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppTwoApplication.class, args);
    }

    @RequestMapping(value = "/rest/resource")
    public String resource(){
        return "登陆成功app-two";
    }
}

