package com.mobile.tvpocket.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.mobile.tvpocket.R;
import com.mobile.tvpocket.adapter.ChannelCatalogPagerAdapter;
import com.mobile.tvpocket.models.MyGroup;
import com.mobile.tvpocket.utils.GlobalConstants;

public class ChannelCatalogFragment extends Fragment {

	View rootView;
	ChannelCatalogPagerAdapter mChannelCatalogPagerAdapter;
	ViewPager mViewPager;

	public ChannelCatalogFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.fragment_channel_catalog, container, false);
		setupTabs();
		// Setup option menus
		this.setHasOptionsMenu(true);

		this.getActivity().getActionBar().setHomeButtonEnabled(true);
		this.getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		this.getActivity().getActionBar()
				.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Combo77GreenUnderline)));
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.channelcatalog, menu);
		List<String> listGroup = new ArrayList<String>();
		for (MyGroup mg : GlobalConstants.ListGroups) {
			listGroup.add(mg.getName());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity().getBaseContext(),
				R.layout.simple_list_item_1, listGroup);
		this.getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		/** Defining Navigation listener */
		ActionBar.OnNavigationListener navigationListener = new OnNavigationListener() {

			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				mViewPager.setCurrentItem(itemPosition);
				return false;
			}
		};

		/**
		 * Setting dropdown items and item navigation listener for the actionbar
		 */
		this.getActivity().getActionBar().setListNavigationCallbacks(adapter, navigationListener);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			this.getActivity().finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setupTabs() {
		try {
			mChannelCatalogPagerAdapter = new ChannelCatalogPagerAdapter(getFragmentManager(),
					GlobalConstants.ListGroups);
			mViewPager = (ViewPager) rootView.findViewById(R.id.channel_catalog_pager);
			PagerTabStrip pagerTabStrip = (PagerTabStrip) rootView.findViewById(R.id.pager_title_strip);
			pagerTabStrip.setDrawFullUnderline(true);
			pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.Combo77GreenUnderline));
			mViewPager.setAdapter(mChannelCatalogPagerAdapter);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

}
