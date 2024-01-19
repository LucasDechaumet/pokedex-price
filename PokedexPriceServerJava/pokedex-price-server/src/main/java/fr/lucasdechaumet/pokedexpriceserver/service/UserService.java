package fr.lucasdechaumet.pokedexpriceserver.service;

import java.security.Principal;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.lucasdechaumet.pokedexpriceserver.model.ChangePasswordRequest;
import fr.lucasdechaumet.pokedexpriceserver.model.User;
import fr.lucasdechaumet.pokedexpriceserver.repository.UserRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final PasswordEncoder encoder;
	
	private final UserRepo repo;
	
	public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
		var user = (User)((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
		
		if (!encoder.matches(request.getCurrentPassword(), user.getPassword())) {
			throw new IllegalStateException("Mauvais mot de passe");
		}
		if(!request.getNewPassword().equals(request.getConfirmationPassword())) {
			throw new IllegalStateException("Les mots de passe ne sont pas identique");
		}
		user.setPassword(encoder.encode(request.getNewPassword()));
		repo.save(user);
	}

}
