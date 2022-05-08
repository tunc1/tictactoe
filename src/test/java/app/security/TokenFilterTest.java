package app.security;

import app.entity.User;
import app.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class TokenFilterTest
{
    @Mock
    TokenService tokenService;
    @Mock
    UserService userService;
    @Mock
    HttpServletRequest request;
    TokenFilter tokenFilter;

    @BeforeEach
    void setUp()
    {
        tokenFilter=new TokenFilter(tokenService,userService);
    }
    @Test
    void doFilterInternal_noAuthorizationHeader()
    {
        Mockito.when(request.getHeader(Mockito.eq("Authorization"))).thenReturn(null);
        Assertions.assertDoesNotThrow(()->tokenFilter.doFilterInternal(request,null,null));
    }
    @Test
    void doFilterInternal_noBearer()
    {
        Mockito.when(request.getHeader(Mockito.eq("Authorization"))).thenReturn("Token");
        Assertions.assertDoesNotThrow(()->tokenFilter.doFilterInternal(request,null,null));
    }
    @Test
    void doFilterInternal_notValid() throws ServletException,IOException
    {
        Mockito.when(request.getHeader(Mockito.eq("Authorization"))).thenReturn("Bearer Token");
        Mockito.when(tokenService.validate(Mockito.anyString())).thenReturn(false);
        tokenFilter.doFilterInternal(request,null,null);
        Mockito.verify(tokenService,Mockito.times(0)).get(Mockito.anyString(),Mockito.eq("username"));
    }
    @Test
    void doFilterInternal_userNotExists() throws ServletException,IOException
    {
        Mockito.when(request.getHeader(Mockito.eq("Authorization"))).thenReturn("Bearer Token");
        Mockito.when(tokenService.validate(Mockito.anyString())).thenReturn(true);
        Mockito.when(tokenService.get(Mockito.anyString(),Mockito.eq("username"))).thenReturn("username");
        Mockito.when(userService.existsByUsername(Mockito.anyString())).thenReturn(false);
        tokenFilter.doFilterInternal(request,null,null);
        Mockito.verify(tokenService,Mockito.times(0)).get(Mockito.anyString(),Mockito.eq("role"));
    }
    @Test
    void doFilterInternal_userExists() throws ServletException,IOException
    {
        User user=new User();
        Mockito.when(request.getHeader(Mockito.eq("Authorization"))).thenReturn("Bearer Token");
        Mockito.when(tokenService.validate(Mockito.anyString())).thenReturn(true);
        Mockito.when(userService.existsByUsername(Mockito.anyString())).thenReturn(true);
        Mockito.when(tokenService.get(Mockito.anyString(),Mockito.eq("username"))).thenReturn("username");
        Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(user);
        tokenFilter.doFilterInternal(request,null,null);
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        Assertions.assertEquals(authentication.getPrincipal(),user);
    }
}