package carlosklein.com.senaimypersonalfinancesapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import carlosklein.com.senaimypersonalfinancesapi.controller.dto.UserDTO;
import carlosklein.com.senaimypersonalfinancesapi.exception.BusinessRulesException;
import carlosklein.com.senaimypersonalfinancesapi.model.entity.User;
import carlosklein.com.senaimypersonalfinancesapi.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService service;
	
	@PostMapping
	public ResponseEntity save(@RequestBody UserDTO dto) {
		User user = User.builder()
				.name(dto.getName())
				.email(dto.getEmail())
				.pass(dto.getPass()).build();

		try {
			User savedUser = service.saveUser(user);
			return new ResponseEntity(savedUser, HttpStatus.CREATED);

		} catch (BusinessRulesException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/auth")
	public ResponseEntity authenticate(@RequestBody UserDTO dto) {
		try {
			User authUser = service.auth(dto.getEmail(), dto.getPass());
			return ResponseEntity.ok(authUser);
			
		} catch (BusinessRulesException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
