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
		validate(user);
		boolean verifyEmail = repository.existsByEmail(user.getEmail());
		
		if(verifyEmail) {
			throw new  BusinessRulesException("E-mail já cadastrado.");
		}
		
		return repository.save(user);
	}
	
	public User auth(String email, String pass) {
		Optional<User> user = repository.findByEmail(email);
		
		if(!user.isPresent() || !user.get().getPass().equals(pass)) {
			throw new BusinessRulesException("E-mail e/ou senha incorretos.");
		}

		return user.get();
	}
	
	public Optional<User> getById(Long id) {
		return repository.findById(id);
	}
	
	public void validate(User user) {
		if(user.getName() == null || user.getName().isEmpty()) {
			throw new BusinessRulesException("Por favor informe um nome válido.");
		}
		
		if(user.getEmail() == null || user.getEmail().isEmpty()) {
			throw new BusinessRulesException("Por favor informe um e-mail válido.");
		}
		
		if(user.getPass() == null || user.getPass().isEmpty()) {
			throw new BusinessRulesException("Por favor informe uma senha válida.");
		}
	}

}
