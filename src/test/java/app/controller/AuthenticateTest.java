package app.controller;

import app.entity.User;
import app.response.ExceptionResponse;
import app.response.TokenResponse;
import app.security.TokenService;
import app.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;

@ExtendWith(MockitoExtension.class)
class AuthenticateTest
{
    Authenticate authenticate;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    TokenService tokenService;
    @Mock
    UserService userService;
    User user;
    @BeforeEach
    void setUp()
    {
        authenticate=new Authenticate(authenticationManager,tokenService,userService);
        user=new User();
        user.setUsername("username");
        user.setPassword("password");
    }
    @Test
    void authenticate_successful()
    {
        String token="token";
        Mockito.when(tokenService.create(Mockito.any())).thenReturn(token);
        Mockito.when(userService.findByUsername(Mockito.any())).thenReturn(user);
        ResponseEntity<?> responseEntity=authenticate.authenticate(user);
        Assertions.assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
        Assertions.assertEquals(((TokenResponse)responseEntity.getBody()).getToken(),token);
    }
    @Test
    void authenticate_unsuccessful()
    {
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenThrow(AuthenticationExceptionStub.class);
        ResponseEntity<?> responseEntity=authenticate.authenticate(user);
        Assertions.assertEquals(responseEntity.getStatusCode(),HttpStatus.UNAUTHORIZED);
    }
}
class AuthenticationExceptionStub extends AuthenticationException
{
    public AuthenticationExceptionStub()
    {
        super(null);
    }
}