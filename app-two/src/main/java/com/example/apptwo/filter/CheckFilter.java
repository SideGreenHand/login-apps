package com.example.apptwo.filter;


import com.example.apptwo.utils.CasServerUtil;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Cookie[] cookies = request.getCookies();
        String tgt = "";
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("TGC")) {
                    tgt = cookie.getValue();
                    break;
                }
            }
        }
        if ("".equals(tgt)){
            response.setContentType("application/json");
            response.setStatus(200);
            PrintWriter writer = response.getWriter();
            writer.write("{\"code\":\"40003\",\"message\":\"user is not login\"}");
            return;
        }
        StringBuffer requestURL = request.getRequestURL();
        String st = CasServerUtil.getST(tgt, requestURL.toString());
        if (!st.startsWith("ST")){
            response.setContentType("application/json");
            response.setStatus(200);
            PrintWriter writer = response.getWriter();
            writer.write("{\"code\":\"40003\",\"message\":\"tgt:"+tgt+" is not found\"}");
            return;
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
