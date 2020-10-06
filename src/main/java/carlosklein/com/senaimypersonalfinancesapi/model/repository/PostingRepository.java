package carlosklein.com.senaimypersonalfinancesapi.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import carlosklein.com.senaimypersonalfinancesapi.model.entity.Posting;

public interface PostingRepository extends JpaRepository<Posting, Long>{
	
}
