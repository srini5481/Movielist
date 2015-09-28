package com.example.android.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

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
import java.util.Arrays;

/**
 * Created by qze713 on 9/9/15.
 */
public class MovieImageFragment extends Fragment implements MovieDetailAdapter.ClickListener{


    //private ArrayList<MovieListArray> mMovieAdapter;

    String[] movieId,movieTitle,movieOverview,movieReleaseDate,moviePosterPath,movieVoteAverage,moviebackdrop;
    public final static String iMovieId = "tempId";
    public final static String iMovieTittle = "temp_movie";
    public final static String iMoviePoster = "temp_movie_Poster";
    public final static String imovieReleaseDate = "temp_imovieRelease date";
    public final static String iMovieRating ="temp_rating";
    public final static String iMovieOverview ="temp_overview";
    public final static String iMovieBackDrop = "temp_backdrop";


    RecyclerView rv;

    MovieDetailAdapter adapter1 = new MovieDetailAdapter();




    /**
     * A placeholder fragment containing a simple view.
     */


    //private MovieListArray movieList;

    //MovieListArray[] movieList;

   MovieListArray[] movieList = {
            new MovieListArray ("Movie Title1:","Release Date : 2015-07-17","Rating : 7.0","http://image.tmdb.org/t/p/w185/7SGGUiTE6oc2fh9MjIk5M00dsQd.jpg"),
            new MovieListArray ("Movie Title2:","Release Date : 2015-06-25","Rating : 6.8","http://image.tmdb.org/t/p/w185/s5uMY8ooGRZOL0oe4sIvnlTsYQO.jpg"),
            new MovieListArray ("Movie Title3:","Release Date : 2015-07-01","Rating : 6.3","http://image.tmdb.org/t/p/w185/5JU9ytZJyR3zmClGmVm9q4Geqbd.jpg")
    };



    public MovieImageFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // inflater.inflate(R.menu.moviefragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        /*if (id == R.id.action_refresh) {

            //FetchMovieData movieData = new FetchMovieData();
            //movieData.execute();

