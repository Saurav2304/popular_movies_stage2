package com.example.saurav.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saurav.popularmovies.R;
import com.example.saurav.popularmovies.model.ReviewResult;

import java.util.List;

/**
 * Created by $USER_NAME on 7/16/2018.
 **/
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {

    private Context context;
    private List<ReviewResult> reviewResultList;
    public ReviewsAdapter(List<ReviewResult> reviewResultList, Context context) {
        this.reviewResultList = reviewResultList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.review_list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ReviewResult reviewResult = reviewResultList.get(position);
        holder.reviewAuthorTv.setText(reviewResult.getAuthor());
        holder.reviewContent.setText(reviewResult.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewResultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView reviewAuthorTv;
        public TextView reviewContent;
        public MyViewHolder(View itemView) {
            super(itemView);
            reviewAuthorTv = itemView.findViewById(R.id.review_author_text);
            reviewContent = itemView.findViewById(R.id.review_content_text);
        }
    }
}
