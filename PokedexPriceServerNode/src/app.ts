import express from "express";
import cors from "cors";
import newsRouter from "./routes/newsRoute";
import pokemonRouter from "./routes/pokemonRoute";

const app = express();
const port = 3000;
app.use(express.json());
app.use(cors());

app.use("/news", newsRouter);
app.use("/pokemon", pokemonRouter);

app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});
