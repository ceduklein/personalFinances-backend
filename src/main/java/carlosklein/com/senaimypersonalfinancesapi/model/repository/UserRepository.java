package carlosklein.com.senaimypersonalfinancesapi.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import carlosklein.com.senaimypersonalfinancesapi.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);
	
	boolean existsByUsername(String username);
	
	Optional<User> findByUsername(String username);
}
