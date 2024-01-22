package fr.lucasdechaumet.pokedexpriceserver.service;

import java.security.Principal;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.lucasdechaumet.pokedexpriceserver.model.CardOfSet;
import fr.lucasdechaumet.pokedexpriceserver.model.Serie;
import fr.lucasdechaumet.pokedexpriceserver.model.SetOfSerie;
import fr.lucasdechaumet.pokedexpriceserver.model.Trainer;
import fr.lucasdechaumet.pokedexpriceserver.model.User;
import fr.lucasdechaumet.pokedexpriceserver.repository.CardOfSetRepo;
import fr.lucasdechaumet.pokedexpriceserver.repository.SerieRepo;
import fr.lucasdechaumet.pokedexpriceserver.repository.SetOfSerieRepo;
import fr.lucasdechaumet.pokedexpriceserver.repository.TrainerRepo;
import fr.lucasdechaumet.pokedexpriceserver.repository.UserRepo;
import fr.lucasdechaumet.pokedexpriceserver.request.CardRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class TrainerService {
	
	private final UserService userService;
	
	private final TrainerRepo trainerRepo;
	
	private final SerieRepo serieRepo;
	
	private final SetOfSerieRepo setOfSerieRepo;
	
	private final CardOfSetRepo cardOfSetRepo;
	
	public Trainer getTrainer(Principal principal) {
		User user = userService.getUser(principal);
		return user.getTrainer();
	}

	public void addCards(String serieId, String setId, Set<CardRequest> cards, Trainer trainer) {
		Serie serie = findOrCreateSerie(trainer, serieId);
	    
		SetOfSerie set = findOrCreateSetOfSerie(serie, setId);
		
		for (CardRequest cardRequest : cards) {
				
		String idApi = cardRequest.getIdApi();
		int normalCards = cardRequest.getNormalCards();
		int holoCards = cardRequest.getHoloCards();
		int reverseCards = cardRequest.getReverseCards();
				
		CardOfSet card = findOrCreateCardOfSet(set, idApi);
				
		card.setNormalCards(card.getNormalCards() + normalCards);
		card.setHoloCards(card.getHoloCards() + holoCards);
		card.setReverseCards(card.getReverseCards() + reverseCards);
		
		cardOfSetRepo.save(card);
		
		}
		setOfSerieRepo.save(set);
		serieRepo.save(serie);
		trainerRepo.save(trainer);
	}
	
	public void removeCards(String serieId, String setId, Set<CardRequest> cards, Trainer trainer) {
		Serie serie = findOrCreateSerie(trainer, serieId);
	    
		SetOfSerie set = findOrCreateSetOfSerie(serie, setId);
		
		for (CardRequest cardRequest : cards) {
			
			String idApi = cardRequest.getIdApi();
			int normalCards = cardRequest.getNormalCards();
			int holoCards = cardRequest.getHoloCards();
			int reverseCards = cardRequest.getReverseCards();
					
			CardOfSet card = findOrCreateCardOfSet(set, idApi);
					
			card.setNormalCards(card.getNormalCards() - normalCards);
			card.setHoloCards(card.getHoloCards() - holoCards);
			card.setReverseCards(card.getReverseCards() - reverseCards);
			
			cardOfSetRepo.save(card);
			
			}
			setOfSerieRepo.save(set);
			serieRepo.save(serie);
			trainerRepo.save(trainer);
	}
	
	public Serie findOrCreateSerie(Trainer trainer, String serieId) {
	    if (trainer.getSeries().isEmpty()) {
	        return createSerieAndSetIdApi(trainer, serieId);
	    }
	    return trainer.getSeries()
	        .stream()
	        .filter(s -> s.getIdApi().equals(serieId))
	        .findFirst()
	        .orElseGet(() -> createSerieAndSetIdApi(trainer, serieId));
	}

	private Serie createSerieAndSetIdApi(Trainer trainer, String IdApi) {
		Serie newSerie = new Serie();
	    newSerie.setIdApi(IdApi);
	    serieRepo.save(newSerie);
	    trainer.getSeries().add(newSerie);
	    trainerRepo.save(trainer);
	    return newSerie;
	}
	
	public SetOfSerie findOrCreateSetOfSerie(Serie serie, String setId) {
	    if (serie.getSets().isEmpty()) {
	        return createSetOfSerieAndSetIdApi(serie, setId);
	    }

	    return serie.getSets()
	        .stream()
	        .filter(s -> s.getIdApi().equals(setId))
	        .findFirst()
	        .orElseGet(() -> createSetOfSerieAndSetIdApi(serie, setId));
	}
	
	private SetOfSerie createSetOfSerieAndSetIdApi(Serie serie, String IdApi) {
		SetOfSerie newSet = new SetOfSerie();
		newSet.setIdApi(IdApi);
		setOfSerieRepo.save(newSet);
		serie.getSets().add(newSet);
		serieRepo.save(serie);
		return newSet;
	}
	
	public CardOfSet findOrCreateCardOfSet(SetOfSerie set, String cardId) {
	    if (set.getCards().isEmpty()) {
	        return createCardOfSetAndSetIdApi(set, cardId);
	    }

	    return set.getCards()
	        .stream()
	        .filter(s -> s.getIdApi().equals(cardId))
	        .findFirst()
	        .orElseGet(() -> createCardOfSetAndSetIdApi(set, cardId));
	}
	
	private CardOfSet createCardOfSetAndSetIdApi(SetOfSerie set, String IdApi) {
		CardOfSet newCard = new CardOfSet();
		newCard.setIdApi(IdApi);
		cardOfSetRepo.save(newCard);
		set.getCards().add(newCard);
		setOfSerieRepo.save(set);
		return newCard;
	}


	
}
