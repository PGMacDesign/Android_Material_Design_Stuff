package com.hotb.pgmacdesign.materialdesignexamples.services;

import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hotb.pgmacdesign.materialdesignexamples.misc.L;
import com.hotb.pgmacdesign.materialdesignexamples.misc.UrlEndpoints;
import com.hotb.pgmacdesign.materialdesignexamples.pojo.Movie;
import com.hotb.pgmacdesign.materialdesignexamples.volleystuff.MyApplication;
import com.hotb.pgmacdesign.materialdesignexamples.volleystuff.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

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

/**
 * This class utilizes the job scheduler library (in build.gradle for details) to schedule events.
 * Created by pmacdowell on 5/7/2015.
 */
public class MyService extends JobService {





	@Override
	public boolean onStartJob(JobParameters jobParameters) {
		L.t(this, "Start Service");
		L.m("Start Service");
		new MyTask(this).execute();
		return true; //Return true if being run on separate thread, false if on main thread. Using async below
	}

	@Override
	public boolean onStopJob(JobParameters jobParameters) {
		L.t(this, "Stop Service");
		L.m("Stop Service");
		return false;
	}

	//Make an async task to run in the background

	/**
	 * Params, progress, result
	 */
	private class MyTask extends AsyncTask<JobParameters, Void, JobParameters>{


		private VolleySingleton volleySingleton;
		private ImageLoader imageLoader;
		private RequestQueue requestQueue;

		//Date formatter / converter
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //NOTE! Small m is a minute, not a month

		MyService myService;

		//Constructor
		MyTask(MyService myService){
			this.myService = myService;

			//Initialize Variables
			volleySingleton = VolleySingleton.getsInstance();
			requestQueue = volleySingleton.getRequestQueue();
			imageLoader = volleySingleton.getImageLoader();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected JobParameters doInBackground(JobParameters... params) {
			return params[0];
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(JobParameters jobParameters) {
			super.onPostExecute(jobParameters);
			//Notify that asynctask has finished
			myService.jobFinished(jobParameters, false);
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
					//try { //Try and format the date. If it fails, at least it keeps going
						//dateObject = dateFormat.parse(releaseDate);
						//L.m("Format on date worked");
						//L.m(dateObject.toString());
						//NO CLUE WHY, BUT I CANNOT GET THE DATE FORMATTED CORRECTLY. PUTTING IT IN AS STRING FOR NOW. COME BACK TO FIX!
					//} catch (ParseException e){
						//e.printStackTrace();
					//}
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
		public String getRequestUrl(int limit){
			return UrlEndpoints.URL_BOX_OFFICE
					+ UrlEndpoints.URL_CHAR_QUESTION
					+ UrlEndpoints.URL_PARAM_API_KEY + MyApplication.API_KEY
					+ UrlEndpoints.URL_CHAR_AMPERSAND
					+ UrlEndpoints.URL_PARAM_LIMIT + limit;
		}

		/**
		 * Sends a JSON request and returns back all of the data from the website being pinged.
		 * @param results Int total number of results you want to receive.
		 */
		private void sendJSONRequest(int results) {
			JsonObjectRequest request = new JsonObjectRequest(
					Request.Method.GET,
					getRequestUrl(results), //Number of search results you want
					(String) null, //TEMP FIX!
					new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							//textVolleyError.setVisibility(View.GONE);
							//L.m(response.toString());
							//mlistMovies = parseJSONResponse(response);
							//adapterBoxOffice.setMovieList(mlistMovies);
						}
					},
					new Response.ErrorListener(){
						//When an error is received in the call. There are 6 sub-classes of these errors.
						@Override
						public void onErrorResponse(VolleyError error) {
							//handleVolleyError(error);
						}
					});
			requestQueue.add(request);
		}
	}
}
