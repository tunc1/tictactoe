package app.security;

import java.util.Date;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import app.entity.User;

@Component
public class TokenService
{
    @Value("${jwt.key}")
    private String key;
    public String create(User user)
    {
        long DAY=60*60*24*1000;
        return JWT.create()
                .withClaim("username",user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+DAY*30))
                .sign(Algorithm.HMAC512(key));
    }
    public boolean validate(String token)
    {
        try
        {
            JWT.require(Algorithm.HMAC512(key)).build().verify(token);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    public String get(String token,String name)
    {
        return JWT.decode(token).getClaim(name).asString();
    }
}
