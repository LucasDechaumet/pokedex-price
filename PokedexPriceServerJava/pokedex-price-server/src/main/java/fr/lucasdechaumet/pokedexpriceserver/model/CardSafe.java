package fr.lucasdechaumet.pokedexpriceserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class CardSafe extends BaseEntity {

	@OneToOne
	private CardPending cardPending;
	
	@OneToOne
	private CardConfirmed cardConfirmed;
}
