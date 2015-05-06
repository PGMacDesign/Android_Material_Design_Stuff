package com.hotb.pgmacdesign.materialdesignexamples.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hotb.pgmacdesign.materialdesignexamples.R;
import com.hotb.pgmacdesign.materialdesignexamples.adapters.AdapterBoxOffice;
import com.hotb.pgmacdesign.materialdesignexamples.misc.L;
import com.hotb.pgmacdesign.materialdesignexamples.misc.SortListener;
import com.hotb.pgmacdesign.materialdesignexamples.misc.UrlEndpoints;
import com.hotb.pgmacdesign.materialdesignexamples.pojo.Movie;
import com.hotb.pgmacdesign.materialdesignexamples.pojo.MovieSorter;
import com.hotb.pgmacdesign.materialdesignexamples.volleystuff.MyApplication;
import com.hotb.pgmacdesign.materialdesignexamples.volleystuff.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.hotb.pgmacdesign.materialdesignexamples.misc.Keys.EndpointBoxOffice.KEY_AUDIENCE_SCORE;
import static com.hotb.pgmacdesign.materialdesignexamples.misc.Keys.EndpointBoxOffice.KEY_ID;
import static com.hotb.pgmacdesign.materialdesignexamples.misc.Keys.EndpointBoxOffice.KEY_MOVIES;
import static com.hotb.pgmacdesign.materialdesignexamples.misc.Keys.EndpointBoxOffice.KEY_POSTERS;
import static com.hotb.pgmacdesign.materialdesignexamples.misc.Keys.EndpointBoxOffice.KEY_RATINGS;
import static com.hotb.pgmacdesign.materialdesignexamples.misc.Keys.EndpointBoxOffice.KEY_RELEASE_DATES;
import static com.hotb.pgmacdesign.materialdesignexamples.misc.Keys.EndpointBoxOffice.KEY_SYNOPSIS;
import static com.hotb.pgmacdesign.materialdesignexamples.misc.Keys.EndpointBoxOffice.KEY_THEATER;
import static com.hotb.pgmacdesign.materialdesignexamples.misc.Keys.EndpointBoxOffice.KEY_THUMBNAIL;
import static com.hotb.pgmacdesign.materialdesignexamples.misc.Keys.EndpointBoxOffice.KEY_TITLE;

//No clue why Android studio won't let me import *. For whatever reason, it imports them individually automatically

//This is being used so I can call the static strings in the interface without referencing the class/package name

public class FragmentBoxOffice extends Fragment implements SortListener {

	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	private String mParam1;
	private String mParam2;

	//Sorter
	MovieSorter movieSorter;

	//Holds movies
	ArrayList<Movie> mlistMovies = new ArrayList<>();

	private VolleySingleton volleySingleton;
	private ImageLoader imageLoader;
	private RequestQueue requestQueue;

	//Adapter
	private AdapterBoxOffice adapterBoxOffice;

	//Date formatter / converter
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //NOTE! Small m is a minute, not a month

	//RecyclerView
	private RecyclerView listMovieHits;

	//Error textView
	private TextView textVolleyError;

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 */
	public static FragmentBoxOffice newInstance(String param1, String param2) {
		FragmentBoxOffice fragment = new FragmentBoxOffice();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public FragmentBoxOffice() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}

