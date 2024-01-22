package fr.lucasdechaumet.pokedexpriceserver.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Serie extends BaseEntity {

	@OneToMany(cascade = CascadeType.ALL)
    private Set<SetOfSerie> sets;

    private String idApi;
}
