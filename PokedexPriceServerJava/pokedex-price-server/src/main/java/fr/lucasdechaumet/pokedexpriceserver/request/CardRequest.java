package fr.lucasdechaumet.pokedexpriceserver.request;

import lombok.Data;

@Data
public class CardRequest {

	private String idApi;
	
	private int normalCards;
	
	private int holoCards;
	
	private int reverseCards;
}
