package fr.lucasdechaumet.pokedexpriceserver.security.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/sign")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService service;

	// first we want to sign up so we pass the request in the service with the register method
	@PostMapping("/up")
	public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest request) {
		service.register(request);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/in")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(service.authenticate(request));
	}
	
	@PostMapping("/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws StreamWriteException, DatabindException, IOException, java.io.IOException {
		service.refreshToken(request, response);
	}
	
	@PostMapping("/validation")
	public ResponseEntity<Object> validation(@RequestParam String token) {
		service.validation(token);
		return ResponseEntity.ok().build();
	}
}
