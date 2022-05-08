package app.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class BeanConfigTest
{
    @Test
    void passwordEncoder()
    {
        PasswordEncoder passwordEncoder=new BeanConfig().passwordEncoder();
        Assertions.assertEquals(passwordEncoder.getClass(),BCryptPasswordEncoder.class);
    }
}