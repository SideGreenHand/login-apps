package com.example.apptwo.config;

import org.jasig.cas.client.authentication.AuthenticationRedirectStrategy;
import org.jasig.cas.client.util.CommonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public final class FacesCompatibleAuthenticationRedirectStrategy implements AuthenticationRedirectStrategy {
    private static final String FACES_PARTIAL_AJAX_PARAMETER = "javax.faces.partial.ajax";

    public FacesCompatibleAuthenticationRedirectStrategy() {
    }

    public void redirect(HttpServletRequest request, HttpServletResponse response, String potentialRedirectUrl) throws IOException {
        String xRequestedWith = request.getHeader("X-Requested-With");
        if ((CommonUtils.isNotBlank(xRequestedWith) && "XMLHttpRequest".equals(xRequestedWith.trim()))||CommonUtils.isNotBlank(request.getParameter(FACES_PARTIAL_AJAX_PARAMETER))) {
            response.setContentType("application/json");
            response.setStatus(200);
            PrintWriter writer = response.getWriter();
            writer.write("{\"casError\":\"403\",\"redirect\":\""+potentialRedirectUrl+"\"}");

        } else {
            response.sendRedirect(potentialRedirectUrl);
        }

    }
}
