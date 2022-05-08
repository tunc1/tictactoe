package app.service;

import app.exception.ConflictException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import app.entity.User;
import app.repository.UserRepository;

@Service
public class UserService
{
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder)
	{
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
	}
	public User save(User user)
	{
		if(!userRepository.existsByUsername(user.getUsername()))
		{
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			return userRepository.save(user);
		}
		throw new ConflictException("Another user uses this username");
	}
	public boolean existsByUsername(String username)
	{
		return userRepository.existsByUsername(username);
	}
	public User findByUsername(String username)
	{
		return userRepository.findByUsername(username);
	}
}