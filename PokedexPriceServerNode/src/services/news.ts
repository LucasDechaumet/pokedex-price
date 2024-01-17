import prisma from "../prisma";

export async function getAllNews() {
  try {
    const response = await prisma.news.findMany({
      orderBy: {
        pubDate: "asc",
      },
    });
    return response;
  } catch (error) {
    console.error(error);
    return [];
  }
}
