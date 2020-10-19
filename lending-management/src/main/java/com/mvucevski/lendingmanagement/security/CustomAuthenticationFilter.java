package com.mvucevski.lendingmanagement.security;

import com.mvucevski.lendingmanagement.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static com.mvucevski.sharedkernel.utils.SecurityConstants.*;

public class CustomAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    RestTemplate restTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = getJWTFromRequest(request);

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(jwt);
            HttpEntity <String> entity = new HttpEntity<String>(headers);

            User user = restTemplate.exchange(AUTH_PATH, HttpMethod.GET, entity, User.class).getBody();

            //User Roles
            Collection<? extends GrantedAuthority> userRoles = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, userRoles);

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch(Exception ex){
            logger.error("Could not set user authentication in security context");
        }

        filterChain.doFilter(request,response);
    }

    private String getJWTFromRequest(HttpServletRequest request){

        String bearerToken = request.getHeader(HEADER_STRING);

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)){
            return bearerToken.substring(7);
        }
        return null;
    }

}
