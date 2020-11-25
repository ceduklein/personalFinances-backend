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
		boolean verifyUsername = repository.existsByUsername(user.getUsername());
		
		if(verifyEmail) {
			throw new  BusinessRulesException("E-mail já cadastrado.");
		}
		
		if(verifyUsername) {
			throw new BusinessRulesException("Nome de usuário já cadastrado.");
		}
		
		return repository.save(user);
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
