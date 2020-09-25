package carlosklein.com.senaimypersonalfinancesapi.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carlosklein.com.senaimypersonalfinancesapi.exception.BusinessRulesException;
import carlosklein.com.senaimypersonalfinancesapi.model.entity.Posting;
import carlosklein.com.senaimypersonalfinancesapi.model.enums.PostingStatus;
import carlosklein.com.senaimypersonalfinancesapi.model.repository.PostingRepository;

@Service
public class PostingService {

	@Autowired
	private PostingRepository repository;
	
	public Posting save(Posting posting) {
		validate(posting);
		posting.setStatus(PostingStatus.PENDENTE);
		return repository.save(posting);
	}
	
	public Posting update(Posting posting) {
		// TODO 
		return null;
	}

	public void delete(Posting posting) {
		// TODO 
		
	}
	
	// Validação do lançamento
	public void validate(Posting posting) {
		if(posting.getDescription()== null || posting.getDescription().trim().equals("")) {
			throw new BusinessRulesException("Please inform a valid description.");
		}
		
		if(posting.getMonth() == null || posting.getMonth() < 1 || posting.getMonth() > 12) {
			throw new BusinessRulesException("Please inform a valid month.");
		}
		
		if(posting.getYear() == null || posting.getYear().toString().length() != 4) {
			throw new BusinessRulesException("Please inform a valid year.");
		}
		
		if(posting.getUser() == null || posting.getUser().getId() == null) {
			throw new BusinessRulesException("Please inform a user.");
		}
		
		if(posting.getValue() == null || posting.getValue().compareTo(BigDecimal.ZERO) < 1) {
			throw new BusinessRulesException("Please inform a valid amount.");
		}
		
		if(posting.getType() == null) {
			throw new BusinessRulesException("Please inform a type.");
		}	
	}
	
}


