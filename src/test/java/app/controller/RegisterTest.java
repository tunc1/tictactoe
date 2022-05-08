package app.controller;

import app.entity.User;
import app.response.TokenResponse;
import app.security.TokenService;
import app.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RegisterTest
{
    @InjectMocks
    Register register;
    @Mock
    UserService userService;
    @Mock
    TokenService tokenService;
    @Test
    void register()
    {
        String token="token";
        Mockito.when(tokenService.create(Mockito.any())).thenReturn(token);
        TokenResponse tokenResponse=register.register(new User());
        Assertions.assertEquals(tokenResponse.getToken(),token);
    }
}