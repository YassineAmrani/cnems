package com.cnems.services;

import com.cnems.entities.User;
import com.cnems.enums.ExceptionStatusMessage;
import com.cnems.enums.Roles;
import com.cnems.exceptions.CnemsException;
import com.cnems.repositories.UserRepository;
import com.cnems.utils.JwtUtils;
import com.cnems.utils.PasswordUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordUtils passwordUtils;

    public String signIn(String username, String password) throws CnemsException {
        User user = userRepository.findByUsername(username);

        if(user == null) throw new CnemsException(404 ,"Not Found");

        if(!passwordUtils.compare(password, user.getPassword())) {
            throw new CnemsException(400 ,"Incorrect Password");
        }

        return jwtUtils.generateToken(username);
    }

    public String signUp(String username, String password, String email) throws CnemsException {
        User checkUser = userRepository.findByUsername(username);

        if(checkUser != null) throw new CnemsException(400, "User Already Exists");

        User user = new User(username, passwordUtils.encryptPassword(password), email, Roles.USER.toString(), new Date());
        userRepository.save(user);

        return jwtUtils.generateToken(username);
    }
}
