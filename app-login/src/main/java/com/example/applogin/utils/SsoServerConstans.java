package com.example.applogin.utils;

import org.springframework.beans.factory.annotation.Value;

public class SsoServerConstans {

    public static String checkUrl;

    @Value("sso.checkUrl")
    public static void setCheckUrl(String checkUrl) {
        SsoServerConstans.checkUrl = checkUrl;
    }
}
