package com.cnems.services;

import com.cnems.entities.User;
import com.cnems.enums.ExceptionStatusMessage;
import com.cnems.repositories.UserRepository;
import com.cnems.utils.JwtUtils;
import com.cnems.utils.PasswordUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordUtils passwordUtils;

    public String signIn(String username, String password) throws Exception {
        User user = userRepository.findByUsername(username);

        if(user == null) throw new Exception("NOT_FOUND");

        if(!passwordUtils.compare(password, user.getPassword())) {
            throw new Exception("Incorrect Password");
        }

        return jwtUtils.generateToken(username);
    }

    public String signUp(String username, String password) throws Exception {
        User checkUser = userRepository.findByUsername(username);

        if(checkUser != null) throw new Exception(ExceptionStatusMessage.ALREADY_EXISTS.toString());

        User user = new User(username, passwordUtils.encryptPassword(password));
        userRepository.save(user);

        return jwtUtils.generateToken(username);
    }
}
