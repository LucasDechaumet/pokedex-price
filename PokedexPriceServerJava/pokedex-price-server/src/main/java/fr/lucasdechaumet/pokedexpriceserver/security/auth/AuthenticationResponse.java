package fr.lucasdechaumet.pokedexpriceserver.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

	private String accessToken;
	
	private String refreshToken;
	
	private String nickname;
}
