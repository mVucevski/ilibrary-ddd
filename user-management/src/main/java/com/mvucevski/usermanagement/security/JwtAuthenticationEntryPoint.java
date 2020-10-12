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
        System.out.println("EDXXPR: " + expired);

        if (expired!=null){
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED,expired);

            UnauthorizedAccessResponse expiredToken =  new UnauthorizedAccessResponse("Expired token");
            //RuntimeException expiredToken = new RuntimeException("Expired token");
            String jsonExpiredToken = new Gson().toJson(expiredToken);

            response.setContentType("application/json");
            response.setStatus(401);
            response.getWriter().print(jsonExpiredToken);
        }else{
            InvalidLoginResponse loginResponse = new InvalidLoginResponse();
            //RuntimeException loginResponse = new RuntimeException("Invalid Login Response");
            String jsonLoginResponse = new Gson().toJson(loginResponse);

            response.setContentType("application/json");
            response.setStatus(401);
            response.getWriter().print(jsonLoginResponse);
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid Login details");
        }
    }

}
