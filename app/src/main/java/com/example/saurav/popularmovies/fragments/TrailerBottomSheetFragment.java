package com.example.saurav.popularmovies.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.saurav.popularmovies.R;
import com.example.saurav.popularmovies.adapter.TrailerAdapter;

import java.util.List;

/**
 * Created by $USER_NAME on 7/17/2018.
 **/
public class TrailerBottomSheetFragment extends BottomSheetDialogFragment implements TrailerAdapter.TrailerItemClickListener{

    private List<String> trailerLinks;

    public TrailerBottomSheetFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_trailer, container, false);
        RecyclerView trailerRecyclerView = rootView.findViewById(R.id.trailer_rcyclr_view);
        ImageView emptyResultsIv = rootView.findViewById(R.id.trailer_empty_state);
        if (getArguments() != null) {
            trailerLinks = getArguments().getStringArrayList("TRAILER_LINKS");
            if (trailerLinks.size() == 0) {
                emptyResultsIv.setVisibility(View.VISIBLE);
            } else {
                emptyResultsIv.setVisibility(View.GONE);
            }
        }
        trailerRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        TrailerAdapter trailerAdapter = new TrailerAdapter(getContext(), trailerLinks, this);
        trailerRecyclerView.setAdapter(trailerAdapter);
        return rootView;
    }
    private void playTrailer(Uri parseTrailer) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, parseTrailer);
        startActivity(browserIntent);
    }

    @Override
    public void onTrailerClick(int clickedItemIndex, List<String> trailerList) {
        playTrailer(Uri.parse(trailerLinks.get(clickedItemIndex)));
    }
}

