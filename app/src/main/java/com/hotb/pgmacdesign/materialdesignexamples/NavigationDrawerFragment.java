package com.hotb.pgmacdesign.materialdesignexamples;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class NavigationDrawerFragment extends Fragment {//implements { //MyAdapterSlideNerd.ClickListener {  //From method 2 of onClick. Commenting out for now

	public static final String PREF_FILE_NAME = "navPref";
	public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;

	//RecyclerView
	private RecyclerView recyclerView;

	//Whether the user is aware of the drawer's existence.
	private boolean mUserLearnedDrawer;
	private boolean mFromSavedInstanceState;

	//Custom RecyclerView adapter
	private MyAdapterSlideNerd adapter;

	//This is the view of the fragment, it is initialized when the setup is called
	private View containerView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Check if the app has been opened before. Typecasts the result as a boolean
		mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
		//Coming back from a rotation
		if(savedInstanceState != null){
			mFromSavedInstanceState = true;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false); //Correct one
		//View view = inflater.inflate(R.layout.check_margins_template, container, false); //Used for checking margins

		//Setup the recycler view
		recyclerView = (RecyclerView) view.findViewById(R.id.drawerRecyclerView);

		//Arraylist of info to pass
		adapter = new MyAdapterSlideNerd(getActivity(), getData());
		//This fragment is the object which implements the click listener
		//adapter.setClickListener(this); //From method 2 of onClick. Commenting out for now

		recyclerView.setAdapter(adapter);
		//Need to set how the view looks else it will not run
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		//This is being set so that the separate class on the bottom (RecyclerTouchListener) can handle all click events
		recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
			//Implementing from the interface. These actually handle the resulting on click events
			public void onClick(View view, int position) {
				Toast.makeText(getActivity(), "Clicked at position "+position, Toast.LENGTH_SHORT).show();

				//TESTING

			}

			//Implementing from the interface
			public void onLongClick(View view, int position) {
				Toast.makeText(getActivity(), "Long Clicked at position "+position, Toast.LENGTH_SHORT).show();

				//TESTING

			}
		}));

		return view;
	}

	//Generic class to get some data for the
	public static List<RecyclerViewData> getData(){
		//The list to be returned.
		List<RecyclerViewData> data = new ArrayList<>();

		//WILL LIKELY WANT TO USE HASH MAP INSTEAD OF 2 ARRAYS

		//List of the icons used
		int[] icons = {
				R.drawable.abc_ic_menu_cut_mtrl_alpha, //1
				R.drawable.abc_btn_switch_to_on_mtrl_00012, //2
				R.drawable.ic_launcher, //3
				R.drawable.abc_ic_menu_share_mtrl_alpha //4
			};

		//List of the titles used
		String[] titles = {
				"abc btn default mtrl shape", //1
				"abc btn borderless material", //2
				"abc btn rating star off mtrl alpha", //3
				"abc ic menu share mtrl alpha" //4
		};

		/*
		//Loop through and add it to the list
		for (int i = 0; i<titles.length && i < icons.length ; i++){
			//Create an empty object
			RecyclerViewData current = new RecyclerViewData();

			//Using setter methods, set the properties of the object
			current.setIconID(icons[i]);
			current.setTitle(titles[i]);

			//Add the object to the list
			data.add(current);
		}
		*/

		//IMPORTANT! This is used only to generate a larger list, DO NOT USE THIS in normal
		for (int i = 0; i<100 ; i++){
			//Create an empty object
			RecyclerViewData current = new RecyclerViewData();

			//Using setter methods, set the properties of the object
			current.setIconID(icons[i%icons.length]); //The modulus operator  here loops through a ton
			current.setTitle(titles[i%titles.length]); //of times to overfill the list to 100 items

			//Add the object to the list
			data.add(current);
		}

		//return the list
		return data;
	}

	public void setup(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
		Log.d("Line ", "157");
		//Initialize the containerView
		containerView = getView().findViewById(fragmentId);
		Log.d("Line ", "160");
		this.mDrawerLayout = drawerLayout;
		Log.d("Line ", "162");
		this.mDrawerToggle = new ActionBarDrawerToggle(getActivity(),
				drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

			//Called when the drawer starts sliding
			public void onDrawerSlide(View drawerView, float slideOffset) {
				Log.d("Line ", "168");
				super.onDrawerSlide(drawerView, slideOffset);
				Log.d("Line ", "170");
				/*
				The slideOffset is how much (in decimal format) the drawer is out. It starts at 0.0
				which means it is in. As it slides out, it increases until it is all the way out and
				changes to 1.0 at max. Essentially it is a scale.
				 */
				//This if statement prevents the screen from darkening completely
				if(slideOffset <.68) {
					//This sets it so that the screen darkens as it slides out
					toolbar.setAlpha(1 - slideOffset);
					Log.d("Line ", "180");
				}
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				//Checks if it has been run for the first time
				if(!mUserLearnedDrawer){
					mUserLearnedDrawer = true;
					/*
					This will save the fact that they have opened it for the first time to preferences
					USEFUL TIP: if you add +"" to a boolean, it converts it to a String so you don't
					have to type the Boolean.toString() call every time.
					 */
					saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, Boolean.toString(mUserLearnedDrawer));
				}

				//This will make it so that the Activity drops the action bar while the drawer is triggered
				getActivity().invalidateOptionsMenu();
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				//This will make it so that the Activity drops the action bar while the drawer is triggered
				getActivity().invalidateOptionsMenu();
			}

			@Override
			public void onDrawerStateChanged(int newState) {
				super.onDrawerStateChanged(newState);
			}
		};

		//This is the first time they opened the drawer
		if(!mUserLearnedDrawer && !mFromSavedInstanceState){
			//This physically opens the drawer
			mDrawerLayout.openDrawer(containerView);
		}
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		//This creates the button (hamburger / 3 lines) that will open the drawer
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerLayout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});
	}

	/**
	 * Saves to the preferences (ADDED STATIC)
	 * @param context Context
	 * @param preferenceName name to be saved to
	 * @param preferenceValue value of the named object
	 */
	public static void saveToPreferences(Context context, String preferenceName, String preferenceValue){
		SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(preferenceName, preferenceValue);
		//editor.commit(); //Slower than apply
		editor.apply();
	}

	/**
	 * Reads from the shared preferences
	 * @param context Context
	 * @param preferenceName name to be referenced to look up
	 * @param defaultValue default value returned if nothing is in the stored spot
	 * @return Returns the string value of the preference name object
	 */
	public static String readFromPreferences(Context context, String preferenceName, String defaultValue){
		SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
		return sharedPreferences.getString(preferenceName, defaultValue);
	}

	//From the MyAdapterSlideNerd adapter class interface
	//public void itemClicked(View view, int position) { //From method 2 of onClick. Commenting out for now
		//startActivity(new Intent(getActivity(), SubActivity.class)); //From method 2 of onClick. Commenting out for now
	//}

	class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

		//Click Listener
		private ClickListener clickListener;

		//This is used to detect whether it was a short click, long click, click and move, etc. Needs a motion event object
		private GestureDetector gestureDetector;

		//Constructor
		public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){

			//Initialize the click Listener
			this.clickListener = clickListener;

			//Gesture Detector
			gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
				//Long click
				public void onLongPress(MotionEvent e) {
					//Pass in the x and y coords of the motion event and it finds what child is at those coords
					View child = recyclerView.findChildViewUnder(e.getX(), e.getY());

					//Check if the click listener is null!
					if(child != null && clickListener != null){
						clickListener.onLongClick(child, recyclerView.getChildLayoutPosition(child)); //Check into getChildAdapterPosition as well
					}
					super.onLongPress(e);
				}

				//Single tap
				public boolean onSingleTapUp(MotionEvent e) {
					return true;
				}

				//IGNORE THE REST, EXPERIMENTING FOR LATER
				/*
				@Override
				public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
					return super.onScroll(e1, e2, distanceX, distanceY);
				}

				@Override
				public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
					return super.onFling(e1, e2, velocityX, velocityY);
				}

				@Override
				public void onShowPress(MotionEvent e) {
					super.onShowPress(e);
				}

				@Override
				public boolean onDown(MotionEvent e) {
					return super.onDown(e);
				}

				@Override
				public boolean onDoubleTap(MotionEvent e) {
					return super.onDoubleTap(e);
				}

				@Override
				public boolean onDoubleTapEvent(MotionEvent e) {
					return super.onDoubleTapEvent(e);
				}

				@Override
				public boolean onSingleTapConfirmed(MotionEvent e) {
					return super.onSingleTapConfirmed(e);
				}
				*/
			});

		}

		/**
		 * This is used to forward the touch events to the gesture detector.
		 * @param rv
		 * @param e
		 * @return
		 */
		public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
			//This intercepts the event and passes it to the gesture detector
			View child = rv.findChildViewUnder(e.getX(), e.getY());

			/*
			If the view is not null, AND the click listener is not null AND the
			gesture handled an event, then proceed.
			 */
			if(child != null && clickListener != null && gestureDetector.onTouchEvent(e)){
				//Kick the event to the user
				clickListener.onClick(child, rv.getChildLayoutPosition(child)); //Check into getChildAdapterPosition as well
			}

			//gestureDetector.onTouchEvent(e);
			return false;
		}

		/**
		 *
		 * @param rv
		 * @param e
		 */
		public void onTouchEvent(RecyclerView rv, MotionEvent e) {

		}
	}

	//Custom interface for 2 of the click methods
	public static interface ClickListener{
		public void onClick(View view, int position);
		public void onLongClick(View view, int position);
	}
}
