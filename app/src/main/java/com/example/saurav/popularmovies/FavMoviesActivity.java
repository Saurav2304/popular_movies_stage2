package com.example.saurav.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.saurav.popularmovies.adapter.MoviesAdapter;
import com.example.saurav.popularmovies.database.MoviesRoomDatabase;
import com.example.saurav.popularmovies.database.MoviesViewModel;
import com.example.saurav.popularmovies.model.Movies;

import java.util.List;

/**
 * Created by $USER_NAME on 7/17/2018.
 **/
public class FavMoviesActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener {

    private static final String TAG = FavMoviesActivity.class.getSimpleName();
    private RecyclerView favouritesRv;
    private MoviesAdapter moviesAdapter;
    private MoviesRoomDatabase moviesRoomDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_movies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        favouritesRv = (RecyclerView) findViewById(R.id.fav_movies_rv);
        favouritesRv.setLayoutManager(new GridLayoutManager(FavMoviesActivity.this, 2));
        moviesRoomDatabase = MoviesRoomDatabase.getDatabaseInstance(getApplicationContext());
        setupViewModel();
    }

    private void setupViewModel() {
        MoviesViewModel viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        viewModel.getMoviesList().observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(@Nullable List<Movies> movies) {
                moviesAdapter = new MoviesAdapter(movies, FavMoviesActivity.this, FavMoviesActivity.this);
                favouritesRv.setAdapter(moviesAdapter);
            }
        });
    }

    @Override
    public void onListItemClick(int clickedItemIndex, List<Movies> moviesList) {
        Intent intent = new Intent(FavMoviesActivity.this, DetailsActivity.class);
        intent.putExtra("clicked_movie", moviesList.get(clickedItemIndex));
        startActivity(intent);
    }
}
