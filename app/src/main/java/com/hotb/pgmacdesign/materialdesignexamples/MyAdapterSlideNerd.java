package com.hotb.pgmacdesign.materialdesignexamples;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * This takes in a VH object (View Holder) as it is expecting you to have a ViewHolder passed in
 * Created by pmacdowell on 4/30/2015.
 */
public class MyAdapterSlideNerd extends RecyclerView.Adapter<MyAdapterSlideNerd.MyViewHolder>{

	private final LayoutInflater inflater;

	//List used to hold the data that will be put into the custom_row fields. Also, initializes it
	List<RecyclerViewData> data = Collections.emptyList();

	//Constructor
	public MyAdapterSlideNerd(Context context, List<RecyclerViewData> aData){
		inflater = LayoutInflater.from(context);
		this.data = aData;
	}

	/**
	 * This deletes an item from the recyclerView
	 * @param position position of the item (Zero Based!)
	 */
	public void delete(int position){
		data.remove(position);
		/*
		You DO NOT call notifyDatasetChanged here, it is costly, instead use an alternative. IE:
		notifyItemChanged(int);
		notifyItemInserted(int);
		notifyItemRemoved(int);
		notifyItemRangeChanged(int, int);
		notifyItemRangeInserted(int, int);
		notifyItemRangeRemoved(int, int);
		 */
		notifyItemRemoved(position);

	}

	/**
	 * Every time a new row is to be displayed, this will  be called
	 * @param parent
	 * @param viewType
	 * @return
	 */
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		//Layout inflater. This represents the root of the custom row.
		View view = inflater.inflate(R.layout.custom_row, parent, false);

		MyViewHolder holder = new MyViewHolder(view);

		return holder;
	}

	/**
	 * This is where you bind the data to the specific row you are updating
	 * @param holder
	 * @param position
	 */
	public void onBindViewHolder(MyViewHolder holder, int position) {
		//Gives us the current information object
		RecyclerViewData current = data.get(position);

		//Set the fields
		holder.title.setText(current.title);
		holder.icon.setImageResource(current.iconID);


	}

	/**
	 * Size of the items
	 * @return the int size of the List of RecyclerViewData objects
	 */
	public int getItemCount() {
		return data.size();
	}

	/**
	 * This is the holder of the custom row that keeps it in memory
	 */
	class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

		TextView title;
		ImageView icon;

		/**
		 * Constructor
		 * @param itemView
		 */
		public MyViewHolder(View itemView) {
			super(itemView);

			//Define the image view and text view from the custom_row.xml
			title = (TextView) itemView.findViewById(R.id.custom_view_text);
			icon = (ImageView) itemView.findViewById(R.id.custom_view_image);

			icon.setOnClickListener(this);

		}

		@Override
		public void onClick(View v) {
			//Toast.makeText(v.getContext(), "Item Clicked at " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
			delete(getLayoutPosition());
			Log.d("Item " + getLayoutPosition(), " Was Deleted");
			/*
			Apparently getPosition() is now deprecated, you should use one of these 2 instead:
			getLayoutPosition()
			getAdapterPosition()
			 */
		}
	}
}
