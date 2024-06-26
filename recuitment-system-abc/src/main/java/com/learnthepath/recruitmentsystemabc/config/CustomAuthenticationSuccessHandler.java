package com.learnthepath.recruitmentsystemabc.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ADMIN")) {
            response.sendRedirect("/admin/home");
        } else if (roles.contains("LEADERSHIP")) {
            response.sendRedirect("/leadership/home");
        } else if (roles.contains("ENTERPRISE")) {
            response.sendRedirect("/enterprise/home");
        } else if (roles.contains("CANDIDATE")) {
            response.sendRedirect("/candidate/home");
        } else if (roles.contains("NONE")) {
            response.sendRedirect("/register-member/home");
        }
    }
}
