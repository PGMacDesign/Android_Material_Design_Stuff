package com.hotb.pgmacdesign.materialdesignexamples.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.hotb.pgmacdesign.materialdesignexamples.fragments.MyFragment;
import com.hotb.pgmacdesign.materialdesignexamples.R;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by pmacdowell on 5/4/2015.
 */
public class MainActivityUsingTabLibrary extends AppCompatActivity implements MaterialTabListener {

	private Toolbar toolbar;
	private MaterialTabHost tabHost;
	private ViewPager viewPager;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_using_tab_library);

		//Toolbar
		toolbar = (Toolbar) findViewById(R.id.app_bar1);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		//Tabhost
		tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
		//Pager
		viewPager = (ViewPager) findViewById(R.id.viewPager);

		//Viewpager adapter
		MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);

		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
			@Override
			public void onPageSelected(int position) {
				//Update the tab to be the item at the selected position
				tabHost.setSelectedNavigationItem(position);
			}
		});

		/*
		//Add the pages to the tabhost via a for loop. This adds text, omitting for now, using pictures instead.
		for (int i = 0; i<adapter.getCount(); i++){
			tabHost.addTab(tabHost.newTab()
				.setText(adapter.getPageTitle(i))
				.setTabListener(this)
			);
		}
		*/

		//Add the pages to the tabhost via a for loop. This adds photos.
		for (int i = 0; i<adapter.getCount(); i++){
			tabHost.addTab(tabHost.newTab()
							.setIcon(adapter.getIcon(i))
							.setTabListener(this)
			);
		}


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	//These 3 are all from the implementation
	/**
	 *
	 * @param materialTab
	 */
	public void onTabSelected(MaterialTab materialTab) {
		viewPager.setCurrentItem(materialTab.getPosition());
	}

	/**
	 *
	 * @param materialTab
	 */
	public void onTabReselected(MaterialTab materialTab) {

	}

	/**
	 *
	 * @param materialTab
	 */
	public void onTabUnselected(MaterialTab materialTab) {

	}

	//This class is for the tabs, remove them for doing nav drawer only
	class MyPagerAdapter extends FragmentPagerAdapter {

		//Text for the tabs. Not using atm. Using pictures instead
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
			return tabText[position];
		}

		//Gets the picture/ photo at that position in the array
		private Drawable getIcon(int position){
			return getResources().getDrawable(icons[position]);
		}

		//Gets the count of the number of tabs
		public int getCount() {
			return 3;
		}
	}

}
