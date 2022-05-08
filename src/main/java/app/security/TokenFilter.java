package app.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.entity.User;
import app.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class TokenFilter extends OncePerRequestFilter
{
    private TokenService tokenService;
    private UserService userService;
    public TokenFilter(TokenService tokenService,UserService userService)
    {
        this.tokenService=tokenService;
        this.userService=userService;
    }
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException,IOException
    {
        String authorization=request.getHeader("Authorization");
        if(authorization!=null)
        {
            if(authorization.contains("Bearer "))
            {
                String token=authorization.split("Bearer ")[1];
                if(tokenService.validate(token))
                {
                    String username=tokenService.get(token,"username");
                    if(userService.existsByUsername(username))
                    {
                        User user=userService.findByUsername(username);
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(user,null,null);
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }
        }
        if(filterChain!=null)
            filterChain.doFilter(request,response);
    }
}