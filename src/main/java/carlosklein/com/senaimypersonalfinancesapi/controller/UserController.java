package carlosklein.com.senaimypersonalfinancesapi.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import carlosklein.com.senaimypersonalfinancesapi.model.entity.Role;
import carlosklein.com.senaimypersonalfinancesapi.model.entity.User;
import carlosklein.com.senaimypersonalfinancesapi.model.enums.ERole;
import carlosklein.com.senaimypersonalfinancesapi.model.repository.RoleRepository;
import carlosklein.com.senaimypersonalfinancesapi.payload.request.LoginRequest;
import carlosklein.com.senaimypersonalfinancesapi.payload.request.SignUpRequest;
import carlosklein.com.senaimypersonalfinancesapi.payload.response.JwtResponse;
import carlosklein.com.senaimypersonalfinancesapi.payload.response.MessageResponse;
import carlosklein.com.senaimypersonalfinancesapi.security.WebSecurityConfig;
import carlosklein.com.senaimypersonalfinancesapi.security.jwt.JwtUtils;
import carlosklein.com.senaimypersonalfinancesapi.service.UserDetailsImpl;
import carlosklein.com.senaimypersonalfinancesapi.service.UserService;

@Import(WebSecurityConfig.class)
//@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	AuthenticationManager authenticationManager;
	
    @Autowired
    RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserService userService;
	
	@Autowired
	JwtUtils jwtUtils;
	
    @Bean
    public JwtUtils utils() { return new JwtUtils(); }  	

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    	Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPass()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = utils().generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getName(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }
    
    @PostMapping("/signup")
    public ResponseEntity<?> saveUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    	User user = User.builder()
    			.name(signUpRequest.getName())
    			.username(signUpRequest.getUsername())
    			.email(signUpRequest.getEmail())
    			.pass(encoder.encode(signUpRequest.getPass()))
    			.build();
    	
    	Set<Role> role = new HashSet();
    	
    	Role userRole = roleRepository.findByName(ERole.ROLE_USER)
    			.orElseThrow(() -> new RuntimeException("Error: Role not found"));
    	
    	role.add(userRole);
    	user.setRole(role);
    	userService.saveUser(user);
    	
    	return ResponseEntity.ok(new MessageResponse("Usuário cadastrado com sucesso."));
    }
}
