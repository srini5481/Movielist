package com.example.android.myapp;

import android.media.Rating;

/**
 * Created by qze713 on 9/27/15.
 */
public class MovieDescriptionTrailer {

    String movieTraillerId;
    String movieTraillerLink;
    String movieTraillerName;
    String MovieTraillerSource;
    String movieTraillerSize;
    String movieTraillerType;

    MovieDescriptionTrailer(String movieTraillerId,String movieTraillerLink, String movieTraillerName,String MovieTraillerSource, String movieTraillerSize,String movieTraillerType) {

        this.movieTraillerId = movieTraillerId;
        this.movieTraillerLink = movieTraillerLink;
        this.movieTraillerName = movieTraillerName;
        this.MovieTraillerSource = MovieTraillerSource;
        this.movieTraillerSize = movieTraillerSize;
        this.movieTraillerType = movieTraillerType;
    }

}
