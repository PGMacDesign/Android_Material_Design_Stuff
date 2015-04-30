package com.hotb.pgmacdesign.materialdesignexamples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Instead of ActionBarActivity, need to use AppCompatActivity instead
 */
public class MainActivity extends AppCompatActivity {

	private Toolbar toolbar;
	NavigationDrawerFragment drawerFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_appbar);

		//Toolbar on top of screen
		toolbar = (Toolbar) findViewById(R.id.app_bar1);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		//To be passed into the drawer fragment
		DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		//Sliding Nav drawer
		drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nav_drawer);
		drawerFragment.setup(R.id.fragment_nav_drawer, drawerLayout, toolbar);

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

}
