import { Router } from "express";
import { getAllSerie, getCardsOfSet, getSetsOfSerie } from "../services/pokemon";

const pokemonRouter = Router();

pokemonRouter.get("/serie", async (req, res) => {
  try {
    const response = await getAllSerie();
    res.status(200).json(response);
  } catch (error: any) {
    res.status(500).json({ error: error.message });
  }
});

pokemonRouter.get("/:serieId/sets", async (req, res) => {
  try {
    const idApi = req.params.serieId;
    const response = await getSetsOfSerie(idApi);
    res.status(200).json(response);
  } catch (error: any) {
    res.status(500).json({ error: error.message });
  }
});

pokemonRouter.get("/:setId/cards", async (req, res) => {
  try {
    const idApi = req.params.setId;
    const response = await getCardsOfSet(idApi);
    res.status(200).json(response);
  } catch (error: any) {
    res.status(500).json({ error: error.message });
  }
});

export default pokemonRouter;
