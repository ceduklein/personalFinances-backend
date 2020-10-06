package carlosklein.com.senaimypersonalfinancesapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import carlosklein.com.senaimypersonalfinancesapi.controller.dto.PostingDTO;
import carlosklein.com.senaimypersonalfinancesapi.exception.BusinessRulesException;
import carlosklein.com.senaimypersonalfinancesapi.model.entity.Posting;
import carlosklein.com.senaimypersonalfinancesapi.model.entity.User;
import carlosklein.com.senaimypersonalfinancesapi.model.enums.PostingStatus;
import carlosklein.com.senaimypersonalfinancesapi.model.enums.PostingType;
import carlosklein.com.senaimypersonalfinancesapi.service.PostingService;
import carlosklein.com.senaimypersonalfinancesapi.service.UserService;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("/api/postings")
@RequiredArgsConstructor
public class PostingController {

	@Autowired
	private PostingService service;
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity save(@RequestBody PostingDTO dto) {
		try {
			Posting entity = dtoConverter(dto);
			entity = service.save(entity);
			return new ResponseEntity(entity, HttpStatus.CREATED);
		} catch (BusinessRulesException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity search(
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "type", required = false) PostingType type,
			@RequestParam(value = "status", required = false) PostingStatus status,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam("user") Long userId) {
		
		Posting filter = new Posting();
		filter.setDescription(description);
		filter.setMonth(month);
		filter.setYear(year);
		filter.setType(type);
		filter.setStatus(status);
		
		Optional<User> user = userService.getById(userId);
		if(!user.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Usuário não encontrado com o id informado.");
		}else {
			filter.setUser(user.get());
		}
		
		List<Posting> postings = service.search(filter);
		return ResponseEntity.ok(postings);	
	}
	
	@PutMapping("{id}")
	public ResponseEntity update(@PathVariable("id") Long id, @RequestBody PostingDTO dto) {
		Optional<Posting> recoveredPosting = service.getById(id);
		
		try {
			Posting updatedPosting = dtoConverter(dto);
			updatedPosting.setId(recoveredPosting.get().getId());
			service.update(updatedPosting);
			return ResponseEntity.ok(updatedPosting);
		} catch (BusinessRulesException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}	
	}
	
	private Posting dtoConverter(PostingDTO dto) {
		Posting posting = new Posting();
		posting.setId(dto.getId());
		posting.setDescription(dto.getDescription());
		posting.setYear(dto.getYear());
		posting.setMonth(dto.getMonth());
		posting.setValue(dto.getValue());
		
		Optional<User> user = userService.getById(dto.getUser());
		
		posting.setUser(user.get());
			
		if(dto.getType() != null) {
			posting.setType(PostingType.valueOf(dto.getType()));
		}

		if(dto.getStatus() != null) {
			posting.setStatus(PostingStatus.valueOf(dto.getStatus()));
		}

		return posting;
	}
}
