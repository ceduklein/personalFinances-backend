package carlosklein.com.senaimypersonalfinancesapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carlosklein.com.senaimypersonalfinancesapi.exception.BusinessRulesException;
import carlosklein.com.senaimypersonalfinancesapi.model.entity.User;
import carlosklein.com.senaimypersonalfinancesapi.model.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public User saveUser(User user) {
		boolean verifyEmail = repository.existsByEmail(user.getEmail());
		
		if(verifyEmail) {
			throw new  BusinessRulesException("E-mail already registered.");
		}
		
		return repository.save(user);
	}
	
	public User auth(String email, String pass) {
		Optional<User> user = repository.findByEmail(email);
		
		if(!user.isPresent()) {
			throw new BusinessRulesException("Incorrect email or password");
		}
		
		if(!user.get().getPass().equals(pass)) {
			throw new BusinessRulesException("Incorrect email or password");
		}
		
		return user.get();
	}

}
