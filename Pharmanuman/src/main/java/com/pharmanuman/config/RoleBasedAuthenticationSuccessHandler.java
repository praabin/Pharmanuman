package com.pharmanuman.config;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

public class RoleBasedAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/index");
        } else if (roles.contains("ROLE_PHARMACY")) {
            response.sendRedirect("/pharmacy/index");
        } else if (roles.contains("ROLE_PC")) {
            response.sendRedirect("/pc/index");
        } else if (roles.contains("ROLE_STOCKIST")) {
            response.sendRedirect("/stockist/index");
        } else {
            response.sendRedirect("/default/index"); // Default URL for other roles
        }
    }
}
