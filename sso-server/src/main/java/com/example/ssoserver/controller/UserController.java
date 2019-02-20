package com.example.ssoserver.controller;

import com.example.ssoserver.properties.SsoProperties;
import com.example.ssoserver.service.TgtServer;
import com.example.ssoserver.utils.CasServerUtil;
import com.example.ssoserver.vo.UserCheckResponse;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user/")
public class UserController{

    @Autowired
    private TgtServer tgtServer;

    /**
     * CAS 登录授权
     */
    @PostMapping("/login")
    public Object login(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String service = request.getParameter("service");

        System.out.println("username：" + username + "，password：" + password + "，service：" + service);

        // 1、获取 TGT
        String tgt = CasServerUtil.getTGT(username, password);
        System.out.println("TGT：" + tgt);

        // 2、获取 ST
        String st = CasServerUtil.getST(tgt, service);
        System.out.println("ST：" + st);

        if (tgt == null || st==null){
            return new ResponseEntity("用户名或密码错误。", HttpStatus.BAD_REQUEST);
        }

        // 3、设置cookie（1小时）
        Cookie cookie = new Cookie(SsoProperties.cookieName, username + "@" + tgt);
        cookie.setMaxAge(Integer.valueOf(SsoProperties.cookieValidTime));             // Cookie有效时间
        cookie.setPath("/");                       // Cookie有效路径
        cookie.setHttpOnly(true);                  // 只允许服务器获取cookie
        cookie.setDomain("server.com");
        response.addCookie(cookie);

        // 4、将当前用户的TGT信息存储在Redis上
        tgtServer.setTGT(username, tgt, Integer.valueOf(SsoProperties.cookieValidTime));

        // 5、302重定向最后授权
        String redirectUrl = service + "?ticket=" + st;
        System.out.println("redirectUrl:" + redirectUrl);

        return "redirect:" + redirectUrl;
    }

    /**
     * 检查用户是否登录过
     */
    @RequestMapping("/check")
    @ResponseBody
    public String checkLoginUser(HttpServletRequest request) throws Exception {

        String service = request.getParameter("service");
        String callback = request.getParameter("callback");
        Cookie[] cookies = request.getCookies();
        String username = null;
        String tgt = null;

        UserCheckResponse result = new UserCheckResponse();

        if (cookies != null) {
            System.out.println(new Gson().toJson(cookies));

            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(SsoProperties.cookieName)) {
                    username = cookie.getValue().split("@")[0];
                    tgt = cookie.getValue().split("@")[1];
                    break;
                }
            }

            if (username != null) {
                // 获取Redis值
                String value = tgtServer.getTGT(username);
                System.out.println("Redis value：" + value);

                // 匹配Redis中的TGT与Cookie中的TGT是否相等
                if (tgt.equals(value)) {
                    // 获取 ST
                    String st = CasServerUtil.getST(tgt, service);
                    System.out.println("ST：" + st);

                    result.setStatus(1);
                    result.setData(service + "?ticket=" + st);
                }
            }
        }

        String tmp = new Gson().toJson(result);
        System.out.println("result：" + tmp);

        return tmp;
    }

    /**
     * 因为TGT在SSO服务端维护，并不在CAS-Server，所以只需要想办法把redis中匹配的tgt信息删除即可。
     */
    @GetMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Cookie[] cookies = request.getCookies();
        String username = null;
        String tgt = null;

        if (cookies != null) {
            System.out.println(new Gson().toJson(cookies));

            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(SsoProperties.cookieName)) {
                    username = cookie.getValue().split("@")[0];
                    tgt = cookie.getValue().split("@")[1];
                    break;
                }
            }

            if (username != null) {
                // 获取Redis值
                String value = tgtServer.getTGT(username);
                System.out.println("Redis value：" + value);

                // 匹配Redis中的TGT与Cookie中的TGT是否相等
                if (tgt.equals(value)) {
                    // 删除TGT
                    tgtServer.delTGT(username);
                }
            }
        }

        System.out.println("callback：" + callback);
        String tmp = callback + "({'code':'0','msg':'登出成功'})";
        System.out.println("result：" + tmp);

        return null;
    }
}
