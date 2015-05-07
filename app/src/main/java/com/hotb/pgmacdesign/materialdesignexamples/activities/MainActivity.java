package com.hotb.pgmacdesign.materialdesignexamples.activities;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hotb.pgmacdesign.materialdesignexamples.R;
import com.hotb.pgmacdesign.materialdesignexamples.fragments.FragmentBoxOffice;
import com.hotb.pgmacdesign.materialdesignexamples.fragments.FragmentSearch;
import com.hotb.pgmacdesign.materialdesignexamples.fragments.FragmentUpcoming;
import com.hotb.pgmacdesign.materialdesignexamples.fragments.NavigationDrawerFragment;
import com.hotb.pgmacdesign.materialdesignexamples.misc.L;
import com.hotb.pgmacdesign.materialdesignexamples.misc.SortListener;
import com.hotb.pgmacdesign.materialdesignexamples.services.MyService;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import me.tatarka.support.job.JobInfo;
import me.tatarka.support.os.PersistableBundle;

/**
 * Created by pmacdowell on 5/6/2015.
 */
public class MainActivity extends AppCompatActivity implements MaterialTabListener, View.OnClickListener {

	//int representing our 0th tab corresponding to the Fragment where search results are dispalyed
	public static final int TAB_SEARCH_RESULTS = 0;
	//int corresponding to our 1st tab corresponding to the Fragment where box office hits are dispalyed
	public static final int TAB_HITS = 1;
	//int corresponding to our 2nd tab corresponding to the Fragment where upcoming movies are displayed
	public static final int TAB_UPCOMING = 2;
	//int corresponding to the number of tabs in our Activity
	public static final int TAB_COUNT = 3;
	//int corresponding to the id of our JobSchedulerService
	private static final int JOB_ID = 100;
	//tag associated with the FAB menu button that sorts by name
	private static final String TAG_SORT_NAME = "sortName";
	//tag associated with the FAB menu button that sorts by date
	private static final String TAG_SORT_DATE = "sortDate";
	//tag associated with the FAB menu button that sorts by ratings
	private static final String TAG_SORT_RATINGS = "sortRatings";
	//Run the JobSchedulerService every 2 minutes
	private static final long POLL_FREQUENCY = 28800000;
	private Toolbar mToolbar;
	//a layout grouping the toolbar and the tabs together
	private ViewGroup mContainerToolbar;
	private MaterialTabHost mTabHost;
	private ViewPager mPager;
	private ViewPagerAdapter mAdapter;
	private FloatingActionButton mFAB;
	private FloatingActionMenu mFABMenu;
	private NavigationDrawerFragment mDrawerFragment;

