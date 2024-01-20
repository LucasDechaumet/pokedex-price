package fr.lucasdechaumet.pokedexpriceserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.lucasdechaumet.pokedexpriceserver.model.Trainer;

public interface TrainerRepo extends JpaRepository<Trainer, Long> {

}
