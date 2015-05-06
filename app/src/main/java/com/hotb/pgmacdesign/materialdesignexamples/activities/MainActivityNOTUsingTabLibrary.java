package com.hotb.pgmacdesign.materialdesignexamples.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.hotb.pgmacdesign.materialdesignexamples.R;
import com.hotb.pgmacdesign.materialdesignexamples.fragments.FragmentBoxOffice;
import com.hotb.pgmacdesign.materialdesignexamples.fragments.FragmentSearch;
import com.hotb.pgmacdesign.materialdesignexamples.fragments.FragmentUpcoming;
import com.hotb.pgmacdesign.materialdesignexamples.fragments.NavigationDrawerFragment;
import com.hotb.pgmacdesign.materialdesignexamples.misc.L;
import com.hotb.pgmacdesign.materialdesignexamples.tabs.SlidingTabLayout;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Instead of ActionBarActivity, need to use AppCompatActivity instead
 */
public class MainActivityNOTUsingTabLibrary extends AppCompatActivity implements MaterialTabListener , View.OnClickListener{

	private Toolbar toolbar;
	NavigationDrawerFragment drawerFragment;

	//These 2 are for the tabs, remove them for doing nav drawer only
	private ViewPager mPager;
	private SlidingTabLayout mTabs;

	//Used for distinguishing the tabs
	public static final int MOVIES_SEARCH_RESULTS=0;
	public static final int MOVIES_HITS=1;
	public static final int MOVIES_UPCOMING=2;

