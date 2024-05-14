package com.cnems.endpoints;

import com.cnems.dto.SuccessMessage;
import com.cnems.enums.ExceptionStatusMessage;
import com.cnems.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.header.Header;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
public class AuthenticationEndpoint {

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/login")
    public ResponseEntity<SuccessMessage> login(@RequestBody String username, @RequestBody String password) {
        try {
            String jwt = authenticationService.signIn(username, password);
            return ResponseEntity.ok().body(new SuccessMessage(true, jwt));
        } catch(Exception e) {
            SuccessMessage successMessage = new SuccessMessage(false, e.getMessage());
            return ResponseEntity.internalServerError().body(successMessage);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        try {
            String jwt = authenticationService.signUp(username, password);
            return ResponseEntity.ok().header("token", jwt).build();
        } catch(Exception e) {
            if(e.getMessage().equals(ExceptionStatusMessage.NOT_FOUND.toString()))
                return ResponseEntity.notFound().build();

            if(e.getMessage().equals(ExceptionStatusMessage.WRONG_PASSWORD.toString()))
                return ResponseEntity.badRequest().build();

            else return ResponseEntity.internalServerError().build();
        }
    }
}
