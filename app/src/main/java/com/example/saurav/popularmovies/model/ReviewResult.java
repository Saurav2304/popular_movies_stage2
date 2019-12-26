package com.example.saurav.popularmovies.model;

/**
 * Created by Saurav on 7/17/2018.
 **/
public class ReviewResult {
    private String author;
    private String content;
    private String  id;
    private String url;

    public ReviewResult(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

}
