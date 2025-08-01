package com.tej0897.msauth.security;


import com.tej0897.msauth.service.JwtService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter implements Filter {

    private final JwtService jwtService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String authHeader = httpServletRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            if (!jwtService.validateToken(token)){
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
