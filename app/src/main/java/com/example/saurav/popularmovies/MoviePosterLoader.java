package com.example.saurav.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.saurav.popularmovies.model.Movies;
import com.example.saurav.popularmovies.utils.QueryUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Saurav on 6/28/2018.
 */
public class MoviePosterLoader extends AsyncTaskLoader<List<Movies>> {
    private List<Movies> listOfMovies;

    public MoviePosterLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movies> loadInBackground() {
        try {
            URL url = QueryUtils.createUrl();
            String jsonResponse = QueryUtils.makeHttpRequest(url);
            listOfMovies = QueryUtils.parseJson(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfMovies;
    }
}
