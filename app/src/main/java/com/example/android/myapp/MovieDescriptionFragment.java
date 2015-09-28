package com.example.android.myapp;

/**
 * Created by qze713 on 9/27/15.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public  class MovieDescriptionFragment extends Fragment {

    String sMovieID;

    String[] movieTraillerID,movieTraillerLink,movieTraillerName,MovieTraillerSource,movieTraillerSize,movieTraillerType;
    MovieDescriptionAdapter movieDescriptionAdapter = new MovieDescriptionAdapter();
    RecyclerView MovieDescriptionRecyclerView;

    public MovieDescriptionFragment() {
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        MovieDescriptionRecyclerView = (RecyclerView) rootView.findViewById(R.id.Movie_Description_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this.getActivity());
        MovieDescriptionRecyclerView.setLayoutManager(llm);

        Intent intent = getActivity().getIntent();

        sMovieID = intent.getStringExtra(MovieImageFragment.iMovieId);
        String sMovieBackdrop = intent.getStringExtra(MovieImageFragment.iMovieBackDrop);
        String mMovieTitle = intent.getStringExtra(MovieImageFragment.iMovieTittle);
        String mReleaseYear =  intent.getStringExtra(MovieImageFragment.imovieReleaseDate);
        String mMovieRating =  intent.getStringExtra(MovieImageFragment.iMovieRating);
        String mMoviePosterUrl = intent.getStringExtra(MovieImageFragment.iMoviePoster);
        String mMovieOverview =  intent.getStringExtra(MovieImageFragment.iMovieOverview);
        String mOverviewTitle = "Overview";

        MovieDescriptionBackdrop movieDescriptionBackDrop;
        movieDescriptionBackDrop = new MovieDescriptionBackdrop(mMovieTitle,sMovieBackdrop);
        MovieDescriptionPoster movieDescriptionPoster;
        movieDescriptionPoster = new MovieDescriptionPoster(mReleaseYear,mMovieRating,mMoviePosterUrl);
        MovieDescriptionOverview movieDescriptionOverview;
        movieDescriptionOverview = new MovieDescriptionOverview(mOverviewTitle,mMovieOverview);






        movieDescriptionAdapter.setMovieDescriptionBackdrop(movieDescriptionBackDrop);
        movieDescriptionAdapter.setMovieDescriptionPoster(movieDescriptionPoster);
        movieDescriptionAdapter.setMovieDescriptionOverview(movieDescriptionOverview);


        MovieDescriptionRecyclerView.setAdapter(movieDescriptionAdapter);


        /*//String sMovieBackdrop = intent.getStringExtra(MovieImageFragment.iMovieBackDrop);
        String mMovieTitle = intent.getStringExtra(MovieImageFragment.iMovieTittle);
        TextView textView1 = (TextView) rootView.findViewById(R.id.TextView1);
        textView1.setText(mMovieTitle);*/

        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        updateMovieTraillers();

    }

    public void updateMovieTraillers(){

        FetchMovieTrailers movieTrailers = new FetchMovieTrailers();
        movieTrailers.execute();


    }

    public class FetchMovieTrailers extends AsyncTask<String,Void,Void> {

        private final String LOG_TAG = FetchMovieTrailers.class.getSimpleName();


        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String movieDescriotionTraillerJsonStr = null;
            //final String API_KEY = getString( R.string.api_key );

            String key = "f6c2f4efbf8b2c0923eb46b73e7d5120";

            //http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7

            try {

                final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/" + sMovieID + "/videos";
                final String MOVIE_ID = "id";
                final String API_KEY_PARM = "api_key";

                //URL url = new URL("http://api.themoviedb.org/3/movie/" + sMovieID + "/videos?api_key=f6c2f4efbf8b2c0923eb46b73e7d5120");



                //URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=f6c2f4efbf8b2c0923eb46b73e7d5120");

                //http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=f6c2f4efbf8b2c0923eb46b73e7d5120
                //api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=f6c2f4efbf8b2c0923eb46b73e7d5120
                //http://api.openweathermap.org/data/2.5/forecast/daily?sort_by=popularity.desc&api_key=f6c2f4efbf8b2c0923eb46b73e7d5120

                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY_PARM, key)
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieDescriotionTraillerJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Movie Data Jsosn string" + movieDescriotionTraillerJsonStr);

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

            try {
                 getMovieTrailersFromJson(movieDescriotionTraillerJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;


        }


        @Override
        protected void onPostExecute(Void aVoid) {

            for (int i = 0; i < movieTraillerID.length; i++) {

                MovieDescriptionTrailer movieDescriptionTrailer=new MovieDescriptionTrailer(movieTraillerID[i],movieTraillerLink[i],movieTraillerName[i],MovieTraillerSource[i],movieTraillerSize[i],movieTraillerType[i]);
                movieDescriptionAdapter.setmovieDescriptionTrailer(movieDescriptionTrailer);


            }
            MovieDescriptionRecyclerView.setAdapter(movieDescriptionAdapter);
            //super.onPostExecute(aVoid);
        }

        /*protected void onPostExecute() {


                for (int i = 0; i < movieTraillerID.length; i++) {

                    MovieDescriptionTrailer movieDescriptionTrailer=new MovieDescriptionTrailer(movieTraillerID[i],movieTraillerLink[i],movieTraillerName[i],MovieTraillerSource[i],movieTraillerSize[i],movieTraillerType[i]);
                    movieDescriptionAdapter.setmovieDescriptionTrailer(movieDescriptionTrailer);


                }
                MovieDescriptionRecyclerView.setAdapter(movieDescriptionAdapter);
                //mMovieAdapter.clear();




            //super.onPostExecute(strings);
        }*/

        private void getMovieTrailersFromJson(String movieDescriotionTraillerJsonStr )
                    throws JSONException{




            JSONObject movieJson = new JSONObject(movieDescriotionTraillerJsonStr);
            JSONArray movieTraillerArray = movieJson.getJSONArray("results");
            int temp = movieTraillerArray.length();
            ArrayList<MovieListArray> movieUrls = new ArrayList<>();

            movieTraillerID = new String[movieTraillerArray.length()];
            movieTraillerLink = new String[movieTraillerArray.length()];
            movieTraillerName = new String[movieTraillerArray.length()];
            MovieTraillerSource = new String[movieTraillerArray.length()];
            movieTraillerSize = new String[movieTraillerArray.length()];
            movieTraillerType = new String[movieTraillerArray.length()];

            for (int i = 0; i < movieTraillerArray.length(); i++) {

                JSONObject movieTrailler = movieTraillerArray.getJSONObject(i);

                movieTraillerID[i] = movieTrailler.getString("id");
                movieTraillerLink[i] = movieTrailler.getString("key");
                movieTraillerName[i] = movieTrailler.getString("name");
                MovieTraillerSource[i] = movieTrailler.getString("site");
                movieTraillerSize[i] = movieTrailler.getString("size");
                movieTraillerType[i] = movieTrailler.getString("type");





            }


        }





    }
}
