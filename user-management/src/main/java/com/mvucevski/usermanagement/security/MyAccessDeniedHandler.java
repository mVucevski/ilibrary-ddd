package com.mvucevski.usermanagement.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        System.out.println("MU ACCESS HANDLE CHECK CHECK");
        try {
            ObjectMapper mapper = new ObjectMapper();
            //TODO Fix this
            //UnauthorizedAccessResponse loginResponse = new UnauthorizedAccessResponse("Warning! This action is only for authorized employees!");
            RuntimeException loginResponse = new RuntimeException("Warning! This action is only for authorized employees!");
            String jsonResponse = new Gson().toJson(loginResponse);


            response.setContentType("application/json");
            response.getWriter().print(jsonResponse);
        } catch (Exception e) {
            System.out.println("MU ACCESS HANDLE CHECK CHECK: EXCEPTION: " + e.getMessage());
            throw new ServletException();
        }
    }
}
