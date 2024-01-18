package fr.lucasdechaumet.pokedexpriceserver.security.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.lucasdechaumet.pokedexpriceserver.model.User;
import fr.lucasdechaumet.pokedexpriceserver.repository.UserRepo;
import fr.lucasdechaumet.pokedexpriceserver.security.config.JwtService;
import fr.lucasdechaumet.pokedexpriceserver.security.token.Token;
import fr.lucasdechaumet.pokedexpriceserver.security.token.TokenRepo;
import fr.lucasdechaumet.pokedexpriceserver.security.token.TokenType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepo userRepo;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	private final TokenRepo tokenRepo;
	
	public AuthenticationResponse register(@Valid RegisterRequest request) {
		var user = User.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.nickname(request.getNickname())
				.birthDate(request.getBirthDate())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(request.getRole())
				.build();
		userRepo.save(user);
		User savedUser = user;
		var jwtToken = jwtService.generateToken(user);
		saveUserToken(savedUser, jwtToken);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}


	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		// now the user are connected because the email and password are correct	
		var user = userRepo.findByEmail(request.getEmail()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		revokeAllUserTokens(user);
		saveUserToken(user, jwtToken);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}
	
	private void revokeAllUserTokens(User user) {
		var validUserTokens = tokenRepo.findAllValidTokenByUser(user.getId());
		if (validUserTokens.isEmpty()) {
			return;
		}
			validUserTokens.forEach(t -> {
				t.setExpired(true);
				t.setRevoked(true);
			});
			tokenRepo.saveAll(validUserTokens);
		}
	
	private void saveUserToken(User user, String jwtToken) {
		var token = Token.builder()
				.user(user)
				.token(jwtToken)
				.tokenType(TokenType.BEARER)
				.revoked(false)
				.expired(false)
				.build();
		tokenRepo.save(token);
	}
	
}
