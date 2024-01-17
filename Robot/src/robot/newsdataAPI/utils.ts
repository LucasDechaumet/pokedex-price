import prisma from "../../prisma";

export async function addArticle(article: Results | any) {
  try {
    await prisma.news.create({
      data: {
        article_id: article.article_id,
        title: article.title,
        link: article.link,
        video_url: article.video_url,
        description: article.description,
        pubDate: article.pubDate,
        image_url: article.image_url,
        source_priority: article.source_priority,
      },
    });
  } catch (error) {
    console.log(error);
  }
}

export async function deleteAllArticle() {
  try {
    await prisma.news.deleteMany();
  } catch (error) {
    console.log(error);
  }
}

export async function deleteArticle(number: number) {
  try {
    const articles = await prisma.news.findMany({
      orderBy: {
        pubDate: "asc",
      },
    });
    await deleteAllArticle();
    articles.splice(0, number);
    for (const article of articles) {
      await addArticle(article);
    }
  } catch (error) {
    console.log(error);
  }
}

export async function getNumberOfArticleInDb() {
  try {
    const response = await prisma.news.count();
    return response;
  } catch (error) {
    console.log(error);
    return 0;
  }
}

export async function isInDb(article: Results): Promise<boolean> {
  try {
    const response = await prisma.news.findFirst({
      where: {
        article_id: article.article_id,
      },
    });
    return response ? true : false;
  } catch (error) {
    console.log(error);
    return true;
  }
}

export async function removeLastArticle() {
  try {
    const lastArticle = await prisma.news.findFirst({
      orderBy: {
        pubDate: "asc",
      },
    });
    if (lastArticle) {
      await prisma.news.delete({
        where: {
          id: lastArticle.id,
        },
      });
    }
  } catch (error) {
    console.log(error);
  }
}
