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
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.hotb.pgmacdesign.materialdesignexamples.R;
import com.hotb.pgmacdesign.materialdesignexamples.adapters.AdapterBoxOffice;
import com.hotb.pgmacdesign.materialdesignexamples.misc.L;
import com.hotb.pgmacdesign.materialdesignexamples.misc.SortListener;
import com.hotb.pgmacdesign.materialdesignexamples.pojo.Movie;
import com.hotb.pgmacdesign.materialdesignexamples.pojo.MovieSorter;
import com.hotb.pgmacdesign.materialdesignexamples.volleystuff.VolleySingleton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

//No clue why Android studio won't let me import *. For whatever reason, it imports them individually automatically

//This is being used so I can call the static strings in the interface without referencing the class/package name

public class FragmentBoxOffice extends Fragment implements SortListener {

	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	private static final String STATE_MOVIES = "state";
	private String mParam1;
	private String mParam2;

	//Sorter
	MovieSorter movieSorter;

	//Holds movies
	ArrayList<Movie> mlistMovies = new ArrayList<>();


	//Adapter
	private AdapterBoxOffice adapterBoxOffice;



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

	/**
	 * To save the instance state, IE when the screen rotates
	 * @param outState
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList(STATE_MOVIES, mlistMovies);
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

		//Coming back from a rotation
		if(savedInstanceState != null){
			//Retrieve the data from the parcel
			mlistMovies = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
			//Set it to the adapter to update details
			adapterBoxOffice.setMovieList(mlistMovies);
		} else {
			//Set the data and send the JSON request (arguments = # of results
			//sendJSONRequest(30);
		}



		// Inflate the layout for this fragment
		return view;
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
