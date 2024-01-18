package fr.lucasdechaumet.pokedexpriceserver.security.auth;

import java.time.LocalDate;

import fr.lucasdechaumet.pokedexpriceserver.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	@NotBlank
	@Size(min = 2, max = 16, message = "{jakarta.validation.constraints.Size.message}")
	private String firstname;
	
	@NotBlank
	@Size(min = 2, max = 16, message = "Le nom doit être entre 2 et 16 caractères")
	private String lastname;
	
	@NotBlank
	@Size(min = 2, max = 16, message = "Le nom d'utilisateur doit être entre 2 et 16 caractères")
	private String nickname;
	
	@Past
	private LocalDate birthDate;
	
	@Email
	@Column(unique = true)
	private String email;
	
	@Size(min = 8, max = 24, message = "Le mot de passe doit contenir entre 8 et 24 caractères")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
            message = "Le mot de passe doit contenir au moins un chiffre, un symbole, une majuscule et une minuscule")
	private String password;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Role role;
}
