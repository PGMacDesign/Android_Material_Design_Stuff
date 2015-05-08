package com.hotb.pgmacdesign.materialdesignexamples.task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.hotb.pgmacdesign.materialdesignexamples.callbacks.BoxOfficeMoviesLoadedListener;
import com.hotb.pgmacdesign.materialdesignexamples.extras.MovieUtils;
import com.hotb.pgmacdesign.materialdesignexamples.network.VolleySingleton;
import com.hotb.pgmacdesign.materialdesignexamples.pojo.Movie;

import java.util.ArrayList;


/**
 * Created by Windows on 02-03-2015.
 */
public class TaskLoadMoviesBoxOffice extends AsyncTask<Void, Void, ArrayList<Movie>> {
    private BoxOfficeMoviesLoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;


    public TaskLoadMoviesBoxOffice(BoxOfficeMoviesLoadedListener myComponent) {

        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {

        ArrayList<Movie> listMovies = MovieUtils.loadBoxOfficeMovies(requestQueue);
        return listMovies;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> listMovies) {
        if (myComponent != null) {
            myComponent.onBoxOfficeMoviesLoaded(listMovies);
        }
    }


}
