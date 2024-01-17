interface cards {
  id: string;
  image: string;
  localId: string;
  name: string;
}

interface singleSetResponse {
  cardCount: {
    firstEd: number;
    holo: number;
    normal: number;
    official: number;
    reverse: number;
    total: number;
  };
  cards: cards[];
  id: string;
  legal: {
    expanded: boolean;
    standard: boolean;
  };
  logo?: string;
  name: string;
  releaseDate: string;
  serie: {
    id: string;
    name: string;
  };
  symbol?: string;
}
