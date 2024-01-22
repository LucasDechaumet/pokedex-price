package fr.lucasdechaumet.pokedexpriceserver.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class SetOfSerie extends BaseEntity {

	@OneToMany(cascade = CascadeType.ALL)
    private Set<CardOfSet> cards = new HashSet<>();

    private String idApi;
}
