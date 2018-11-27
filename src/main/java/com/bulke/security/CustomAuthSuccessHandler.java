package com.bulke.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.debug("Authentication successful.");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SiteUser siteUser = (SiteUser) auth.getPrincipal();

        String url = request.getParameter("url");

        if (url != null) {
            url = URLDecoder.decode(url, "UTF-8");
        } else {
            url = "";
        }
//
        String targetUrl = request.getContextPath() + url;
//        log.debug("url = " + url);
//
//        if (url.contains("?")) {
//            if (!url.contains("back")) {
//                targetUrl += "&back";
//            }
//        } else {
//            targetUrl += "?back";
//        }
        request.getSession(false).setAttribute("user", "(" + siteUser.getName() + ") " + siteUser.getUsername());

//        log.debug("Redirecting to page: " + targetUrl);
        response.sendRedirect(targetUrl);
    }
}
