package com.hotb.pgmacdesign.materialdesignexamples.callbacks;

import com.hotb.pgmacdesign.materialdesignexamples.pojo.Movie;

import java.util.ArrayList;


/**
 * Created by Windows on 02-03-2015.
 */
public interface BoxOfficeMoviesLoadedListener {
    public void onBoxOfficeMoviesLoaded(ArrayList<Movie> listMovies);
}
