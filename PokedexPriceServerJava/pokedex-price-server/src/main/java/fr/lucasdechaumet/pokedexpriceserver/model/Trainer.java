package fr.lucasdechaumet.pokedexpriceserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Trainer extends BaseEntity {
	
	@OneToOne
	private User user;
}