	//Creating the service here as it will be needed in multiple fragments
	private me.tatarka.support.job.JobScheduler mJobScheduler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupFAB();
		setupTabs();
		setupJob();
		setupDrawer();
		//animate the Toolbar when it comes into the picture
		//AnimationUtils.animateToolbarDroppingDown(mContainerToolbar);

	}

	private void setupDrawer() {
		mToolbar = (Toolbar) findViewById(R.id.app_bar1);
		//mContainerToolbar = (ViewGroup) findViewById(R.id.container_app_bar);
		//set the Toolbar as ActionBar
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		//setup the NavigationDrawer
		//mDrawerFragment = (NavigationDrawerFragment)
				//getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

		//mDrawerFragment.setup(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
	}


	public void onDrawerItemClicked(int index) {
		mPager.setCurrentItem(index);
	}

	public View getContainerToolbar() {
		return mContainerToolbar;
	}

	private void setupTabs() {
		mTabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
		mPager = (ViewPager) findViewById(R.id.viewPager);
		mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mAdapter);
		//when the page changes in the ViewPager, update the Tabs accordingly
		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				mTabHost.setSelectedNavigationItem(position);

			}
		});
		//Add all the Tabs to the TabHost
		for (int i = 0; i < mAdapter.getCount(); i++) {
			mTabHost.addTab(
					mTabHost.newTab()
							.setIcon(mAdapter.getIcon(i))
							.setTabListener(this));
		}
	}


	private void setupJob() {
		mJobScheduler = me.tatarka.support.job.JobScheduler.getInstance(this);
		//set an initial delay with a Handler so that the data loading by the JobScheduler does not clash with the loading inside the Fragment
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				//schedule the job after the delay has been elapsed
				buildJob();
			}
		}, 30000);
	}


	private void buildJob() {
		//attach the job ID and the name of the Service that will work in the background
		JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this, MyService.class));

		//To send data between classes
		PersistableBundle persistableBundle = new PersistableBundle();
		persistableBundle.putString("useless_key", "useless_value");

		//set periodic polling that needs net connection and works across device reboots
		builder.setPeriodic(POLL_FREQUENCY)
				.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) //As in, they are not being billed for the data here
				//.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) //As in, they can be on any network
				//.setRequiresCharging(true)//As in, they must be charging for this to run
				//.setRequiresDeviceIdle(true) //As in, device must be idle for this service to run
				.setExtras(persistableBundle) //Add bundle of extras here to pass to a new activity if needed. ONLY primitive types allowed
				//.setMinimumLatency(1000) //DO NOT RUN THIS in conjunction with setPeriodic!
				//.setOverrideDeadline( long maxExecutionDelayMillis)//Delays this in max before starting. DO NOT RUN THIS in conjunction with setPeriodic!
				.setPersisted(true); //Will run affter device reboots if set to true
		//Don't forget to add it to the job scheduler object
		mJobScheduler.schedule(builder.build());
	}

	private void setupFAB() {
		//define the icon for the main floating action button
		ImageView iconFAB = new ImageView(this);
		iconFAB.setImageResource(R.drawable.ic_launcher);

		//set the appropriate background for the main floating action button along with its icon
		mFAB = new FloatingActionButton.Builder(this)
				.setContentView(iconFAB)
				.setBackgroundDrawable(R.drawable.ic_launcher)
				.build();

		//define the icons for the sub action buttons
		ImageView iconSortName = new ImageView(this);
		iconSortName.setImageResource(R.drawable.ic_launcher);
		ImageView iconSortDate = new ImageView(this);
		iconSortDate.setImageResource(R.drawable.ic_launcher);
		ImageView iconSortRatings = new ImageView(this);
		iconSortRatings.setImageResource(R.drawable.ic_launcher);

		//set the background for all the sub buttons
		SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
		itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_launcher));


		//build the sub buttons
		SubActionButton buttonSortName = itemBuilder.setContentView(iconSortName).build();
		SubActionButton buttonSortDate = itemBuilder.setContentView(iconSortDate).build();
		SubActionButton buttonSortRatings = itemBuilder.setContentView(iconSortRatings).build();

		//to determine which button was clicked, set Tags on each button
		buttonSortName.setTag(TAG_SORT_NAME);
		buttonSortDate.setTag(TAG_SORT_DATE);
		buttonSortRatings.setTag(TAG_SORT_RATINGS);

		buttonSortName.setOnClickListener(this);
		buttonSortDate.setOnClickListener(this);
		buttonSortRatings.setOnClickListener(this);

		//add the sub buttons to the main floating action button
		mFABMenu = new FloatingActionMenu.Builder(this)
				.addSubActionView(buttonSortName)
				.addSubActionView(buttonSortDate)
				.addSubActionView(buttonSortRatings)
				.attachTo(mFAB)
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
			L.m("Settings selected");
			return true;
		}
		/*
		if (id == R.id.action_touch_intercept_activity) {
			startActivity(new Intent(this, ActivityTouchEvent.class));
		}

		if (R.id.action_activity_calling == id) {
			startActivity(new Intent(this, ActivityA.class));
		}
		if (R.id.action_shared_transitions == id) {
			startActivity(new Intent(this, ActivitySharedA.class));
		}
		if (R.id.action_tabs_using_library == id) {
			startActivity(new Intent(this, ActivitySlidingTabLayout.class));
		}
		if (R.id.action_vector_test_activity == id) {
			startActivity(new Intent(this, ActivityVectorDrawable.class));
		}

		if (R.id.action_dynamic_tabs_activity == id) {
			startActivity(new Intent(this, ActivityDynamicTabs.class));
		}
		if (R.id.action_recycler_item_animations == id) {
			startActivity(new Intent(this, ActivityRecylerAnimators.class));
		}
		*/
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onTabSelected(MaterialTab materialTab) {
		//when a Tab is selected, update the ViewPager to reflect the changes
		mPager.setCurrentItem(materialTab.getPosition());
	}

	@Override
	public void onTabReselected(MaterialTab materialTab) {
	}

	@Override
	public void onTabUnselected(MaterialTab materialTab) {
	}

	@Override
	public void onClick(View v) {
		//call instantiate item since getItem may return null depending on whether the PagerAdapter is of type FragmentPagerAdapter or FragmentStatePagerAdapter
		//Done to prevent null pointer errors and instantiate things first.
		Fragment fragment = (Fragment) mAdapter.instantiateItem(mPager, mPager.getCurrentItem());

		if (fragment instanceof SortListener) {

			if (v.getTag().equals(TAG_SORT_NAME)) {
				//call the sort by name method on any Fragment that implements sortlistener
				((SortListener) fragment).onSortByName();
			}
			if (v.getTag().equals(TAG_SORT_DATE)) {
				//call the sort by date method on any Fragment that implements sortlistener
				((SortListener) fragment).onSortByDate();
			}
			if (v.getTag().equals(TAG_SORT_RATINGS)) {
				//call the sort by ratings method on any Fragment that implements sortlistener
				((SortListener) fragment).onSortByRating();
			}
		}


	}


	private void toggleTranslateFAB(float slideOffset) {
		if (mFABMenu != null) {
			if (mFABMenu.isOpen()) {
				mFABMenu.close(true);
			}
			mFAB.setTranslationX(slideOffset * 200);
		}
	}

	public void onDrawerSlide(float slideOffset) {
		toggleTranslateFAB(slideOffset);
	}

	private class ViewPagerAdapter extends FragmentStatePagerAdapter {

		int icons[] = {R.drawable.ic_launcher,
				R.drawable.ic_launcher,
				R.drawable.ic_launcher};

		FragmentManager fragmentManager;

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
			fragmentManager = fm;
		}

		public Fragment getItem(int num) {
			Fragment fragment = null;
//            L.m("getItem called for " + num);
			switch (num) {
				case TAB_SEARCH_RESULTS:
					fragment = FragmentSearch.newInstance("", "");
					break;
				case TAB_HITS:
					fragment = FragmentBoxOffice.newInstance("", "");
					break;
				case TAB_UPCOMING:
					fragment = FragmentUpcoming.newInstance("", "");
					break;
			}
			return fragment;

		}

		@Override
		public int getCount() {
			return TAB_COUNT;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return getResources().getStringArray(R.array.tabs_array)[position];
		}

		private Drawable getIcon(int position) {
			return getResources().getDrawable(icons[position]);
		}
	}
}