package com.example.applogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class AppLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppLoginApplication.class, args);
    }

    @GetMapping("/login")
    public String index(){
        return "login";
    }

    @GetMapping("/app")
    public String app(){
        return "app";
    }

    @GetMapping("/resource")
    @ResponseBody
    public String resource(){
        return "登陆成功app -login";
    }
}

