package fr.lucasdechaumet.pokedexpriceserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class CardOfSet extends BaseEntity {

	private String idApi;
	
	private int normalCards;
	
	private int holoCards;
	
	private int reverseCards;
	
	@OneToOne
	private CardSafe cardSafe;
}
