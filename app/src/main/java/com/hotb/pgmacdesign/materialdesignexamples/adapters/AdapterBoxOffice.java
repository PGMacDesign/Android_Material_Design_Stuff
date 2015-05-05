package com.hotb.pgmacdesign.materialdesignexamples.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.hotb.pgmacdesign.materialdesignexamples.R;
import com.hotb.pgmacdesign.materialdesignexamples.misc.L;
import com.hotb.pgmacdesign.materialdesignexamples.pojo.Movie;
import com.hotb.pgmacdesign.materialdesignexamples.volleystuff.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by pmacdowell on 5/5/2015.
 */
public class AdapterBoxOffice extends RecyclerView.Adapter<AdapterBoxOffice.ViewHolderBoxOffice> {

	//Movie list to hold data
	private ArrayList<Movie> listMovies = new ArrayList<>();

	private LayoutInflater layoutInflater;

	private VolleySingleton volleySingleton;
	private ImageLoader imageLoader;
	private RequestQueue requestQueue;

	//Constructor
	public AdapterBoxOffice(Context context){
		layoutInflater = LayoutInflater.from(context);
		volleySingleton = VolleySingleton.getsInstance();
		imageLoader = VolleySingleton.getImageLoader();
	}

	//Setter method
	public void setMovieList(ArrayList<Movie> listMovies){
		this.listMovies = listMovies;
		notifyItemRangeChanged(0, listMovies.size());
	}

	@Override
	public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = layoutInflater.inflate(R.layout.custom_movie_box_office, parent, false);
		ViewHolderBoxOffice viewHolder = new ViewHolderBoxOffice(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(final ViewHolderBoxOffice holder, int position) {
		//For the given position, put the data stored in the array into the view
		Movie currentMovie = listMovies.get(position);

		holder.movieTitle.setText(currentMovie.getTitle());

		String tempDate = currentMovie.getReleaseDateTheater().toString();
		if(tempDate != null){
			holder.movieReleaseDate.setText(tempDate);
		} else {
			holder.movieReleaseDate.setText("NA");
		}

		holder.movieAudienceScore.setRating(currentMovie.getAudienceScore() / 20.0f); // Div by 20 b/c score is out of 5 stars

		//Use volleysingleton image loader here for the thumbnail
		String urlThumbnail = currentMovie.getUrlThumbnail();
		//If the thumbnail is not null, load the image
		if(urlThumbnail != null){
			imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
				@Override
				public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
					holder.movieThumbnail.setImageBitmap(response.getBitmap());
				}

				@Override
				public void onErrorResponse(VolleyError error) {
					//Do something
					L.m("VOLLEY ERROR");
				}
			});
		} else {
			//Do something
			L.m("urlThumbnail is null");
		}
		//holder.movieThumbnail

	}

	/**
	 * Loads the images into the viewHolder object
	 * @param urlThumbnail
	 * @param holder
	 */
	private void loadImages(String urlThumbnail, final ViewHolderBoxOffice holder){
		HERE
	}

	@Override
	public int getItemCount() {
		return listMovies.size();
	}

	static class ViewHolderBoxOffice extends RecyclerView.ViewHolder{

		private ImageView movieThumbnail;
		private TextView movieTitle;
		private TextView movieReleaseDate;
		private RatingBar movieAudienceScore;

		public ViewHolderBoxOffice(View itemView){
			super(itemView);
			//Initialize the variables
			movieThumbnail = (ImageView) itemView.findViewById(R.id.movieThumbnail);
			movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
			movieReleaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
			movieAudienceScore = (RatingBar) itemView.findViewById(R.id.movieAudienceScore);
		}
	}
}
