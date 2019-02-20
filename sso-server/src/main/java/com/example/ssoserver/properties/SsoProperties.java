package com.example.ssoserver.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SsoProperties {

    public static String tokenUrl;
    public static String cookieValidTime;
    public static String cookieName;

    @Value("${cas.tokenUrl}")
    public void setTokenUrl(String tokenUrl) {
        SsoProperties.tokenUrl = tokenUrl;
    }
    @Value("${cas.cookieValidTime}")
    public void setCookieValidTime(String cookieValidTime) {
        SsoProperties.cookieValidTime = cookieValidTime;
    }
    @Value("${cas.cookieName}")
    public void setCookieName(String cookieName) {
        SsoProperties.cookieName = cookieName;
    }
}
