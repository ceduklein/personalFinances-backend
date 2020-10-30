package carlosklein.com.senaimypersonalfinancesapi.model.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import carlosklein.com.senaimypersonalfinancesapi.model.entity.Role;
import carlosklein.com.senaimypersonalfinancesapi.model.enums.ERole;

public interface RoleRepository extends CrudRepository<Role, Integer> {

	Optional<Role> findByName(ERole name);
}
