import { Router } from "express";
import { getAllNews } from "../services/news";

const newsRouter = Router();

newsRouter.get("/all", async (req, res) => {
  try {
    const news = await getAllNews();
    res.status(200).json(news);
  } catch (error: any) {
    res.status(500).json({ error: error.message });
  }
});

export default newsRouter;
