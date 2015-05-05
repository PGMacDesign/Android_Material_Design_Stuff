package com.hotb.pgmacdesign.materialdesignexamples.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hotb.pgmacdesign.materialdesignexamples.R;
import com.hotb.pgmacdesign.materialdesignexamples.pojo.Movie;

import java.util.ArrayList;

/**
 * Created by pmacdowell on 5/5/2015.
 */
public class AdapterBoxOffice extends RecyclerView.Adapter<AdapterBoxOffice.ViewHolderBoxOffice> {

	//Movie list to hold data
	private ArrayList<Movie> listMovies = new ArrayList<>();

	private LayoutInflater layoutInflater;
	public AdapterBoxOffice(Context context){
		layoutInflater = LayoutInflater.from(context);
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
	public void onBindViewHolder(ViewHolderBoxOffice holder, int position) {
		//For the given position, put the data stored in the array into the view
	}

	@Override
	public int getItemCount() {
		return 0;
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
