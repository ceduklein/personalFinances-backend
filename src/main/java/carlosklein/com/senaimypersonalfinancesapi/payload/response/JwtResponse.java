package carlosklein.com.senaimypersonalfinancesapi.payload.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtResponse {

	private String token;
	
    private String type = "Bearer";
    
    private Long id;
    
    @JsonIgnore
    private String username;
    
    private String name;
    
    @JsonIgnore
    private String email;
    
    @JsonIgnore
    private List<String> roles;

    public JwtResponse(String token, Long id, String name,  String username,  String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getRoles() {
        return roles;
    }
}
