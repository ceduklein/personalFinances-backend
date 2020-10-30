package carlosklein.com.senaimypersonalfinancesapi.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import carlosklein.com.senaimypersonalfinancesapi.model.entity.User;

public class UserDetailsImpl implements UserDetails {

	 private static final long serialVersionUID = 1L;

	 private Long id;

	 private String name;

	 private String username;

	 private String email;
	 
	 @JsonIgnore
	 private String pass;
	 
	 private Collection<? extends GrantedAuthority> authorities;
	 
	 public UserDetailsImpl(Long id, String name, String username, String email, String pass,
             Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.pass = pass;
		this.authorities = authorities;
	 }
	 
	 public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRole().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPass(),
                authorities
        );
	 }
	 
	 @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

	    public Long getId() { return id; }

	    public String getName() { return name; }

	    public String getEmail() { return email; }

	    @Override
	    public String getPassword() { return pass; }

	    @Override
	    public String getUsername() { return username; }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o)
	            return true;
	        if (o == null || getClass() != o.getClass())
	            return false;
	        UserDetailsImpl user = (UserDetailsImpl) o;
	        return Objects.equals(id, user.id);
	    }
}
