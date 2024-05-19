package com.cnems.interceptor;

import com.cnems.entities.User;
import com.cnems.repositories.UserRepository;
import com.cnems.services.AuthenticationService;
import com.cnems.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Claims token = jwtUtils.decodeToken(request.getHeader("Authorization"));

        User user = userRepository.findByUsername(token.getSubject());

        if(user == null) return false;

        return System.currentTimeMillis() - token.getExpiration().getTime() <= 0;
    }
}
