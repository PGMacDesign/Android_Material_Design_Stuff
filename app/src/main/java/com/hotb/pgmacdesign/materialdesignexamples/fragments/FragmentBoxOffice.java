package com.hotb.pgmacdesign.materialdesignexamples.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hotb.pgmacdesign.materialdesignexamples.R;
import com.hotb.pgmacdesign.materialdesignexamples.misc.L;
import com.hotb.pgmacdesign.materialdesignexamples.misc.UrlEndpoints;
import com.hotb.pgmacdesign.materialdesignexamples.pojo.Movie;
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
import static com.hotb.pgmacdesign.materialdesignexamples.misc.Keys.EndpointBoxOffice.KEY_RATINGS;
import static com.hotb.pgmacdesign.materialdesignexamples.misc.Keys.EndpointBoxOffice.KEY_RELEASE_DATES;
import static com.hotb.pgmacdesign.materialdesignexamples.misc.Keys.EndpointBoxOffice.KEY_SYNOPSIS;
import static com.hotb.pgmacdesign.materialdesignexamples.misc.Keys.EndpointBoxOffice.KEY_THEATER;
import static com.hotb.pgmacdesign.materialdesignexamples.misc.Keys.EndpointBoxOffice.KEY_THUMBNAIL;
import static com.hotb.pgmacdesign.materialdesignexamples.misc.Keys.EndpointBoxOffice.KEY_TITLE;

//This is being used so I can call the static strings in the interface without referencing the class/package name

public class FragmentBoxOffice extends Fragment {

	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	private String mParam1;
	private String mParam2;

	private VolleySingleton volleySingleton;
	private ImageLoader imageLoader;
	private RequestQueue requestQueue;

	//Holds the movies
	private ArrayList<Movie> listMovies = new ArrayList<>();

	//Date formatter / converter
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //NOTE! Small m is a minute, not a month

	//RecyclerView
	private RecyclerView listMovieHits;

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

		sendJSONRequest();
	}

	private void sendJSONRequest() {
		JsonObjectRequest request = new JsonObjectRequest(
				Request.Method.GET,
				getRequestUrl(10),
				(String) null, //TEMP FIX!
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						L.m(response.toString());
						parseJSONResponse(response);
					}
				},
				new Response.ErrorListener(){
					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});
		requestQueue.add(request);
	}

	/**
	 * Parse the JSON response
	 * @param response response received from the server in JSON format
	 */
	private void parseJSONResponse(JSONObject response) {
		if(response == null || response.length() == 0){
			return;
		}

		//Parse the response out
		try {
			StringBuilder data = new StringBuilder();

			//Put the response in to a JSON Array
			JSONArray arrayMovies = response.getJSONArray(KEY_MOVIES);

			//Loop through the array
			for(int i = 0; i < arrayMovies.length(); i++){
				//Get the object
				JSONObject currentMovie = arrayMovies.getJSONObject(i);

				//Returns a String, but we are forcing a Long instead
				long  id = currentMovie.getLong(KEY_ID);
				String title = currentMovie.getString(KEY_TITLE);

				//This grouping of code is to get a nested object (theater) under release_dates
				JSONObject objectReleaseDate = currentMovie.getJSONObject(KEY_RELEASE_DATES);
				String releaseDate=null;
				if(objectReleaseDate.has(KEY_THEATER)){
					releaseDate = objectReleaseDate.getString(KEY_THEATER);
				} else {
					releaseDate = "N/A";
				}

				//This grouping of code is to get nested objects under ratings
				JSONObject objectRatings = currentMovie.getJSONObject(KEY_RATINGS);
				int audience_score=-1;
				if(objectRatings.has(KEY_AUDIENCE_SCORE)){
					audience_score = objectRatings.getInt(KEY_AUDIENCE_SCORE);
				}

				//This grouping of code is to get a nested object (thumbnail) under posters
				JSONObject objectPosters = currentMovie.getJSONObject(KEY_RELEASE_DATES);
				String urlThumbnail=null;
				if(objectPosters.has(KEY_THUMBNAIL)){
					urlThumbnail = objectPosters.getString(KEY_THUMBNAIL);
				}

				String synopsis = currentMovie.getString(KEY_SYNOPSIS);

				//Set the movie object
				Movie movie = new Movie();
				movie.setId(id);
				movie.setTitle(title);
				Date dateObject = dateFormat.parse(releaseDate);
				movie.setReleaseDateTheater(dateObject);

				//Add it to the list
				listMovies.add(movie);

				//Append the data
				data.append("\n" + id+ ", " + title + ", " + releaseDate + ", " + audience_score + ", " + synopsis);

			}

			L.m(data.toString());
			L.m("\n GAP \n ");
			L.m(listMovies.toString());

		} catch (JSONException e){
			e.printStackTrace();
		} catch (ParseException e){ //Called if the Date parsing fails
			e.printStackTrace();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                            Bundle savedInstanceState) {
		//Create the view to reference
		View view = inflater.inflate(R.layout.fragment_fragment_box_office, container, false);
		//Initialize the recycler view
		listMovieHits = (RecyclerView) view.findViewById(R.id.listMovieHits);
		//Set the layout manager
		listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));

		// Inflate the layout for this fragment
		return view;
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
}
