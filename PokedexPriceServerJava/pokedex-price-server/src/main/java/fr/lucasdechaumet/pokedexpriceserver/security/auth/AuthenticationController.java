package fr.lucasdechaumet.pokedexpriceserver.security.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/sign")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService service;

	// first we want to sign up so we pass the request in the service with the register method
	@PostMapping("/up")
	public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
		return ResponseEntity.ok(service.register(request));
	}
	
	@PostMapping("/in")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(service.authenticate(request));
	}
}
