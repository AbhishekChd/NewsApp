package com.example.abhishek.newsapp.old.contracts;

public final class BaseUrlContract {
    public final static int TOP_HEADLINES = 0;
    public final static int EVERYTHING = 1;

    public final static int HEADLINES_BUSINESS = 0;
    public final static int HEADLINES_ENTERTAINMENT = 1;
    public final static int HEADLINES_HEALTH = 2;
    public final static int HEADLINES_SCIENCE = 3;
    public final static int HEADLINES_SPORTS = 4;
    public final static int HEADLINES_TECHNOLOGY = 5;

    public static final String NEWS_URL = "https://newsapi.org/v2";

    // https://newsapi.org/v2/top-headlines?country=us
    public final static String TOP_HEADLINES_URL = "top-headlines";
    // https://newsapi.org/v2/everything?q=bitcoin
    public final static String EVERYTHING_URL = "everything";
    public final static String PARAM_QUERY = "q";
    public final static String PARAM_COUNTRY = "country";
    public final static String PARAM_CATEGORY = "category";

    public final static String PARAM_BUSINESS = "business";
    public final static String PARAM_ENTERTAINMENET = "entertainment";
    public final static String PARAM_HEALTH = "health";
    public final static String PARAM_SCIENCE = "science";
    public final static String PARAM_SPORTS = "sports";
    public final static String PARAM_TECHNOLOGY = "technology";

}
