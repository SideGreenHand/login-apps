package com.example.apptwo.filter;

import org.jasig.cas.client.authentication.UrlPatternMatcherStrategy;

public class SimpleUrlPatternMatcherStrategy implements UrlPatternMatcherStrategy {
    @Override
    public boolean matches(String url) {
        return true;
    }

    @Override
    public void setPattern(String pattern) {

    }
}
