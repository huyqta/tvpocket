package com.mobile.tvpocket.adapter;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.mobile.tvpocket.R;
import com.mobile.tvpocket.models.MyChannel;
import com.mobile.tvpocket.models.MyGroup;
import com.mobile.tvpocket.services.ChannelService;
import com.mobile.tvpocket.utils.GlobalConstants.CustomLayout;

public class ChannelCatalogPagerAdapter extends FragmentPagerAdapter {

	List<MyGroup> listGroups;

	public ChannelCatalogPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
		// TODO Auto-generated constructor stub
	}

	public ChannelCatalogPagerAdapter(FragmentManager fragmentManager, List<MyGroup> lGroups) {
		super(fragmentManager);
		// TODO Auto-generated constructor stub
		this.listGroups = lGroups;
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		Fragment mChannelCatalogDetailFragment = new ChannelCatalogDetailFragment(this.listGroups.get(position).getId());
		return mChannelCatalogDetailFragment;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return this.listGroups.get(position).getName();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.listGroups.size();
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}

	// //////////////////////////////////////
	// ----- ChannelCatalogFragment ------//
	// //////////////////////////////////////
	public static class ChannelCatalogDetailFragment extends Fragment {

		public int ChannelGroup;

		public ChannelCatalogDetailFragment() {
			
		}
		
		public ChannelCatalogDetailFragment(int channelgrp) {
			this.ChannelGroup = channelgrp;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
				@Nullable Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_channel_catalog_detail, container, false);

			GridView gridview_detail = (GridView) rootView.findViewById(R.id.gridview_fragment_channel_catalog_detail);
			List<MyChannel> filChannels = ChannelService.FilterChannelsByGroup(ChannelGroup);

			CustomListviewImageTextAdapter mCustomListviewImageTextAdapter = new CustomListviewImageTextAdapter(
					container.getContext(), filChannels, CustomLayout.CHANNEL_CATALOG_ITEM, filChannels.size());

			mCustomListviewImageTextAdapter.setFragmentManager(this.getFragmentManager());

			gridview_detail.setAdapter(mCustomListviewImageTextAdapter);
			return rootView;
		}
	}

}
