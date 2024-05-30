package com.cnems.services;

import com.cnems.entities.User;
import com.cnems.mocks.UserMocks;
import com.cnems.repositories.UserRepository;
import com.cnems.services.AuthenticationService;
import com.cnems.utils.JwtUtils;
import com.cnems.utils.PasswordUtils;
import io.jsonwebtoken.Jwt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthenticationServiceTest {

    @InjectMocks
    AuthenticationService authenticationService;

    @Mock
    UserRepository userRepository;

    @Mock
    JwtUtils jwtUtils;

    @Mock
    PasswordUtils passwordUtils;

    UserMocks userMocks = new UserMocks();
    @Test
    @DisplayName("Login: Successful")
    public void testSuccessfulLogin(){
        when(userRepository.findByUsername(any())).thenReturn(UserMocks.getMockedUser());
        when(passwordUtils.compare("test", "test")).thenReturn(true);
        when(jwtUtils.generateToken(any())).thenReturn("this is a jwt token my boy!");

        Assertions.assertDoesNotThrow(() -> authenticationService.signIn("test", "test"));
    }

    @Test
    @DisplayName("Login: Wrong Password")
    public void testWrongPasswordLogin(){

        when(userRepository.findByUsername(any())).thenReturn(UserMocks.getMockedUser());
        when(passwordUtils.compare("test", "test")).thenReturn(false);

        Assertions.assertThrows(Exception.class,() -> authenticationService.signIn("test", "test"));
    }

    @Test
    @DisplayName("Login: Not Found")
    public void testNotFoundLogin(){

        when(userRepository.findByUsername(any())).thenReturn(null);

        Assertions.assertThrows(Exception.class,() -> authenticationService.signIn("test", "test"));
    }

    @Test
    @DisplayName("Registration: Successful")
    public void testSuccessfulRegistration(){
        String jwt = "this is a jwt token my boy!";

        when(userRepository.findByUsername("test")).thenReturn(null);
        when(userRepository.save(any())).thenReturn(null);
        when(jwtUtils.generateToken("test")).thenReturn("this is a jwt token my boy!");

        Assertions.assertDoesNotThrow(() -> authenticationService.signUp("test", "test", "test@test.com"));
    }

    @Test
    @DisplayName("Registration: Already Exists")
    public void testAlredyExistsRegistration(){

        when(userRepository.findByUsername("test")).thenReturn(UserMocks.getMockedUser());

        Assertions.assertThrows(Exception.class,() -> authenticationService.signUp("test", "test", "test@test.com"));
    }

    @Test
    @DisplayName("Registration: Missing Username or password")
    public void testMissingUsernameOrPasswordRegistration(){

        when(userRepository.findByUsername("test")).thenReturn(UserMocks.getMockedUser());

        Assertions.assertThrows(Exception.class,() -> authenticationService.signUp( "test", "", ""));
    }
}
