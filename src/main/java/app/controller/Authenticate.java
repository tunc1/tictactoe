package app.controller;

import app.response.TokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entity.User;
import app.response.ExceptionResponse;
import app.service.UserService;
import app.security.TokenService;

@RestController
@RequestMapping("/authenticate")
public class Authenticate
{
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private UserService userService;
    public Authenticate(AuthenticationManager authenticationManager,TokenService tokenService,UserService userService)
    {
        this.authenticationManager=authenticationManager;
        this.tokenService=tokenService;
        this.userService=userService;
    }
    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody User user)
    {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        try
        {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            User userDetails=userService.findByUsername(user.getUsername());
            String token=tokenService.create(userDetails);
            return new ResponseEntity<>(new TokenResponse(token),HttpStatus.OK);
        }
        catch(AuthenticationException e)
        {
            return new ResponseEntity<>(new ExceptionResponse(e.getMessage()),HttpStatus.UNAUTHORIZED);
        }
    }
}