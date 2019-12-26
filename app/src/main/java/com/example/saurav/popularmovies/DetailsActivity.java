package com.example.saurav.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.example.saurav.popularmovies.R;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.saurav.popularmovies.adapter.TrailerAdapter;
import com.example.saurav.popularmovies.database.MoviesRoomDatabase;
import com.example.saurav.popularmovies.fragments.ReviewsFragment;
import com.example.saurav.popularmovies.fragments.TrailerBottomSheetFragment;
import com.example.saurav.popularmovies.model.Movies;
import com.example.saurav.popularmovies.utils.API;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;



/**
 * Created by Saurav on 6/27/2018.
 */
public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();
    private String trailer_url;
    private MoviesRoomDatabase moviesRoomDatabase;
    private Movies selectedMovie;
    private List<String> trailerLinks;
    private TrailerAdapter trailerAdapter;
    private TextView movieTrailer;
    private String review_url;
    private String youtube_trailer_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView moviePosterImage = findViewById(R.id.movie_detail_poster_image);
        ImageView movieBackdropImage = findViewById(R.id.details_poster);
        final Button movieAddToFavBtn = findViewById(R.id.movie_detail_fav);
        TextView movieTitle = findViewById(R.id.detail_title);
        TextView movieOverview = findViewById(R.id.detail_synopsis);
        TextView movieReleaseDate = findViewById(R.id.detail_release_date);
        TextView movieVoteAverage = findViewById(R.id.details_vote);
        movieTrailer = findViewById(R.id.movie_detail_play_trailer);
        Button reviewBtn = findViewById(R.id.reviews_btn);
        moviesRoomDatabase = MoviesRoomDatabase.getDatabaseInstance(getApplicationContext());
        selectedMovie = (Movies) getIntent().getSerializableExtra("clicked_movie");
        trailer_url = API.BASE_URL + "/" + selectedMovie.getId() + "/videos?api_key=" + BuildConfig.API_KEY +"&language=en-US";
        review_url = API.BASE_URL +"/" +selectedMovie.getId()+ "/reviews?api_key="+ BuildConfig.API_KEY+"&language=en-US";
        String actualMovieBackdropPath = API.IMAGE_BASE_URL + API.BIG_IMG_SIZE + selectedMovie.getBackdropPath();
        Picasso.with(DetailsActivity.this).load(actualMovieBackdropPath).into(movieBackdropImage);
        String actualMoviePosterPath = API.IMAGE_BASE_URL + API.SMALL_IMG_SIZE + selectedMovie.getPosterPath();
        Picasso.with(DetailsActivity.this).load(actualMoviePosterPath).into(moviePosterImage);
        movieTitle.setText(selectedMovie.getTitle());
        movieOverview.setText(selectedMovie.getOverview());
        movieReleaseDate.setText(selectedMovie.getReleaseDate());
        movieVoteAverage.setText(String.valueOf(selectedMovie.getVoteAverage()));
        fetchTrailers();
        if (selectedMovie.isSaved()) {
            movieAddToFavBtn.setText(R.string.unsave);
        } else {
            movieAddToFavBtn.setText(R.string.save);
        }
        movieTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trailerLinks.size() > 1) {
                    showTrailerSheetFragment();
                }
                else if(trailerLinks.size()==0){
                    Toast.makeText(DetailsActivity.this, "There are no trailers for this movie currently.", Toast.LENGTH_SHORT).show();
                }
                else {
                    playTrailer(Uri.parse(trailerLinks.get(0)));
                }
            }
        });
        movieAddToFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedMovie.isSaved()) {
                    movieAddToFavBtn.setText(R.string.unsave);
                    addMovieToFavourites();
                } else {
                    movieAddToFavBtn.setText(R.string.save);
                    removeMovieFromFavourites();
                }
            }
        });
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReviewSheetFragment();
            }
        });
    }

    private void showReviewSheetFragment() {
        ReviewsFragment reviewsBottomSheetFragment = new ReviewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("REVIEW_URL",review_url);
        reviewsBottomSheetFragment.setArguments(bundle);
        reviewsBottomSheetFragment.show(getSupportFragmentManager(), reviewsBottomSheetFragment.getTag());
    }

    private void showTrailerSheetFragment() {
        TrailerBottomSheetFragment bottomSheetFragment = new TrailerBottomSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("TRAILER_LINKS", (ArrayList<String>) trailerLinks);
        bottomSheetFragment.setArguments(bundle);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }
    private void fetchTrailers() {
        trailerLinks = new ArrayList<>();
        AndroidNetworking.get(trailer_url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for (int i = 0; i < response.getJSONArray("results").length(); i++) {
                                youtube_trailer_key = response.getJSONArray("results").getJSONObject(i).getString("key");
                                trailerLinks.add(API.YOUTUBE_BASE_URL + youtube_trailer_key);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(DetailsActivity.this, anError.getErrorDetail() + ":Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void playTrailer(Uri parseTrailer) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, parseTrailer);
        startActivity(browserIntent);
    }

    private void removeMovieFromFavourites() {
        moviesRoomDatabase.moviesDAO().deleteMovieWithId(selectedMovie.getId());
        selectedMovie.setSaved(false);
        Toast.makeText(this, selectedMovie.getTitle() + " is removed from favourites", Toast.LENGTH_SHORT).show();
    }

    private void addMovieToFavourites() {
        selectedMovie.setSaved(true);
        moviesRoomDatabase.moviesDAO().insert(selectedMovie);
        Toast.makeText(this,selectedMovie.getTitle() + " is added to favourites",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_share) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Share trailer");
            i.putExtra(Intent.EXTRA_TEXT, trailerLinks.get(0));
            startActivity(Intent.createChooser(i, "Choose"));
        }
        return super.onOptionsItemSelected(item);
    }
}