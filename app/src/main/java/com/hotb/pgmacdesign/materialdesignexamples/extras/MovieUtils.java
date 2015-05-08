package com.hotb.pgmacdesign.materialdesignexamples.extras;

import com.android.volley.RequestQueue;
import com.hotb.pgmacdesign.materialdesignexamples.database.DBMovies;
import com.hotb.pgmacdesign.materialdesignexamples.json.Endpoints;
import com.hotb.pgmacdesign.materialdesignexamples.json.Parser;
import com.hotb.pgmacdesign.materialdesignexamples.json.Requestor;
import com.hotb.pgmacdesign.materialdesignexamples.materialtest.MyApplication;
import com.hotb.pgmacdesign.materialdesignexamples.pojo.Movie;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Windows on 02-03-2015.
 */
public class MovieUtils {
    public static ArrayList<Movie> loadBoxOfficeMovies(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, Endpoints.getRequestUrlBoxOfficeMovies(30));
        ArrayList<Movie> listMovies = Parser.parseMoviesJSON(response);
        MyApplication.getWritableDatabase().insertMovies(DBMovies.BOX_OFFICE, listMovies, true);
        return listMovies;
    }

    public static ArrayList<Movie> loadUpcomingMovies(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, Endpoints.getRequestUrlUpcomingMovies(30));
        ArrayList<Movie> listMovies = Parser.parseMoviesJSON(response);
        MyApplication.getWritableDatabase().insertMovies(DBMovies.UPCOMING, listMovies, true);
        return listMovies;
    }
}
