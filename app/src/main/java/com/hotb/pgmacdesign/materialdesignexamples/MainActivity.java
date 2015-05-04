package com.hotb.pgmacdesign.materialdesignexamples;

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

import com.hotb.pgmacdesign.materialdesignexamples.tabs.SlidingTabLayout;

/**
 * Instead of ActionBarActivity, need to use AppCompatActivity instead
 */
public class MainActivity extends AppCompatActivity {

	private Toolbar toolbar;
	NavigationDrawerFragment drawerFragment;

	//These 2 are for the tabs, remove them for doing nav drawer only
	private ViewPager mPager;
	private SlidingTabLayout mTabs;

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
		mTabs = (SlidingTabLayout) findViewById(R.id.tabs1);

		//This makes it so that if you have X tabs, it distributes them evenly, dynamically.
		mTabs.setDistributeEvenly(true);

		//Sets the color of the sliding bar underneath the tabs
		mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.primary_color_accent));

		//Sets the background color of the tabs
		mTabs.setBackgroundColor(getResources().getColor(R.color.white));

		//1)Set the custom layout 2) Textview of the data in the tab
		mTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
		mTabs.setViewPager(mPager);
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
			startActivity(new Intent(this, ActivityUsingTabLibrary.class));
		}

		return super.onOptionsItemSelected(item);
	}

	//This class is for the tabs, remove them for doing nav drawer only
	class MyPagerAdapter extends FragmentPagerAdapter {

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
			MyFragment myFragment = MyFragment.getInstance(position);
			return myFragment;
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
