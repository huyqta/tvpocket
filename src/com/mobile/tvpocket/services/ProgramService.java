package com.mobile.tvpocket.services;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import com.mobile.tvpocket.R;
import com.mobile.tvpocket.activities.SearchActivity;
import com.mobile.tvpocket.adapter.HomePagerAdapter.HomeDetailFragment;
import com.mobile.tvpocket.fragments.ProgramsFragment;
import com.mobile.tvpocket.models.Program;
import com.mobile.tvpocket.utils.ConvertUtil;
import com.mobile.tvpocket.utils.GlobalConstants;
import com.mobile.tvpocket.utils.RestUtil;

public class ProgramService {

	public void GetProgramFromUrlCrawl(String channel, String urlcrawl, String date, Fragment fragment) {
		PrefetchData predata = new PrefetchData(fragment, date);
		predata.execute(urlcrawl, date);
	}

	public void GetProgramFromUrlApi(int channelid, String urlapi, String date, Fragment fragment) {
		PrefetchData predata = new PrefetchData(fragment, date);
		DateTime dttemp = DateTime.parse(date, DateTimeFormat.forPattern("dd/MM/yyyy"));
		String url = String.format(urlapi, channelid, dttemp.toString("yyyy-MM-dd"));
		predata.execute(url, date);
	}

	public void GetProgramOnAirFromUrlApi(String date, Fragment fragment) {
		PrefetchData predata = new PrefetchData(fragment, date);
		predata.HomeTabName = "ONAIR";
		String urlapi = "http://huyqta.esy.es/index.php/api/programs/GetProgramsOnAir/%s/%s/format/json";
		String url = String.format(urlapi, DateTime.now().toString("yyyy-MM-dd"), DateTime.now().toString("HH:mm"));
		predata.execute(url);
	}

	public void GetProgramsNextFromUrlApi(String date, Fragment fragment) {
		PrefetchData predata = new PrefetchData(fragment, date);
		predata.HomeTabName = "NEXT";
		String urlapi = "http://huyqta.esy.es/index.php/api/programs/GetNextPrograms/%s/%s/%s/%s/format/json";
		String url = String.format(urlapi, DateTime.now().toString("yyyy-MM-dd"), DateTime.now().toString("HH:mm"),
				GlobalConstants.RangeNextPrograms, "0");
		predata.execute(url);
	}

	public void SearchPrograms(Activity activity, String input) {
		PrefetchData predata = new PrefetchData(activity);
		String urlapi = "http://huyqta.esy.es/index.php/api/programs/SearchPrograms/%s/0/format/json";
		String url = String.format(urlapi, input.replace(" ", "huyqtahuyqta"));
		predata.execute(url);
	}

	private class PrefetchData extends AsyncTask<String, Void, String> {

		Context context;

		Fragment fragment;
		Activity activity;
		String HomeTabName;
		ProgressDialog progressDialog;

		public PrefetchData(Fragment fragment, String date) {
			this.fragment = fragment;
			this.context = fragment.getActivity();
		}

		public PrefetchData(Activity activity) {
			this.activity = activity;
			this.context = activity;
		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			if (this.fragment != null) {
				this.context = this.fragment.getActivity();
			} else {
				this.context = activity;
			}
			progressDialog = ProgressDialog.show(context, null, GlobalConstants.ApplicationContext.getResources()
					.getString(R.string.on_processing), true, false);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String prgjson = "";
			try {
				// Temp code, we can remove it
				// String url = params[0].replace("%", this.dtGetData.toString("dd/MM/yyyy"));
				String url = params[0];
				String ret = RestUtil.get(url);
				// prgjson = gson.fromJson(ret, String.class);
				prgjson = ret;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return prgjson;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
			List<Program> ListPrograms = new ArrayList<Program>();
			if (!result.equals("")) {
				ListPrograms = ConvertUtil.ConvertFromAPIHost(result);
			}
			if (this.fragment != null) {
				if (this.fragment.getClass().equals(ProgramsFragment.class)) {
					ProgramsFragment mProgramsFragment = (ProgramsFragment) this.fragment;
					mProgramsFragment.loadProgramsByChannel(ListPrograms);

				}
				if (this.fragment.getClass().equals(HomeDetailFragment.class)) {
					HomeDetailFragment mHomeDetailFragment = (HomeDetailFragment) this.fragment;
					if (this.HomeTabName.equals("ONAIR")) {
						mHomeDetailFragment.loadProgramsOnAir(ListPrograms);
					}
					if (this.HomeTabName.equals("NEXT")) {
						mHomeDetailFragment.loadNextPrograms(ListPrograms);
					}
				}
			}
			if (this.activity != null) {
				if (this.activity.getClass().equals(SearchActivity.class)) {
					SearchActivity mSearchActivity = (SearchActivity) this.activity;
					mSearchActivity.loadPrograms(ListPrograms);
				}
			}

		}
	}
}
