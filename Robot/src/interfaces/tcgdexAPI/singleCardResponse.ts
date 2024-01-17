interface singleCardResponse {
  category: string;
  id: string;
  image?: string;
  localId: string;
  name: string;
  rarity: string;
  set: {
    cardCount: {
      official: number;
      total: number;
    };
    id: string;
    logo: string;
    name: string;
    symbol: string;
  };
  variants: {
    firstEdition: boolean;
    holo: boolean;
    normal: boolean;
    reverse: boolean;
    wPromo: boolean;
  };
  hp: number;
  types: string[];
  stage: string;
  attacks: {
    cost: string[];
    name: string;
    effect: string;
    damage: string;
  }[];
  retreat: number;
  regulationMark: string;
  legal: {
    standard: boolean;
    expanded: boolean;
  };
}
