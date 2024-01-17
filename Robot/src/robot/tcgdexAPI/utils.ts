import prisma from "../../prisma";

const WEBP = ".webp";
const HIGH = "/high.webp";

export async function isInDb(id: string, table: any): Promise<boolean> {
  try {
    const response = await (prisma[table] as any).findFirst({
      where: {
        idApi: id,
      },
    });
    return response ? true : false;
  } catch (error) {
    console.log(error);
    return true;
  }
}

export async function addSerie(data: singleSerieResponse) {
  await prisma.serie.create({
    data: {
      idApi: data.id,
      name: data.name,
    },
  });
}

export async function addSet(set: singleSetResponse) {
  const logoUrl = set.logo ? set.logo + WEBP : "null";
  console.log(logoUrl);
  await prisma.set.create({
    data: {
      idApi: set.id,
      name: set.name,
      serieId: set.serie.id,
      totalCards: set.cardCount.total,
      logo: logoUrl,
    },
  });
}

export async function addCard(card: singleCardResponse) {
  const imageUrl = card.image ? card.image + HIGH : "null";
  const type = card.types ? card.types[0] : null;
  console.log(card.id);
  console.log(imageUrl);
  await prisma.card.create({
    data: {
      category: card.category,
      idApi: card.id,
      name: card.name,
      image: imageUrl,
      rarity: card.rarity,
      setId: card.set.id,
      type: type,
      variants: card.variants,
    },
  });
}
