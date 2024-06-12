package com.cnems.filter;

import com.cnems.entities.User;
import com.cnems.enums.Roles;
import com.cnems.repositories.UserRepository;
import com.cnems.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenFilter implements Filter {

    @Autowired
    UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        JwtUtils jwtUtils = new JwtUtils();

        try {
            String token = request.getHeader("Authorization");

            if(token == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                Claims decodedToken = jwtUtils.decodeToken(token.split(" ")[1]);
                User user = userRepository.findByUsername(decodedToken.getSubject());

                if(user == null) response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                else {
                    if(request.getRequestURI().contains("/admin") && !user.getRole().contains(Roles.ADMIN.toString()))
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    request.setAttribute("userId", user.getId());
                }
            }

        } catch (ExpiredJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

        filterChain.doFilter(request, response);
    }
}
