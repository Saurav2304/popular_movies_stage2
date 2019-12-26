package com.example.saurav.popularmovies.utils;

import com.example.saurav.popularmovies.BuildConfig;

/**
Created by Saurav on 6/27/2018.
**/
public class API {
    public static final String BASE_URL = "https://api.themoviedb.org/3/movie";
    public static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    public static final String POPULAR_MOVIES = BASE_URL + "/popular?api_key="+ BuildConfig.API_KEY;
    public static final String TOP_RATED_MOVIES = BASE_URL + "/top_rated?api_key="+ BuildConfig.API_KEY;
    //public static final String RECOMMENDATIONS_URL = BASE_URL+"383498"+"/recommendations?api_key="+BuildConfig.API_KEY;
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    public static final String SMALL_IMG_SIZE = "w342/";
    public static final String BIG_IMG_SIZE = "w500/";
}
