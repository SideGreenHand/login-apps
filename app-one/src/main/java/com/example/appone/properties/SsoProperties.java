package com.example.appone.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SsoProperties {

    public static String tokenUrl = "https://cas.server.com:8443/cas/v1/tickets";

    @Value("${cas.tokenUrl}")
    public void setTokenUrl(String tokenUrl) {
        SsoProperties.tokenUrl = tokenUrl;
    }

}
