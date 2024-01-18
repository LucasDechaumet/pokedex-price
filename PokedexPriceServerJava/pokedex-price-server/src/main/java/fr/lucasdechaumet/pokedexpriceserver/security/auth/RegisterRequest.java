package fr.lucasdechaumet.pokedexpriceserver.security.auth;

import java.time.LocalDate;

import fr.lucasdechaumet.pokedexpriceserver.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	private String firstname;
	
	private String lastname;
	
	private String nickname;
	
	private LocalDate birthDate;
	
	private String email;
	
	private String password;
	
	private Role role;
}
