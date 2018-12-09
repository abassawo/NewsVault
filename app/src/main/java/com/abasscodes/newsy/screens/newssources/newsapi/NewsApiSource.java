package com.abasscodes.newsy.screens.newssources.newsapi;

//Pojo for dealing with sources from news-api.com
public enum NewsApiSource {

    WSJ("WSJ", "the-wall-street-journal"),
    BBC("BBC", "bbc-news"),
    ALJ("Al Jazeera", "a;-jazeera-english"),
    CNN("CNN", "cnn"),
    ABC("ABC", "abc-news"),
    BLOOM("Bloomberg", "bloomberg"),
    BUS_INSIDER("Business Insider", "business-insider"),
    BUZZFEED("Buzzfeed", "buzzfeed"),
    ESPN("Espn", "espn"),
    FIN_TIMES("Financial Times", "financial-times"),
    FORTUNE("Fortune", "Fortune"),
    GOOG("Google News", "google-news"),
    MASH("Mashable", "mashable");
//    NAT_GEO("National Geographic", "national-geographic"),
//    NBC_NEWS("NBC", "nbc-news"),
//    NEW_SCIENTIST("New Scientist", "new-scientist"),
//    NEWSWEEK("NewsWeek", "newsweek"),
//    NY_MAG("NY Magazine", "new-york-magazine"),
//    POLIICO("Politico", "politico"),
//    RECODE("Recode", "recode"),
//    REDDIT("Reddit", "reddit-r-all"),
//    REUTERS("Reuters", "reuters"),
//    TALKSPORT("TalkSport", "talksport"),
//    TECHCHRUNCH("TechCrunch", "techcrunch"),
//    TECHRADAR("TechRadar", "techradar"),
//    ECONOMIST("Economist", "the-economist"),
//    HUFFPO("Huffington Post", "the-huffington-post"),
//    TELEGRAPH("Telegraph", "the-telegraph"),
//    VERGE("The Verge", "the-verge"),
//    WASHPOST("Washington Post", "the-washington-post"),
//    TIME("Time", "time"),
//    VICE("Vice", "vice-news"),
//    WIRED("Wired", "wired");


    private String name, source;


    NewsApiSource(String title, String apiQuery) {
        this.name = title;
        this.source = apiQuery;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
