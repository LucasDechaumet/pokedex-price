package fr.lucasdechaumet.pokedexpriceserver;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.lucasdechaumet.pokedexpriceserver.model.Role;
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
			var admin = RegisterRequest.builder()
					.firstname("Lucas")
					.lastname("Dechaumet")
					.nickname("Lulu")
					.birthDate(LocalDate.of(2001, 07, 8))
					.email("lucas@mail.com")
					.password("159753456852@Lucas")
					.role(Role.ADMIN)
					.build();
			System.out.println("Admin token: " + service.register(admin).getAccessToken());

			var trainer = RegisterRequest.builder()
					.firstname("Alexis")
					.lastname("Engrand")
					.nickname("Titoum")
					.birthDate(LocalDate.of(2001, 05, 19))
					.email("alexis@mail.com")
					.password("159753456852@Alexis")
					.role(Role.NEW_TRAINER)
					.build();
			System.out.println("Manager token: " + service.register(trainer).getAccessToken());

		};
	}
}
