package com.cnems.endpoints;

import com.cnems.dto.SuccessMessage;
import com.cnems.enums.ExceptionStatusMessage;
import com.cnems.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.oauth2.client.OAuth2ClientSecurityMarker;
import org.springframework.security.web.header.Header;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/auth")
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
        } catch(Exception e) {
            SuccessMessage successMessage = new SuccessMessage(false, e.getMessage());

            if(e.getMessage().equals(ExceptionStatusMessage.NOT_FOUND.toString()))
                return ResponseEntity.notFound().build();

            if(e.getMessage().equals(ExceptionStatusMessage.WRONG_PASSWORD.toString()))
                return ResponseEntity.badRequest().body(successMessage);

            return ResponseEntity.internalServerError().body(successMessage);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessMessage> register(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password, @RequestHeader String token) {
        try {
            if((username == null || username.isEmpty() ) || (password == null || password.isEmpty()))
                return ResponseEntity.badRequest()
                        .body(new SuccessMessage(false, "Username and password are required."));

            String jwt = authenticationService.signUp(username, password);
            return ResponseEntity.ok().header("token", jwt).body(new SuccessMessage(true, "User registered successfully."));
        } catch(Exception e) {

            SuccessMessage successMessage = new SuccessMessage(false, e.getMessage());

            if(e.getMessage().equals(ExceptionStatusMessage.ALREADY_EXISTS.toString()))
                return ResponseEntity.badRequest().body(successMessage);

            else return ResponseEntity.internalServerError().build();
        }
    }
}
