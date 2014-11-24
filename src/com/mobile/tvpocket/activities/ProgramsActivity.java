package com.mobile.tvpocket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.mobile.tvpocket.R;
import com.mobile.tvpocket.fragments.ProgramsFragment;
import com.splunk.mint.Mint;

public class ProgramsActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Mint.initAndStartSession(ProgramsActivity.this, "46a7ecb3");
		setContentView(R.layout.activity_channel_catalog);

		// Nhan channel tu activity program
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("BUNDLE");

		// Goi fragment programs
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		ProgramsFragment fragment = new ProgramsFragment();

		fragment.setArguments(bundle);
		fragmentTransaction.add(R.id.fragment_container, fragment);
		fragmentTransaction.commit();
	}
}
