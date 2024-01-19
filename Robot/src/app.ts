import express from "express";
import { robotNews } from "./robot/newsdataAPI/robot";
import { robotPokemon } from "./robot/tcgdexAPI/robot";

const app = express();

app.listen(3000, () => {
  console.log("Server is running on port 3000");
  // robotPokemon();
  // robotNews();
});
