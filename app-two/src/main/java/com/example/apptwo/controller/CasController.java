package com.example.apptwo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class CasController {

    @Value("${cas.Server-logout-Url}")
    private String logoutUrl;

    @Value("${serverName}")
    private String serverName;
    //退出
    @RequestMapping("logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        if (serverName.endsWith("/")){
            serverName = serverName.substring(0, serverName.length() - 1);
        }
        serverName = serverName + request.getContextPath() + "/";

        return "redirect:" + logoutUrl + "?service=" + serverName;
    }
}
