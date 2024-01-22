package fr.lucasdechaumet.pokedexpriceserver.service;

import java.security.Principal;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import fr.lucasdechaumet.pokedexpriceserver.model.CardOfSet;
import fr.lucasdechaumet.pokedexpriceserver.model.Serie;
import fr.lucasdechaumet.pokedexpriceserver.model.SetOfSerie;
import fr.lucasdechaumet.pokedexpriceserver.model.Trainer;
import fr.lucasdechaumet.pokedexpriceserver.model.User;
import fr.lucasdechaumet.pokedexpriceserver.repository.UserRepo;
import fr.lucasdechaumet.pokedexpriceserver.request.CardRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TrainerService {
	
	private final UserRepo userRepo;
	
	public User getUser(Principal principal) {
		User user = null;
		try {
			Authentication authentication = (Authentication) principal;
			Object principalObject = authentication.getPrincipal();
			user = (User) principalObject;
		} catch (Exception e) {
			System.out.println(e);
		}
		return user;
	}

	public void addCards(String serieId, String setId, Set<CardRequest> cards, User user) {
			//HERE org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role
			//fr.lucasdechaumet.pokedexpriceserver.model.Trainer.serie: could not initialize proxy - no Session
			Serie serie = findOrCreateSerie(user.getTrainer(), serieId);
			SetOfSerie set = findOrCreateSetOfSerie(serie, setId);
			serie.getSets().add(set);
			for (CardRequest cardRequest : cards) {
				
				String idApi = cardRequest.getIdApi();
				int normalCards = cardRequest.getNormalCards();
				int holoCards = cardRequest.getHoloCards();
				int reverseCards = cardRequest.getReverseCards();
				
				CardOfSet card = findOrCreateCardOfSet(set, idApi);
				
				card.setNormalCards(card.getNormalCards() + normalCards);
				card.setHoloCards(card.getHoloCards() + holoCards);
				card.setReverseCards(card.getReverseCards() + reverseCards);
				
				set.getCards().add(card);
			}
			userRepo.save(user);
	}
	
	public Serie findOrCreateSerie(Trainer trainer, String serieId) {
		Serie existSerie = trainer.getSerie().stream().filter(serie -> serie.getIdApi().equals(serieId)).findFirst().orElse(null);
		if (existSerie == null) {
			Serie newSerie = new Serie();
			trainer.getSerie().add(newSerie);
			newSerie.setIdApi(serieId);
			
			return newSerie;
		} else {
			return existSerie;
		}
	}
	
	public SetOfSerie findOrCreateSetOfSerie(Serie serie, String setId) {
		SetOfSerie existSet = serie.getSets().stream().filter(set -> set.getIdApi().equals(setId)).findFirst().orElse(null);
		if (existSet == null) {
			SetOfSerie newSet = new SetOfSerie();
			newSet.setIdApi(setId);
			return newSet;
		} else {
			return existSet;
		}
	}
	
	public CardOfSet findOrCreateCardOfSet(SetOfSerie set, String carId) {
		CardOfSet existCard = set.getCards().stream().filter(card -> card.getIdApi().equals(carId)).findFirst().orElse(null);
		if (existCard == null) {
			CardOfSet newCard = new CardOfSet();
			newCard.setIdApi(carId);
			return newCard;
		} else {
			return existCard;
		}
	}

}
