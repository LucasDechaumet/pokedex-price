import {
  addArticle,
  deleteAllArticle,
  deleteArticle,
  getNumberOfArticleInDb,
  isInDb,
  removeLastArticle,
} from "./utils";

const URL = "https://newsdata.io/api/1/news?";
const API_KEY = process.env.APIKEY_NEWS;
const q = "pokemon";
const language = "fr";

export async function robotNews() {
  const response = await fetch(
    `${URL}apikey=${API_KEY}&qInTitle=${q}&language=${language}`
  );
  const data: NewsResponse = await response.json();
  const newNumber = data.results.length;
  const currentNumber = await getNumberOfArticleInDb();
  if (newNumber >= currentNumber) {
    await deleteAllArticle();
  } else {
    await deleteArticle(currentNumber - newNumber);
  }

  for (const article of data.results) {
    if (!(await isInDb(article))) {
      await addArticle(article);
    }
  }
}
