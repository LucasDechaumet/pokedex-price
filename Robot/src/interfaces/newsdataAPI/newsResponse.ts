interface NewsResponse {
  status: string;
  totalResults: number;
  results: Results[];
  nextPage: string;
}

interface Results {
  article_id: string;
  title: string;
  link: string;
  keywords: string[];
  creator: string[];
  video_url?: string;
  description: string;
  content: string;
  pubDate: string;
  image_url: string;
  source_id: string;
  source_priority: number;
  country: string[];
  category: string[];
  language: string;
  ai_tags: string;
  sentiment: string;
  sentiment_stats: string;
}
