package com.hotb.pgmacdesign.materialdesignexamples.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hotb.pgmacdesign.materialdesignexamples.R;
import com.hotb.pgmacdesign.materialdesignexamples.volleystuff.VolleySingleton;

/**
 * Created by pmacdowell on 5/4/2015.
 */
public class MyFragment extends Fragment {

	private TextView textView;

	public static MyFragment getInstance(int position){
		MyFragment myFragment = new MyFragment();
		Bundle args = new Bundle();
		args.putInt("position", position);
		myFragment.setArguments(args);
		return myFragment;
	}

	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
		View layout = inflater.inflate(R.layout.fragment_my, container, false);

		textView = (TextView) layout.findViewById(R.id.position_in_fragment);

		Bundle bundle = getArguments();
		if(bundle != null){
			try {
				String str = "" + bundle.getInt("position");
				textView.setText("Postion is at: " + str);
			} catch (Exception e){
				e.printStackTrace();
				Log.d("Main Activity", "Line 139");
				textView.setText("STUFF!");
			}
		}

		createRequestQueue();

		return layout;
	}

	private void createRequestQueue() {

		RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();

		StringRequest request = new StringRequest(Request.Method.GET, "http://http.net/", new Response.Listener<String>(){
			public void onResponse(String response) {
				Log.d("Response: ", response);
			}
		}, new Response.ErrorListener(){
			public void onErrorResponse(VolleyError error) {
				Log.d("Error: ", error.getMessage());
			}
		});

		requestQueue.add(request);

	}
}