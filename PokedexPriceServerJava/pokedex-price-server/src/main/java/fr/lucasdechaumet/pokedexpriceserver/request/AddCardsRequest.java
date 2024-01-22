package fr.lucasdechaumet.pokedexpriceserver.request;

import java.util.Set;

import lombok.Data;

@Data
public class AddCardsRequest {

	private String serieId;

	private String setId;
	
	private Set<CardRequest> cards;
}
