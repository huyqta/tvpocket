package com.mobile.tvpocket.activities;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mobile.tvpocket.R;
import com.mobile.tvpocket.fragments.AdFragment;
import com.mobile.tvpocket.fragments.HomeFragment;
import com.mobile.tvpocket.fragments.NavigationDrawerFragment;
import com.mobile.tvpocket.utils.GlobalConstants;
import com.splunk.mint.Mint;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

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
		AdFragment adfragment = new AdFragment();
		fragmentTransaction.add(R.id.adFragment, adfragment, "AdFragment");
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

	/**
	 * This class makes the ad request and loads the ad.
	 */
	// public static class AdFragment extends Fragment {
	//
	// private AdView mAdView;
	//
	// public AdFragment() {
	// }
	//
	// @Override
	// public void onActivityCreated(Bundle bundle) {
	// super.onActivityCreated(bundle);
	//
	// // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
	// // values/strings.xml.
	// mAdView = (AdView) getView().findViewById(R.id.adView);
	//
	// // Create an ad request. Check logcat output for the hashed device ID to
	// // get test ads on a physical device. e.g.
	// // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
	// AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
	//
	// // Start loading the ad in the background.
	// mAdView.loadAd(adRequest);
	// }
	//
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	// return inflater.inflate(R.layout.fragment_ad, container, false);
	// }
	//
	// /** Called when leaving the activity */
	// @Override
	// public void onPause() {
	// if (mAdView != null) {
	// mAdView.pause();
	// }
	// super.onPause();
	// }
	//
	// /** Called when returning to the activity */
	// @Override
	// public void onResume() {
	// super.onResume();
	// if (mAdView != null) {
	// mAdView.resume();
	// }
	// }
	//
	// /** Called before the activity is destroyed */
	// @Override
	// public void onDestroy() {
	// if (mAdView != null) {
	// mAdView.destroy();
	// }
	// super.onDestroy();
	// }
	//
	// }
}
