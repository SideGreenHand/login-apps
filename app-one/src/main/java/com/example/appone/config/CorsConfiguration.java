package com.example.appone.config;

import com.example.appone.filter.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CorsConfiguration {

    @Bean
    public FilterRegistrationBean corsFilterBean(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CorsFilter());
        // 设定匹配的路径
        registration.addUrlPatterns("/*");
        /*Map<String,String> initParameters = new HashMap<String, String>();
        initParameters.put("cors.allowed.origins", "http://*.server.com");
        initParameters.put("cors.support.credentials", "true");
        registration.setInitParameters(initParameters);*/
        // 设定加载的顺序
        registration.setOrder(0);
        return registration;
    }

}
