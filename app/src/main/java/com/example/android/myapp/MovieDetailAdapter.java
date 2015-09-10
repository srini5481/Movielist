package com.example.android.myapp;

import android.content.Context;
import android.media.Rating;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qze713 on 9/9/15.
 */
public class MovieDetailAdapter extends RecyclerView.Adapter<MovieDetailAdapter.MovieDetailHolder> {



    private List<Object> movieDetailList;


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public MovieDetailAdapter() {
        movieDetailList = new ArrayList<>();
    }


    @Override
    public MovieDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        int listViewItemType = getItemViewType(viewType);
        MovieDetailHolder pvh = null;

        switch (viewType) {

            case 0:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movielistlayout, parent, false);
                pvh = new CardLayout_one(v);
                break;




        }




        return pvh;


    }

    @Override
    public void onBindViewHolder(MovieDetailHolder holder, int position) {

        holder.displayRow(position);

    }

    @Override
    public int getItemCount() {


        return movieDetailList.size();
    }

    public void setMovieDetail(MovieListArray mMovieAdapter) {

        movieDetailList.add(mMovieAdapter);
    }


    @Override
    public int getItemViewType(int position) {
        Object object = movieDetailList.get(position);
        if (object instanceof MovieListArray) {
            return 0;
        } else{
            return 1;
        }



    }


    public  class MovieDetailHolder extends RecyclerView.ViewHolder {

        CardView cv;


        public MovieDetailHolder(View itemView) {
            super(itemView);



        }

        protected void displayRow(int position) {

        }

    }



    public class CardLayout_one extends MovieDetailHolder{




        TextView movieTitle;
        TextView movieYear;
        TextView movieRating;
        ImageView moviePoster;

        public CardLayout_one(View itemView) {
            super(itemView);


            cv = (CardView)itemView.findViewById(R.id.cv2);

            movieTitle=(TextView)itemView.findViewById(R.id.movie_title);
            movieYear=(TextView)itemView.findViewById(R.id.movie_year);
            movieRating=(TextView)itemView.findViewById(R.id.movie_rating);
            moviePoster=(ImageView)itemView.findViewById(R.id.movie_poster);




        }

        @Override
        protected void displayRow(int position) {

            MovieListArray poster = (MovieListArray) movieDetailList.get(position);


            Context context = cv.getContext(); //holder.moviePoster.getContext();

            Picasso.with(context)
                    .load(poster.moviePoster)
                    .into(moviePoster);

            movieTitle.setText(poster.movieTitle);
            movieRating.setText((poster.Rating));
            movieYear.setText((poster.movieYear));

        }
    }



}

