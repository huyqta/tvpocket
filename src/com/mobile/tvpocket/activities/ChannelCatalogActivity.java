package com.mobile.tvpocket.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.mobile.tvpocket.R;
import com.mobile.tvpocket.fragments.ChannelCatalogFragment;
import com.splunk.mint.Mint;

public class ChannelCatalogActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Mint.initAndStartSession(ChannelCatalogActivity.this, "46a7ecb3");
		setContentView(R.layout.activity_channel_catalog);

		// Goi fragment channel catalog
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		ChannelCatalogFragment fragment = new ChannelCatalogFragment();
		fragmentTransaction.add(R.id.fragment_container, fragment);
		fragmentTransaction.commit();
	}
}