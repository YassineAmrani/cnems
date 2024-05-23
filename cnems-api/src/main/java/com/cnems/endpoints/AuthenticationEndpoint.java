package com.cnems.endpoints;

import com.cnems.dto.SuccessMessage;
import com.cnems.enums.ExceptionStatusMessage;
import com.cnems.exceptions.CnemsException;
import com.cnems.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.oauth2.client.OAuth2ClientSecurityMarker;
import org.springframework.security.web.header.Header;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/auth")
public class AuthenticationEndpoint {

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/login")
    public ResponseEntity<SuccessMessage> login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        try {
            if((username == null || username.isEmpty() ) || (password == null || password.isEmpty()))
                return ResponseEntity.badRequest()
                        .body(new SuccessMessage(false, "Username and password are required."));

            String jwt = authenticationService.signIn(username, password);
            return ResponseEntity.ok().header("jwt", jwt).body(new SuccessMessage(true, "Welcome"));
        } catch(CnemsException e) {
            SuccessMessage successMessage = new SuccessMessage(false, e.getMessage());
            return ResponseEntity.status(e.getStatus()).body(successMessage);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessMessage> register(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password, @RequestParam(name = "email") String email) {
        try {
            if((username == null || username.isEmpty() ) || (password == null || password.isEmpty()))
                return ResponseEntity.badRequest()
                        .body(new SuccessMessage(false, "Username and password are required."));

            String jwt = authenticationService.signUp(username, password, email);
            return ResponseEntity.ok().header("token", jwt).body(new SuccessMessage(true, "User registered successfully."));
        } catch(CnemsException e) {
            SuccessMessage successMessage = new SuccessMessage(false, e.getMessage());
            return ResponseEntity.status(e.getStatus()).body(successMessage);
        }
    }
}
