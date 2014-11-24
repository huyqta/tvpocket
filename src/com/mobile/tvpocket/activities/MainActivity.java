package com.mobile.tvpocket.activities;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;

import com.mobile.tvpocket.R;
import com.mobile.tvpocket.fragments.HomeFragment;
import com.mobile.tvpocket.fragments.NavigationDrawerFragment;
import com.mobile.tvpocket.utils.GlobalConstants;
import com.splunk.mint.Mint;

public class MainActivity extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		Mint.initAndStartSession(MainActivity.this, "46a7ecb3");
		GlobalConstants.MainContext = this;

		// Them navigation sliding menu
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(
				R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

		// Goi fragment Home
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		HomeFragment homefragment = new HomeFragment();
		fragmentTransaction.add(R.id.fragment_container, homefragment, "HomeFragment");
		fragmentTransaction.commit();

	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// Thay doi activity khi chon tren sliding menu
		// displayView(position);
	}

	public void onSectionAttached(int number) {
		// Set title khi chon
		mTitle = GlobalConstants.ApplicationMenus.get(number);
		switch (number) {
		case 1:
			// mTitle = GlobalConstants.ApplicationMenus.get(number);
			break;
		case 2:
			// mTitle = getString(R.string.title_section2);
			break;
		case 3:
			// mTitle = getString(R.string.title_section3);
			break;
		}
	}

	// Chua tim hieu method nay :D
	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
		actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Combo77GreenUnderline)));
	}
}
