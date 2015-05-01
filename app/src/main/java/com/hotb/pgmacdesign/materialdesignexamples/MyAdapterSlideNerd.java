package com.hotb.pgmacdesign.materialdesignexamples;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

	//private ClickListener clickListener; //From method 2 of onClick. Commenting out for now

	//List used to hold the data that will be put into the custom_row fields. Also, initializes it
	List<RecyclerViewData> data = Collections.emptyList();

	private Context context;

	//Constructor
	public MyAdapterSlideNerd(Context context, List<RecyclerViewData> aData){
		inflater = LayoutInflater.from(context);
		this.data = aData;
		this.context = context;
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
	 * This sets up the interface variable
	 */
	//public void setClickListener(ClickListener clickListener){ //From method 2 of onClick. Commenting out for now
		//this.clickListener = clickListener; //From method 2 of onClick. Commenting out for now
	//}

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

			icon.setOnClickListener(this); //This will set it do onClick() method

		}

		@Override
		public void onClick(View v) {
			//Toast.makeText(v.getContext(), "Item Clicked at " + getLayoutPosition(), Toast.LENGTH_SHORT).show();

			//Call a method to delete the item at the position
			//delete(getLayoutPosition());
			//Log.d("Item " + getLayoutPosition(), " Was Deleted");
			/*
			Apparently getPosition() is now deprecated, you should use one of these 2 instead:
			getLayoutPosition()
			getAdapterPosition()
			 */

			//if(clickListener != null){ //From method 2 of onClick. Commenting out for now
				//clickListener.itemClicked(v, getLayoutPosition()); //From method 2 of onClick. Commenting out for now
			//}
		}
	}

	//Interface
	//public interface ClickListener{ //From method 2 of onClick. Commenting out for now
		//public void itemClicked(View view, int position); //From method 2 of onClick. Commenting out for now
	//}
}
