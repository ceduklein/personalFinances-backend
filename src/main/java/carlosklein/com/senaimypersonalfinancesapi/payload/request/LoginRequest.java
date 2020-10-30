package carlosklein.com.senaimypersonalfinancesapi.payload.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

	@NotBlank
    private String username;

    @NotBlank
    private String pass;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String password) {
        this.pass = password;
    }
}
