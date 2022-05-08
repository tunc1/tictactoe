package app.controller;

import app.entity.User;
import app.response.TokenResponse;
import app.security.TokenService;
import app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class Register
{
    private UserService userService;
    private TokenService tokenService;
    public Register(UserService userService,TokenService tokenService)
    {
        this.userService=userService;
        this.tokenService=tokenService;
    }
    @PostMapping
    @ResponseStatus(code=HttpStatus.CREATED)
    public TokenResponse register(@RequestBody User user)
    {
        userService.save(user);
        String token=tokenService.create(user);
        return new TokenResponse(token);
    }
}