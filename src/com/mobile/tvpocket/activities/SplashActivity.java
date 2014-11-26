package com.mobile.tvpocket.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.mobile.tvpocket.R;
import com.mobile.tvpocket.fragments.AdFragment;
import com.mobile.tvpocket.services.ChannelService;
import com.mobile.tvpocket.services.GroupService;
import com.mobile.tvpocket.services.ReminderService;
import com.mobile.tvpocket.utils.GlobalConstants;
import com.splunk.mint.Mint;

public class SplashActivity extends Activity {

	TextView textview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Mint.initAndStartSession(SplashActivity.this, "46a7ecb3");
		setContentView(R.layout.activity_splash);

		textview = (TextView) findViewById(R.id.loading);

		GlobalConstants.ApplicationContext = getApplicationContext();
		GlobalConstants.adfragment = new AdFragment();
		/**
		 * Showing splashscreen while making network calls to download necessary data before launching the app Will use
		 * AsyncTask to make http call
		 */

		new PrefetchData().execute();

	}

	private class PrefetchData extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			boolean isOnline = GlobalConstants.isConnectingToInternet(GlobalConstants.ApplicationContext);
			if (!isOnline) {
				textview.setText(getResources().getString(R.string.lost_connection));
				textview.setTextColor(Color.RED);
				while (!isOnline) {
					try {
						Thread.sleep(3000);
						isOnline = GlobalConstants.isConnectingToInternet(GlobalConstants.ApplicationContext);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if (isOnline) {
				ChannelService.GetAllChannelsFromWS(); // Get all channels from WS
				GroupService.GetAllGroupsFromWS(); // Get all groups from WS
			}
			ChannelService.GetAllFavouritesFromFile();
			ReminderService.GetAllRemindersFromFile();

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// After completing http call
			// will close this activity and lauch main activity
			Intent i = new Intent(SplashActivity.this, MainActivity.class);
			startActivity(i);

			// close this activity
			finish();
		}
	}
}
