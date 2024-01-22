package fr.lucasdechaumet.pokedexpriceserver.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class CardPending extends BaseEntity {

	private int normalCards;
	
	private int holoCards;
	
	private int reverseCards;
}
