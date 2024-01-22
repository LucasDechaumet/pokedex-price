package fr.lucasdechaumet.pokedexpriceserver.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class SetOfSerie extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL)
    private Set<CardOfSet> cards;

    private String idApi;
}
