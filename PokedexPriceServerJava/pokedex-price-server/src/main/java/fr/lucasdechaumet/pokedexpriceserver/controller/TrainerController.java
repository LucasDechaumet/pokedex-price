package fr.lucasdechaumet.pokedexpriceserver.controller;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.lucasdechaumet.pokedexpriceserver.model.Trainer;
import fr.lucasdechaumet.pokedexpriceserver.model.User;
import fr.lucasdechaumet.pokedexpriceserver.request.AddCardsRequest;
import fr.lucasdechaumet.pokedexpriceserver.request.CardRequest;
import fr.lucasdechaumet.pokedexpriceserver.service.TrainerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainer")
public class TrainerController {
	
	private final TrainerService trainerService;

	@PostMapping("/addCards")
	public ResponseEntity<Object> addCards(@RequestBody AddCardsRequest request, Principal principal) {
		Trainer trainer = trainerService.getTrainer(principal);
		String serieId = request.getSerieId();
		String setId = request.getSetId();
		Set<CardRequest> cards = request.getCards();
		trainerService.addCards(serieId, setId, cards, trainer);
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping("/removeCards")
	public ResponseEntity<Object> removeCards(@RequestBody AddCardsRequest request, Principal principal) {
		Trainer trainer = trainerService.getTrainer(principal);
		String serieId = request.getSerieId();
		String setId = request.getSetId();
		Set<CardRequest> cards = request.getCards();
		trainerService.removeCards(serieId, setId, cards, trainer);
		return ResponseEntity.ok().build();
	}
}
