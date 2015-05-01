package com.hotb.pgmacdesign.materialdesignexamples;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
		toolbar = (Toolbar) findViewById(R.id.app_bar11);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		//To be passed into the drawer fragment
		DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		//Sliding Nav drawer
		drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nav_drawer1);
		drawerFragment.setup(R.id.fragment_nav_drawer1, drawerLayout, toolbar); //ERROR IS HERE
		//ERROR ABOVE
		//These 4 are for the tabs, remove them for doing nav drawer only
		mPager = (ViewPager) findViewById(R.id.fragment_nav_drawer1);/////////////////////
		mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		mTabs = (SlidingTabLayout) findViewById(R.id.tabs1);
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

		return super.onOptionsItemSelected(item);
	}

	//This class is for the tabs, remove them for doing nav drawer only
	class MyPagerAdapter extends FragmentPagerAdapter {

		String[] tabs;

		//Constructor
		public MyPagerAdapter(FragmentManager fm){
			super(fm);
		}

		//creates a new instance and returns a Fragment
		public Fragment getItem(int position) {
			MyFragment myFragment = MyFragment.getInstance(position);
			return myFragment;
		}

		//Gets the title at that position in the array
		public CharSequence getPageTitle(int position){
			return tabs[position];
		}

		//Gets the count of the number of tabs
		public int getCount() {
			return 3;
		}
	}

	//Some class used for the fragment
	public static class MyFragment extends Fragment {

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

			textView = (TextView) textView.findViewById(R.id.position_in_fragment);

			Bundle bundle = getArguments();
			if(bundle != null){
				textView.setText(bundle.getInt("position"));
			}

			return layout;
		}
	}
}