            updateMovieData();



            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }


    private void updateMovieData(){



        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //String location = prefs.getString(getString(R.string.pref_location_key),
        //      getString(R.string.pref_location_default));

        //String sortOrder = prefs.getString(getString(R.string.SortType),
         //       getString(R.string.sort_type_Value_Most_Popular));

        FetchMovieData movieData = new FetchMovieData();
        movieData.execute();


    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovieData();
    }




    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_main,container,false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this.getActivity());

        rv.setLayoutManager(llm);
        Intent intent = getActivity().getIntent();

        adapter1.setClickListener(this);

        //rv.setOnClickListener(m)




        //MovieListArray movieList1;


        /*for (MovieListArray movieList1 : movieList)
            adapter1.setMovieDetail(movieList1);


        //movieList[i]=new MovieListArray(movieReleaseDate[i],movieVoteAverage[i],moviePosterPath[i]);

        rv.setAdapter(adapter1);
*/

        return rootView;
    }

    @Override
    public void itemClicked(View v, int position) {

        //startActivity(new Intent(getActivity(),DetailActivity.class));

        String smovieId = movieId[position];
        String smovieTitle = movieTitle[position];
        String sMovieBackdrop = moviebackdrop[position];
        String sMovieUrl = moviePosterPath[position];
        String sreleaseYear = movieReleaseDate[position];
        String sMovieRating = movieVoteAverage[position];
        String sMovieOverview = movieOverview[position];


        Intent intent = new Intent(getActivity(), DetailActivity.class);
        //.putExtra(Intent.EXTRA_TEXT, smovieTitle);
        intent.putExtra(iMovieId,smovieId);
        intent.putExtra(iMovieTittle,smovieTitle);
        intent.putExtra(iMovieBackDrop,sMovieBackdrop);
        intent.putExtra(iMoviePoster,sMovieUrl);
        intent.putExtra(imovieReleaseDate,sreleaseYear);
        intent.putExtra(iMovieRating,sMovieRating);
        intent.putExtra(iMovieOverview,sMovieOverview);
        ;
        startActivity(intent);

    }

    public class FetchMovieData extends AsyncTask<Void,Void,ArrayList<MovieListArray>> {

        private final String LOG_TAG = FetchMovieData.class.getSimpleName();


        @Override
        protected ArrayList<MovieListArray> doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String movieDetailJsonStr = null;

            String key = "f6c2f4efbf8b2c0923eb46b73e7d5120";

            http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7

            try {

                final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
                final String SORT_TYPE = "sort_by";
                final String API_KEY= "api_key";

                //URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=f6c2f4efbf8b2c0923eb46b73e7d5120");

                //http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=f6c2f4efbf8b2c0923eb46b73e7d5120
                //api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=f6c2f4efbf8b2c0923eb46b73e7d5120
                //http://api.openweathermap.org/data/2.5/forecast/daily?sort_by=popularity.desc&api_key=f6c2f4efbf8b2c0923eb46b73e7d5120

                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_TYPE, "popularity.desc")
                        .appendQueryParameter(API_KEY, key)
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
                movieDetailJsonStr = buffer.toString();

                Log.v(LOG_TAG,"Movie Data Jsosn string" + movieDetailJsonStr);

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally{
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
                return getMovieDataFromJson(movieDetailJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;



        }


        @Override
        protected void onPostExecute(ArrayList<MovieListArray> strings) {

            if (strings != null) {

       for (int i = 0; i < movieId.length; i++) {

            MovieListArray movieList1=new MovieListArray(movieTitle[i],movieReleaseDate[i],movieVoteAverage[i],moviePosterPath[i]);
            adapter1.setMovieDetail(movieList1);


        }
                rv.setAdapter(adapter1);
                //mMovieAdapter.clear();


            }


            //super.onPostExecute(strings);
        }



        private ArrayList<MovieListArray> getMovieDataFromJson(String movieDetailJsonStr)
                throws JSONException {


            JSONObject movieJson = new JSONObject(movieDetailJsonStr);
            JSONArray movieArray = movieJson.getJSONArray("results");
            int temp = movieArray.length();
            ArrayList<MovieListArray> movieUrls = new ArrayList<>();


            movieId = new String[movieArray.length()];
            moviebackdrop= new String[movieArray.length()];
            movieTitle = new String[movieArray.length()];
            movieOverview = new String[movieArray.length()];
            movieReleaseDate = new String[movieArray.length()];
            moviePosterPath = new String[movieArray.length()];
            movieVoteAverage = new String[movieArray.length()];
            for (int i = 0; i < movieArray.length(); i++) {

                JSONObject movie = movieArray.getJSONObject(i);

                movieId[i] = movie.getString("id");
                moviebackdrop[i]="http://image.tmdb.org/t/p/w185" + movie.getString("backdrop_path");
                movieTitle[i] = "Movie Title : " + movie.getString("original_title");
                movieOverview[i] = movie.getString("overview");
                movieReleaseDate[i] = "ReleaseDate : "  + movie.getString("release_date");
                moviePosterPath[i] = "http://image.tmdb.org/t/p/w185" + movie.getString("poster_path");
                //movieUrls.add(i,new MovieListArray ("http://image.tmdb.org/t/p/w185" + movie.getString("poster_path")));
                //movieList1.
                movieVoteAverage[i] = "Rating : " + movie.getString("vote_average");
                movieUrls.add(i,new MovieListArray (movieTitle[i],movieReleaseDate[i],movieVoteAverage[i],moviePosterPath[i]));
                //movieList[i]=new MovieListArray(movieReleaseDate[i],movieVoteAverage[i],moviePosterPath[i]);



            }


            for (String s : moviePosterPath) {
                Log.v(LOG_TAG, "Poster Path entry: " + s);
            }


            for (String s : moviebackdrop) {
                Log.v(LOG_TAG, "Movie Back drop entry: " + s);
            }
            //Log.d(LOG_TAG, moviePosterPath[i]);
            //return moviePosterPath;
            return movieUrls;


        }
    }
}
