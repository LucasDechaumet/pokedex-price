package fr.lucasdechaumet.pokedexpriceserver;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.lucasdechaumet.pokedexpriceserver.model.Role;
import fr.lucasdechaumet.pokedexpriceserver.model.User;
import fr.lucasdechaumet.pokedexpriceserver.security.auth.AuthenticationService;
import fr.lucasdechaumet.pokedexpriceserver.security.auth.RegisterRequest;

@SpringBootApplication
public class PokedexPriceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokedexPriceServerApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {

			var user = RegisterRequest.builder()
					.firstname("Alexis")
					.lastname("Engrand")
					.nickname("Titoum")
					.birthDate(LocalDate.of(2001, 05, 19))
					.email("alexis@mail.com")
					.password("159753456852@Alexis")
					.role(Role.NEW_TRAINER)
					.build();
			String token = service.register(user);
			User savedUser = service.validation(token);
			savedUser.setActivated(true);
		};
	}
}
