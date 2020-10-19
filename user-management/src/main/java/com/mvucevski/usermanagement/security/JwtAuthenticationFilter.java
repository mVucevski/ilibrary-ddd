package com.mvucevski.usermanagement.security;

import com.mvucevski.usermanagement.domain.model.User;
import com.mvucevski.usermanagement.domain.model.UserId;
import com.mvucevski.usermanagement.application.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.mvucevski.usermanagement.security.SecurityConstants.HEADER_STRING;
import static com.mvucevski.usermanagement.security.SecurityConstants.TOKEN_PREFIX;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UsersService usersService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = getJWTFromRequest(request);

            if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt, request)){
                String userId = tokenProvider.getUserIdFromJWT(jwt);
                User userDetails = usersService.loadUserById(new UserId(userId));

                //User Roles
                Collection<? extends GrantedAuthority> userRoles = userDetails.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())).collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userRoles);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        }catch(Exception ex){
            logger.error("Could not set user authentication in security context", ex);
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
