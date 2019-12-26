package com.example.saurav.popularmovies.database;

import android.app.Application;
import android.support.annotation.NonNull;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.AndroidViewModel;

import com.example.saurav.popularmovies.model.Movies;

import java.util.List;

/**
 * Created by Saurav on 7/17/2018.
 **/
public class MoviesViewModel extends AndroidViewModel {

    private LiveData<List<Movies>> moviesList;

    public LiveData<List<Movies>> getMoviesList() {
        return moviesList;
    }

    public MoviesViewModel(@NonNull Application application){
        super(application);
        MoviesRoomDatabase database = MoviesRoomDatabase.getDatabaseInstance(this.getApplication());
        moviesList = database.moviesDAO().getAllMovies();
    }
}