		//Initialize Variables
		volleySingleton = VolleySingleton.getsInstance();
		requestQueue = volleySingleton.getRequestQueue();
		imageLoader = volleySingleton.getImageLoader();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		//Create the view to reference
		View view = inflater.inflate(R.layout.fragment_fragment_box_office, container, false);
		//Textview for error handling
		textVolleyError = (TextView) view.findViewById(R.id.textVolleyError);
		//Initialize the recycler view
		listMovieHits = (RecyclerView) view.findViewById(R.id.listMovieHits);
		//Set the layout manager
		listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));

		adapterBoxOffice = new AdapterBoxOffice(getActivity());
		listMovieHits.setAdapter(adapterBoxOffice);

		//Set the data and send the JSON request
		sendJSONRequest(30);

		// Inflate the layout for this fragment
		return view;
	}

	private void sendJSONRequest(int results) {
		JsonObjectRequest request = new JsonObjectRequest(
				Request.Method.GET,
				getRequestUrl(results), //Number of search results you want
				(String) null, //TEMP FIX!
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						textVolleyError.setVisibility(View.GONE);
						L.m(response.toString());
						mlistMovies = parseJSONResponse(response);
						adapterBoxOffice.setMovieList(mlistMovies);
					}
				},
				new Response.ErrorListener(){
					//When an error is received in the call. There are 6 sub-classes of these errors.
					@Override
					public void onErrorResponse(VolleyError error) {
						handleVolleyError(error);
					}
				});
		requestQueue.add(request);
	}

	//Handles all volley errors
	private void handleVolleyError(VolleyError error){
		L.m("Error in response");
		//Error has occurred, make the textView visible
		textVolleyError.setVisibility(View.VISIBLE);

		//Break error down and check which one it is
		if(error instanceof TimeoutError || error instanceof NoConnectionError){
			textVolleyError.setText(R.string.error_timeout);
		} else if (error instanceof AuthFailureError){
			textVolleyError.setText(R.string.authentication_error);
		} else if (error instanceof ServerError){
			textVolleyError.setText(R.string.server_error);
		} else if (error instanceof NetworkError){
			textVolleyError.setText(R.string.network_error);
		} else if (error instanceof ParseError){
			textVolleyError.setText(R.string.parse_error);
		}
	}

	/**
	 * Parse the JSON response
	 * @param response response received from the server in JSON format
	 */
	private ArrayList<Movie> parseJSONResponse(JSONObject response) {
		//Holds the movies
		ArrayList<Movie> listMovies = new ArrayList<>();

		if(response == null || response.length() == 0){
			return listMovies;
		}




		//Parse the response out
		try {

			//Put the response in to a JSON Array
			JSONArray arrayMovies = response.getJSONArray(KEY_MOVIES);

			//Loop through the array
			for(int i = 0; i < arrayMovies.length(); i++){

				/*
				These need to go in the for loop because if they were out of the loop, every time
				it iterates it will leave the last value in the variable so it will not be re-
				initialized with a new value. This could lead to inconsistencies in the data where
				some data that SHOULD return a null/ NA will instead return the value of the last
				movie that was brought in.
				 */
				long id = -1;
				String title = "NA";
				String releaseDate = "NA";
				int audience_score = -1;
				String synopsis = "NA";
				String urlThumbnail = "NA";

				//Get the object
				JSONObject currentMovie = arrayMovies.getJSONObject(i);

				//Returns a String, but we are forcing a Long instead
				if(currentMovie.has(KEY_ID) && !currentMovie.isNull(KEY_ID)){
					id = currentMovie.getLong(KEY_ID);
				}

				if(currentMovie.has(KEY_TITLE) && !currentMovie.isNull(KEY_TITLE)){
					title = currentMovie.getString(KEY_TITLE);
				}


				//This grouping of code is to get a nested object (theater) under release_dates
				JSONObject objectReleaseDate = currentMovie.getJSONObject(KEY_RELEASE_DATES);
				if(objectReleaseDate.has(KEY_THEATER) && !objectReleaseDate.isNull(KEY_THEATER)){
					releaseDate = objectReleaseDate.getString(KEY_THEATER);
				}

				//This grouping of code is to get nested objects under ratings
				JSONObject objectRatings = currentMovie.getJSONObject(KEY_RATINGS);
				if(objectRatings.has(KEY_AUDIENCE_SCORE) && !objectRatings.isNull(KEY_AUDIENCE_SCORE)){
					audience_score = objectRatings.getInt(KEY_AUDIENCE_SCORE);
				}

				//This grouping of code is to get a nested object (thumbnail) under posters
				JSONObject objectPosters = currentMovie.getJSONObject(KEY_POSTERS);
				if(objectPosters.has(KEY_THUMBNAIL) && !objectPosters.isNull(KEY_THUMBNAIL)){
					urlThumbnail = objectPosters.getString(KEY_THUMBNAIL);
				}

				if(currentMovie.has(KEY_SYNOPSIS) && !currentMovie.isNull(KEY_SYNOPSIS)){
					synopsis = currentMovie.getString(KEY_SYNOPSIS);
				}



				//Set the movie object
				Movie movie = new Movie();
				movie.setId(id);
				movie.setTitle(title);
				movie.setUrlThumbnail(urlThumbnail);
				movie.setAudienceScore(audience_score);
				movie.setSynopsis(synopsis);
				Date dateObject = null;
				try { //Try and format the date. If it fails, at least it keeps going
					dateObject = dateFormat.parse(releaseDate);
					//L.m("Format on date worked");
					//L.m(dateObject.toString());
					//NO CLUE WHY, BUT I CANNOT GET THE DATE FORMATTED CORRECTLY. PUTTING IT IN AS STRING FOR NOW. COME BACK TO FIX!
				} catch (ParseException e){
					e.printStackTrace();
				}
				movie.setReleaseDateTheater(releaseDate);

				//Add it to the list if the values are not missing / null
				if(id != -1 && !title.equalsIgnoreCase("NA")){
					listMovies.add(movie);
				}


			}

			L.m("\n GAP \n ");
			L.m(listMovies.toString());

		} catch (JSONException e){
			e.printStackTrace();
		}

		return listMovies;
	}


	/**
	 * Appends the info to complete the URL String.
	 * @param limit limit number of results
	 * @return Returns the final URL to send in String format
	 */
	public static String getRequestUrl(int limit){
		return UrlEndpoints.URL_BOX_OFFICE
				+ UrlEndpoints.URL_CHAR_QUESTION
				+ UrlEndpoints.URL_PARAM_API_KEY + MyApplication.API_KEY
				+ UrlEndpoints.URL_CHAR_AMPERSAND
				+ UrlEndpoints.URL_PARAM_LIMIT + limit;
	}

	@Override
	public void onSortByName() {
		L.m("Sort " + " Name " + " Search " + "Box office");

		movieSorter.sortMoviesByName(mlistMovies);
		adapterBoxOffice.notifyDataSetChanged();
	}

	@Override
	public void onSortByDate() {
		L.m("Sort " + " date " + " Search " + "Box office");

		movieSorter.sortMoviesByDate(mlistMovies);
		adapterBoxOffice.notifyDataSetChanged();
	}

	@Override
	public void onSortByRating() {
		L.m("Sort " + " rating " + " Search " + "Box office");

		movieSorter.sortMoviesByRating(mlistMovies);
		adapterBoxOffice.notifyDataSetChanged();
	}
}
