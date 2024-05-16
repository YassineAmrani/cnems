package com.cnems.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class PasswordUtils {

    private  final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public  String encryptPassword(String password) {return passwordEncoder.encode(password);
    }

    public  boolean compare(String IncomingPassword, String DBPassword) {
        return passwordEncoder.matches(IncomingPassword, DBPassword);
    }
}
