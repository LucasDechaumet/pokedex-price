package fr.lucasdechaumet.pokedexpriceserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.lucasdechaumet.pokedexpriceserver.model.Serie;

public interface SerieRepo extends JpaRepository<Serie, Long> {

}
