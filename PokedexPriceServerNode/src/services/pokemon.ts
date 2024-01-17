import prisma from "../prisma";

export async function getAllSerie() {
  try {
    const response = await prisma.serie.findMany();
    return response;
  } catch (error) {
    console.error(error);
    return [];
  }
}

export async function getSetsOfSerie(idApi: string) {
  try {
    const response = await prisma.set.findMany({
      where: {
        serieId: idApi,
      },
    });
    return response;
  } catch (error) {
    console.error(error);
    return [];
  }
}

export async function getCardsOfSet(idApi: string) {
  try {
    const response = await prisma.card.findMany({
      where: {
        setId: idApi,
      },
    });
    return response;
  } catch (error) {
    console.error(error);
    return [];
  }
}
