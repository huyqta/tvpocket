package com.mobile.tvpocket.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.tvpocket.R;
import com.mobile.tvpocket.activities.ChannelCatalogActivity;
import com.mobile.tvpocket.activities.FavouriteActivity;
import com.mobile.tvpocket.activities.ReminderActivity;
import com.mobile.tvpocket.adapter.HomePagerAdapter;
import com.mobile.tvpocket.adapter.HomePagerAdapter.HomeDetailFragment;
import com.mobile.tvpocket.utils.GlobalConstants;

public class HomeFragment extends Fragment {

	View rootView;
	HomePagerAdapter mHomePagerAdapter;
	ViewPager mViewPager;

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_home, container, false);

		setupTabs();

		// Setup option menus
		this.setHasOptionsMenu(true);

		this.getActivity().getActionBar()
				.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Combo77GreenUnderline)));

		return rootView;
	}

	private void setupTabs() {
		try {
			List<HomePagerAdapter.HomeDetailFragment> listHDF = new ArrayList<HomePagerAdapter.HomeDetailFragment>();
			HomePagerAdapter.HomeDetailFragment fragm;
			fragm = new HomePagerAdapter.HomeDetailFragment(getResources().getString(R.string.on_air));
			listHDF.add(fragm);
			fragm = new HomePagerAdapter.HomeDetailFragment(getResources().getString(R.string.on_next));
			listHDF.add(fragm);
			mHomePagerAdapter = new HomePagerAdapter(getFragmentManager(), listHDF);
			mViewPager = (ViewPager) rootView.findViewById(R.id.home_pager);
			PagerTabStrip pagerTabStrip = (PagerTabStrip) rootView.findViewById(R.id.pager_title_strip);
			pagerTabStrip.setDrawFullUnderline(true);
			pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.Combo77GreenUnderline));
			mViewPager.setAdapter(mHomePagerAdapter);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.home, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		// handle item selection
		switch (item.getItemId()) {
		case android.R.id.home:
			this.getActivity().finish();
			return true;
		case R.id.home_my_favourite:
			intent = new Intent(this.getActivity(), FavouriteActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			GlobalConstants.HomeActivityDrawerLayout.closeDrawers();
			startActivity(intent);
			return true;
		case R.id.home_channel_catalog:
			intent = new Intent(this.getActivity(), ChannelCatalogActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			GlobalConstants.HomeActivityDrawerLayout.closeDrawers();
			startActivity(intent);
			return true;
		case R.id.home_my_reminder:
			intent = new Intent(this.getActivity(), ReminderActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			GlobalConstants.HomeActivityDrawerLayout.closeDrawers();
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}
}
