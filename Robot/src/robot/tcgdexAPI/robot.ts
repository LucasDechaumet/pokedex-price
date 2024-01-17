import { addCard, addSerie, addSet, isInDb } from "./utils";

export async function robotPokemon() {
  const idOfSeries: string[] = [];
  const idOfSets: string[] = [];
  const idOfCards: string[] = [];

  const response = await fetch("https://api.tcgdex.net/v2/fr/series");
  const data: listSeriesResponse[] = await response.json();
  data.forEach((element) => {
    idOfSeries.push(element.id);
  });
  for (const id of idOfSeries) {
    const response = await fetch(`https://api.tcgdex.net/v2/fr/series/${id}`);
    const serieData: singleSerieResponse = await response.json();
    if (!(await isInDb(id, "serie"))) {
      await addSerie(serieData);
    }
    const sets = serieData.sets;
    for (const set of sets) {
      idOfSets.push(set.id);
    }
  }
  for (const id of idOfSets) {
    const response = await fetch(`https://api.tcgdex.net/v2/fr/sets/${id}`);
    const setData: singleSetResponse = await response.json();
    if (!(await isInDb(id, "set"))) {
      await addSet(setData);
    }
    const cards = setData.cards;
    for (const card of cards) {
      idOfCards.push(card.id);
    }
  }
  for (const id of idOfCards) {
    const response = await fetch(`https://api.tcgdex.net/v2/fr/cards/${id}`);
    const cardData: singleCardResponse = await response.json();
    if (!(await isInDb(id, "card"))) {
      await addCard(cardData);
    }
  }
}
