package com.adev.vedacommunity.oauth.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;

@Service
public class CustomOauthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    PrintWriter writer = response.getWriter();
    writer.println("소셜 로그인에 실패했습니다. 관리자에게 문의하세요");
    }
}
