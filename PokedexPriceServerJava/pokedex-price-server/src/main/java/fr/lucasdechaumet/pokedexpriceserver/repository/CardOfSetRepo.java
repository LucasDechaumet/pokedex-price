package fr.lucasdechaumet.pokedexpriceserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.lucasdechaumet.pokedexpriceserver.model.CardOfSet;

public interface CardOfSetRepo extends JpaRepository<CardOfSet, Long> {

}
