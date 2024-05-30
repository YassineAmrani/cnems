package com.cnems.mocks;

import com.cnems.entities.User;
import com.cnems.utils.PasswordUtils;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.core.parameters.P;

import java.util.Date;

@TestComponent
public class UserMocks {


    public static User getMockedUser() {
        return new User(0, "test", "test","test@test.com", "USER", new Date());
    }
}
