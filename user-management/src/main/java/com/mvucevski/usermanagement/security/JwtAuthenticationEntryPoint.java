package com.mvucevski.usermanagement.security;

import com.google.gson.Gson;
import com.mvucevski.usermanagement.exception.InvalidLoginResponse;
import com.mvucevski.usermanagement.exception.UnauthorizedAccessResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        final String expired = (String) request.getAttribute("expired");

        if (expired!=null){
            UnauthorizedAccessResponse expiredToken =  new UnauthorizedAccessResponse("Expired token");
            String jsonExpiredToken = new Gson().toJson(expiredToken);

            response.setContentType("application/json");
            response.setStatus(401);
            response.getWriter().print(jsonExpiredToken);
        }else{
            InvalidLoginResponse loginResponse = new InvalidLoginResponse();
            String jsonLoginResponse = new Gson().toJson(loginResponse);

            response.setContentType("application/json");
            response.setStatus(401);
            response.getWriter().print(jsonLoginResponse);
        }
    }

}
