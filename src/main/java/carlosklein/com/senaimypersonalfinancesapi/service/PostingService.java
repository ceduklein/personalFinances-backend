package carlosklein.com.senaimypersonalfinancesapi.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	// Busca todos os resultados, com base nos filtros recebidos.
	public List<Posting> search(Posting filter) {
		Example example = Example.of(filter, ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}
	
	// Atualiza o lançamento recebido.
	public Posting update(Posting posting) {
		validate(posting);
		return repository.save(posting);
	}

	public void delete(Posting posting) {
		Optional<Posting> postingToDelete = getById(posting.getId());
		repository.deleteById(postingToDelete.get().getId());
		
	}
	
	// Recupera o lançamento pelo id
	public Optional<Posting> getById(Long id) {
		return repository.findById(id);
	}
	
	// Validação do lançamento
	public void validate(Posting posting) {
		if(posting.getDescription()== null || posting.getDescription().isEmpty()) {
			throw new BusinessRulesException("Por favor informe uma descrição válida.");
		}
		
		if(posting.getMonth() == null || posting.getMonth() < 1 || posting.getMonth() > 12) {
			throw new BusinessRulesException("Por favor informe um mês válido.");
		}
		
		if(posting.getYear() == null || posting.getYear().toString().length() != 4) {
			throw new BusinessRulesException("Por favor informe um ano válido.");
		}
		
		if(posting.getUser() == null || posting.getUser().getId() == null) {
			throw new BusinessRulesException("Por favor informe um usuário.");
		}
		
		if(posting.getValue() == null || posting.getValue().compareTo(BigDecimal.ZERO) < 1) {
			throw new BusinessRulesException("Por favor informe um valor válido.");
		}
		
		if(posting.getType() == null) {
			throw new BusinessRulesException("Por favor informe um tipo.");
		}	
	}
	
}


