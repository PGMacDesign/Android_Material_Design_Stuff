package com.hotb.pgmacdesign.materialdesignexamples.task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.hotb.pgmacdesign.materialdesignexamples.callbacks.UpcomingMoviesLoadedListener;
import com.hotb.pgmacdesign.materialdesignexamples.extras.MovieUtils;
import com.hotb.pgmacdesign.materialdesignexamples.network.VolleySingleton;
import com.hotb.pgmacdesign.materialdesignexamples.pojo.Movie;

import java.util.ArrayList;

/**
 * Created by Windows on 02-03-2015.
 */
public class TaskLoadUpcomingMovies extends AsyncTask<Void, Void, ArrayList<Movie>> {
    private UpcomingMoviesLoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;


    public TaskLoadUpcomingMovies(UpcomingMoviesLoadedListener myComponent) {

        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {

        ArrayList<Movie> listMovies = MovieUtils.loadUpcomingMovies(requestQueue);
        return listMovies;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> listMovies) {
        if (myComponent != null) {
            myComponent.onUpcomingMoviesLoaded(listMovies);
        }
    }
}
