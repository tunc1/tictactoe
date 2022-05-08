package app.service;

import app.entity.User;
import app.exception.ConflictException;
import app.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest
{
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @BeforeEach
    void setUp()
    {
        userService=new UserService(userRepository,passwordEncoder);
    }
    @Test
    void save()
    {
        User user=new User();
        user.setUsername("username");
        user.setPassword("password");
        String encodedPassword="encodedPassword";
        Mockito.when(userRepository.existsByUsername(Mockito.any())).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any())).thenAnswer(invocationOnMock ->invocationOnMock.getArgument(0,User.class));
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn(encodedPassword);
        User saved=userService.save(user);
        Assertions.assertEquals(encodedPassword,saved.getPassword());
    }
    @Test
    void save_throwsConflictException()
    {
        User user=new User();
        user.setUsername("username");
        Mockito.when(userRepository.existsByUsername(Mockito.any())).thenReturn(true);
        Assertions.assertThrows(ConflictException.class,()->userService.save(user));
    }
    @Test
    void existsByUsername()
    {
        boolean exists=true;
        String username="username";
        Mockito.when(userRepository.existsByUsername(Mockito.any())).thenReturn(exists);
        boolean result=userService.existsByUsername(username);
        Assertions.assertEquals(exists,result);
    }
    @Test
    void findByUsername()
    {
        User user=new User();
        String username="username";
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(user);
        User found=userService.findByUsername(username);
        Assertions.assertEquals(user,found);
    }
}