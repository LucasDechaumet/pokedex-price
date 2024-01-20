package fr.lucasdechaumet.pokedexpriceserver.security.auth;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.lucasdechaumet.pokedexpriceserver.model.User;
import fr.lucasdechaumet.pokedexpriceserver.repository.UserRepo;
import fr.lucasdechaumet.pokedexpriceserver.security.service.JwtService;
import fr.lucasdechaumet.pokedexpriceserver.security.token.Token;
import fr.lucasdechaumet.pokedexpriceserver.security.token.TokenRepo;
import fr.lucasdechaumet.pokedexpriceserver.security.token.TokenType;
import fr.lucasdechaumet.pokedexpriceserver.security.validation.EmailService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
	
	private final EmailService emailSender;
	
	public void register(@Valid RegisterRequest request) {
		try {
	    var user = User.builder()
	            .firstname(request.getFirstname())
	            .lastname(request.getLastname())
	            .nickname(request.getNickname())
	            .birthDate(request.getBirthDate())
	            .email(request.getEmail())
	            .password(passwordEncoder.encode(request.getPassword()))
	            .isActivated(false)
	            .role(request.getRole())
	            .build();
	        userRepo.save(user);
	        // we dont use any more the token in the registration because first we want that the user confirme
	        // our account in the mail
	        User savedUser = user;
	        var jwtToken = jwtService.generateValidationToken(user);
	        System.out.println("TOKEN : " + jwtToken);
	        System.out.println("MAIL : " + user.getEmail());
//	        var refreshToken = jwtService.generateRefreshToken(savedUser);
//	        saveUserToken(savedUser, jwtToken);
	        String link = "http://localhost:8080/sign/validation?token=" + jwtToken;
	        emailSender.send(user.getEmail(), user.getFirstname(), link);
		} catch (MailSendException e) {
			throw new RuntimeException("Le mail n'est pas correct", e);
	    } catch (Exception e) {
	    	System.out.println("==========================================");
	    	System.out.println(e);
	    	System.out.println("==========================================");
	        throw new RuntimeException("Une erreur s'est produite lors de l'enregistrement de l'utilisateur", e);
	    }
	}


	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		// now the user are connected because the email and password are correct	
		var user = userRepo.findByEmail(request.getEmail()).orElseThrow();
		if (!user.isActivated()) {
	        throw new RuntimeException("Le compte n'est pas activé.");
	    }
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveUserToken(user, jwtToken);
		return AuthenticationResponse.builder()
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
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


	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, StreamWriteException, DatabindException, java.io.IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String userEmail;
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		refreshToken = authHeader.substring(7);
		userEmail = jwtService.extractUsername(refreshToken);
		if (userEmail != null) {
			var user = this.userRepo.findByEmail(userEmail).orElseThrow();
			if (jwtService.isTokenValid(refreshToken, user)) {
				var accessToken = jwtService.generateToken(user);
				revokeAllUserTokens(user);
				saveUserToken(user, accessToken);
				var authResponse = AuthenticationResponse.builder()
						.accessToken(accessToken)
						.refreshToken(refreshToken)
						.build();
				new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
			}
		}
	}


	public void validation(String token) {
		if (jwtService.isTokenExpired(token)) {
			throw new RuntimeException("Votre email de confirmation à expiré");
		}
		User user = userRepo.findByEmail(jwtService.extractUsername(token)).orElseThrow();
		user.setActivated(true);
		userRepo.save(user);
	}
	
}
