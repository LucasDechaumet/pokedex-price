interface sets {
  cardCount: {
    offical: number;
    total: number;
  };
  id: string;
  logo?: string;
  name: string;
  symbol?: string;
}

interface singleSerieResponse {
  id: string;
  logo?: string;
  name: string;
  sets: sets[];
}