	/*
	These 'tags' are used for the buttons on the overlay floating button. As you COULD make
	global buttons, initialize them, and use them, that would require defining them in the
	XML file, which we are not doing as we are using a library. Therefore, using these custom
	tags (String) instead.
	 */
	private static final String TAG_SORT_NAME = "sortName";
	private static final String TAG_SORT_DATE = "sortDate";
	private static final String TAG_SORT_RATINGS = "sortRatings";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main_appbar);
		setContentView(R.layout.activity_main);

		//Toolbar on top of screen
		toolbar = (Toolbar) findViewById(R.id.app_bar1);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		//To be passed into the drawer fragment
		DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout2);

		//Sliding Nav drawer
		drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
		drawerFragment.setup(R.id.fragment_navigation_drawer, drawerLayout, toolbar);

		//ERROR ABOVE
		//These 4 are for the tabs, remove them for doing nav drawer only
		mPager = (ViewPager) findViewById(R.id.viewPager);
		mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		//mTabs = (SlidingTabLayout) findViewById(R.id.tabs1);

		//This makes it so that if you have X tabs, it distributes them evenly, dynamically.
		mTabs.setDistributeEvenly(true);

		//Sets the color of the sliding bar underneath the tabs
		mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.primary_color_accent));

		//Sets the background color of the tabs
		mTabs.setBackgroundColor(getResources().getColor(R.color.white));

		//1)Set the custom layout 2) Textview of the data in the tab
		mTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
		mTabs.setViewPager(mPager);

		/*
		This next section is all about the icon builder. I am putting it in a try catch simply
		so that it can be differentiated out. See the notes/ comments in the build.gradle file
		for more details on the link to the github page.
		 */
		try {
			setupFloatingButton();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * This is the floating button that appears over the overlay
	 */
	private void setupFloatingButton(){
		//This is going to be used for the floating options menu button
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.ic_launcher); //Change the button picture here

		//Continuing with the floating options menu button
		FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
				.setContentView(imageView)
						//.setBackgroundDrawable(R.drawable.selector_button_custom)//Set a background
						//Removing the background for now as it makes the button square vs circle.
				.build();

		//Create menu icons for the 3 options when the button is clicked.
		ImageView iconSortName = new ImageView(this);
		ImageView iconSortDate = new ImageView(this);
		ImageView iconSortRatings = new ImageView(this);

		iconSortName.setImageResource(R.drawable.abc_ic_menu_copy_mtrl_am_alpha);
		iconSortDate.setImageResource(R.drawable.abc_ic_menu_copy_mtrl_am_alpha);
		iconSortRatings.setImageResource(R.drawable.abc_ic_menu_copy_mtrl_am_alpha);

		//Sub action bar builder
		SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

		//To create a custom background like above. Removing for now for visibility
		//itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_button_custom));

		//Create the buttons and add the icons to it using the itemBuilder object
		SubActionButton buttonSortName = itemBuilder.setContentView(iconSortName).build();
		SubActionButton buttonSortDate = itemBuilder.setContentView(iconSortDate).build();
		SubActionButton buttonSortRatings = itemBuilder.setContentView(iconSortRatings).build();

		//Set the tags, these are used as identifiers since we are not declaring it via the XML
		buttonSortName.setTag(TAG_SORT_NAME);
		buttonSortDate.setTag(TAG_SORT_DATE);
		buttonSortRatings.setTag(TAG_SORT_RATINGS);

		//Set on to onClickListener
		buttonSortName.setOnClickListener(this);
		buttonSortDate.setOnClickListener(this);
		buttonSortRatings.setOnClickListener(this);

		//Add these buttons to the main floating menu button
		FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
				.addSubActionView(buttonSortName)
				.addSubActionView(buttonSortDate)
				.addSubActionView(buttonSortRatings)
				.attachTo(actionButton)
				.build();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		if (id == R.id.Navigate){
			startActivity(new Intent(this, SubActivity.class));
		}

		if (id == R.id.open_activity_using_tab_library){
			startActivity(new Intent(this, MainActivityUsingTabLibrary.class));
		}

		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onTabSelected(MaterialTab materialTab) {
	}

	@Override
	public void onTabReselected(MaterialTab materialTab) {
	}

	@Override
	public void onTabUnselected(MaterialTab materialTab) {
	}

	/**
	 * On Click listener for buttons
	 * @param v View being passed
	 */
	@Override
	public void onClick(View v) {

		//Using the tags
		if(v.getTag().equals(TAG_SORT_NAME)){
			L.m("Name was clicked");

		}
		if(v.getTag().equals(TAG_SORT_DATE)){
			L.m("Date was clicked");

		}
		if(v.getTag().equals(TAG_SORT_RATINGS)){
			L.m("Ratings was clicked");
		}

	}

	/*
	This class is for the tabs, remove them for doing nav drawer only
	NOTE! The FragmentPagerAdapter is used to save the state of each
	fragment when it is switched to a different one. This essentially
	calls savedInstanceState when it goes back to the previous fragment.
	 */
	private class MyPagerAdapter extends FragmentPagerAdapter {

		//Text for the tabs
		String[] tabText;
		//Pictures for the tabs
		int icons[] = {R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher};


		//Constructor
		public MyPagerAdapter(FragmentManager fm){
			super(fm);
			tabText = getResources().getStringArray(R.array.tabs_array);
		}

		//creates a new instance and returns a Fragment
		public Fragment getItem(int position) {
			//MyFragment myFragment = MyFragment.getInstance(position);

			Fragment fragment = null;
			//Inflates the right fragment depending on which position is up
			switch(position){
				case MOVIES_SEARCH_RESULTS:
					fragment = FragmentSearch.newInstance("", ""); //Empty params for now
					break;

				case MOVIES_HITS:
					fragment = FragmentBoxOffice.newInstance("", ""); //Empty params for now
					break;

				case MOVIES_UPCOMING:
					fragment = FragmentUpcoming.newInstance("", ""); //Empty params for now
					break;
			}

			return fragment;
		}

		//Gets the title at that position in the array
		public CharSequence getPageTitle(int position){

			//'Spanable'
			Drawable drawable = getResources().getDrawable(icons[position]);

			//If you do not set the boundaries here, the image will not be shown
			drawable.setBounds(0, 0, 72, 72); //Need to change this to support multiple screen sizes

			//This embeds the image inside the text using the Spanable class
			ImageSpan imageSpan = new ImageSpan(drawable);
			SpannableString spannableString = new SpannableString(" "); //MUST have a space here
			/*Merge the string and text together
			@Params:
			1) What is the object you want to merge
			2) Where do you want to start it
			3) Where do you want to end it
			4) How do you want to merge it. (4 Tag options, look under SPAN_INCLUSIVE... etc).
			 */
			spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

			return spannableString;
		}

		//Gets the count of the number of tabs
		public int getCount() {
			return 3;
		}
	}


}
