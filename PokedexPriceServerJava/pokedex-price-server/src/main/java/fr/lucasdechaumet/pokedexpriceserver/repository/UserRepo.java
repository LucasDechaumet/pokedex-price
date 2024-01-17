package fr.lucasdechaumet.pokedexpriceserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import model.User;

public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
}
