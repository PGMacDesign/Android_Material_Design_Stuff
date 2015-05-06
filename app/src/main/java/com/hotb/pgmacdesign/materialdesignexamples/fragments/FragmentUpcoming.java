package com.hotb.pgmacdesign.materialdesignexamples.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hotb.pgmacdesign.materialdesignexamples.R;
import com.hotb.pgmacdesign.materialdesignexamples.misc.L;
import com.hotb.pgmacdesign.materialdesignexamples.misc.SortListener;

/**

 */
public class FragmentUpcoming extends Fragment implements SortListener {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;


	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment FragmentUpcoming.
	 */
	// TODO: Rename and change types and number of parameters
	public static FragmentUpcoming newInstance(String param1, String param2) {
		FragmentUpcoming fragment = new FragmentUpcoming();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public FragmentUpcoming() {
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
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_fragment_upcoming, container, false);
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}

	@Override
	public void onDetach() {
		super.onDetach();

	}


	@Override
	public void onSortByName() {
		L.m("Sort " + " Name " + " Search " + "Upcoming");
	}

	@Override
	public void onSortByDate() {
		L.m("Sort " + " date " + " Search " + "Upcoming");
	}

	@Override
	public void onSortByRating() {
		L.m("Sort " + " rating " + " Search " + "Upcoming");
	}
}
