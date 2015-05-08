package com.hotb.pgmacdesign.materialdesignexamples.callbacks;

import com.hotb.pgmacdesign.materialdesignexamples.pojo.Movie;

import java.util.ArrayList;


/**
 * Created by Windows on 13-04-2015.
 */
public interface UpcomingMoviesLoadedListener {
    public void onUpcomingMoviesLoaded(ArrayList<Movie> listMovies);
}
