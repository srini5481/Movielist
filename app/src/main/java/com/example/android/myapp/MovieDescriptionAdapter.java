package com.example.android.myapp;

import android.content.Context;
import android.graphics.Movie;
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
 * Created by qze713 on 9/27/15.
 */
public class MovieDescriptionAdapter extends RecyclerView.Adapter<MovieDescriptionAdapter.MovieDescriptionHolder>{


    private List<Object> movieDescriptionList;
    //OnItemClickListener monItemClickListener;
    View v;



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public MovieDescriptionAdapter() {
        movieDescriptionList = new ArrayList<>();
    }


    @Override
    public MovieDescriptionHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        int listViewItemType = getItemViewType(viewType);
        MovieDescriptionHolder pvh = null;

        switch (viewType) {

            case 0:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.moviedescription_backdrop, parent, false);
                pvh = new Movie_Description_Backdrop(v);
                break;
            case 1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.moviedescription_poster, parent, false);
                pvh = new Movie_Description_Poster(v);
                break;
            case 2:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.moviedescription_overview, parent, false);
                pvh = new Movie_Description_Overview(v);
                break;
            case 3:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.moviedescription_trailler, parent, false);
                pvh = new Movie_Description_Trailler(v);
                break;




        }




        return pvh;


    }

    @Override
    public void onBindViewHolder(MovieDescriptionHolder holder, int position) {

        holder.displayRow(position);

    }



    @Override
    public int getItemCount() {


        return movieDescriptionList.size();
    }

    public void setMovieDescriptionBackdrop(MovieDescriptionBackdrop mMovieAdapter) {

        movieDescriptionList.add(mMovieAdapter);
    }

    public void setMovieDescriptionPoster(MovieDescriptionPoster mMovieAdapter) {
        movieDescriptionList.add(mMovieAdapter);
    }

    public void setMovieDescriptionOverview(MovieDescriptionOverview mMovieAdapter) {
        movieDescriptionList.add(mMovieAdapter);
    }

    public void setmovieDescriptionTrailer(MovieDescriptionTrailer mMovieAdapter){
        movieDescriptionList.add(mMovieAdapter);
    }


    @Override
    public int getItemViewType(int position) {
        Object object = movieDescriptionList.get(position);
        if (object instanceof MovieDescriptionBackdrop) {
            return 0;
        } else if (object instanceof MovieDescriptionPoster) {
            return 1;
        } else if (object instanceof MovieDescriptionOverview) {
            return 2;
        } else if (object instanceof MovieDescriptionTrailer) {
            return 3;
        } else{
            return 4;
        }



    }


    public  class MovieDescriptionHolder extends RecyclerView.ViewHolder  {

        CardView cardView;


        public MovieDescriptionHolder(View itemView) {
            super(itemView);



        }

        protected void displayRow(int position) {

        }


    }



    public class Movie_Description_Backdrop extends MovieDescriptionHolder{


        TextView movieTitle;
        ImageView movieBackdrop;


        public Movie_Description_Backdrop(View itemView) {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.Movie_Description_Backdrop_CardView);

            movieTitle = (TextView) itemView.findViewById(R.id.movie_Description_title);
            movieBackdrop = (ImageView) itemView.findViewById(R.id.movie_backdrop);

        }

        @Override
        protected void displayRow(int position) {

            MovieDescriptionBackdrop poster = (MovieDescriptionBackdrop) movieDescriptionList.get(position);

            Context context = cardView.getContext();

            //holder.moviePoster.getContext();

            Picasso.with(context)
                    .load(poster.movieBackdrop)
                    .into(movieBackdrop);

            movieTitle.setText(poster.movieTitle);


        }


    }


    public class Movie_Description_Poster extends MovieDescriptionHolder {





        TextView movieYear;
        TextView movieRating;
        ImageView moviePoster;

        public Movie_Description_Poster(View itemView) {
            super(itemView);


            cardView = (CardView)itemView.findViewById(R.id.Movie_Description_Poster_CardView);


            movieYear=(TextView)itemView.findViewById(R.id.movie_year);
            movieRating=(TextView)itemView.findViewById(R.id.movie_rating);
            moviePoster=(ImageView)itemView.findViewById(R.id.movie_poster);




        }

        @Override
        protected void displayRow(int position) {

            MovieDescriptionPoster poster = (MovieDescriptionPoster) movieDescriptionList.get(position);

            Context context = cardView.getContext();

            //holder.moviePoster.getContext();

            Picasso.with(context)
                    .load(poster.moviePoster)
                    .into(moviePoster);


            movieRating.setText((poster.Rating));
            movieYear.setText((poster.movieYear));

        }


    }


    public class Movie_Description_Overview extends MovieDescriptionHolder {





        TextView overViewTitle;
        TextView OverViewDes;


        public Movie_Description_Overview(View itemView) {
            super(itemView);


            cardView = (CardView)itemView.findViewById(R.id.Movie_Description_Overview_CardView);

            overViewTitle=(TextView)itemView.findViewById(R.id.movie_OverView_Title);
            OverViewDes=(TextView)itemView.findViewById(R.id.movie_Overview);

        }

        @Override
        protected void displayRow(int position) {

            MovieDescriptionOverview poster = (MovieDescriptionOverview) movieDescriptionList.get(position);
            overViewTitle.setText((poster.overviewTitle));
            OverViewDes.setText((poster.overViewDiscription));

        }

    }


    public class Movie_Description_Trailler extends MovieDescriptionHolder {





        TextView movieTraillerName;



        public Movie_Description_Trailler(View itemView) {
            super(itemView);


            cardView = (CardView)itemView.findViewById(R.id.Movie_Description_Trailler_CardView);

            movieTraillerName=(TextView)itemView.findViewById(R.id.Movie_Trailler_Name);


        }

        @Override
        protected void displayRow(int position) {

            MovieDescriptionTrailer poster = (MovieDescriptionTrailer) movieDescriptionList.get(position);
            movieTraillerName.setText((poster.movieTraillerName));

        }

    }





}
