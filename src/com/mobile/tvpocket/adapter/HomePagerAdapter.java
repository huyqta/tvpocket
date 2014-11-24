package com.mobile.tvpocket.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobile.tvpocket.R;
import com.mobile.tvpocket.models.MyFavourite;
import com.mobile.tvpocket.models.Program;
import com.mobile.tvpocket.services.ProgramService;
import com.mobile.tvpocket.utils.GlobalConstants;
import com.mobile.tvpocket.utils.GlobalConstants.CustomLayout;

public class HomePagerAdapter extends FragmentPagerAdapter {

	// String[] listTabs = { "Đang chiếu", "Sắp chiếu" };
	ProgressBar pProgressBar;
	List<HomeDetailFragment> listHDF;

	public HomePagerAdapter(FragmentManager fragmentManager, List<HomeDetailFragment> listHDF) {
		super(fragmentManager);
		// TODO Auto-generated constructor stub
		this.listHDF = listHDF;
	}

	@Override
	public Fragment getItem(int position) {
		return this.listHDF.get(position);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return this.listHDF.get(position).Tabname;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.listHDF.size();
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}

	// //////////////////////////////////////
	// ----- ChannelCatalogFragment ------//
	// //////////////////////////////////////
	public static class HomeDetailFragment extends Fragment {

		// String[] listTabs = { "Đang chiếu", "Sắp chiếu" };
		public String Tabname;
		public View rootView;
		public ViewGroup container;
		private Handler counterHandler = new Handler();

		public HomeDetailFragment(String name) {
			this.Tabname = name;
		}

		public HomeDetailFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
				@Nullable Bundle savedInstanceState) {
			this.rootView = inflater.inflate(R.layout.fragment_home_detail, container, false);

			GlobalConstants.HomeDetailFragment = this;

			ProgramService ps = new ProgramService();
			// for (String tab : listTabs) {
			String datestring = DateTime.now().toString("dd/MM/yyyy");
			if (this.Tabname == getResources().getString(R.string.on_air)) {
				ps.GetProgramOnAirFromUrlApi(datestring, this);
			}
			if (this.Tabname == getResources().getString(R.string.on_next)) {
				ps.GetProgramsNextFromUrlApi(datestring, this);
			}

			counterHandler.postDelayed(CheckHomeFragment, 180000);

			this.container = container;
			return rootView;
		}

		public void loadProgramsOnAir(List<Program> listPrograms) {
			CustomListviewImage2TextAdapter mCustomListviewImage2TextAdapter;
			List<Program> listProgramTemps = new ArrayList<Program>();

			String favchannelsString = "|";
			for (MyFavourite fav : GlobalConstants.ListFavourites) {
				favchannelsString += fav.getId() + "|";
			}

			for (Program prg : listPrograms) {
				if (favchannelsString.indexOf("|" + prg.getRefchannel() + "|") > -1) {
					listProgramTemps.add(prg);
				}
			}
			GlobalConstants.ListProgramsOnAir = listProgramTemps;
			ListView listview_detail = (ListView) this.container.findViewById(R.id.listview_fragment_home_detail);
			TextView textview_message = (TextView) this.container.findViewById(R.id.textview_message);

			if (GlobalConstants.ListProgramsOnAir.size() == 0) {
				listview_detail.setEmptyView(textview_message);
				textview_message.setText(getResources().getString(R.string.please_add_favourite));
			} else {
				mCustomListviewImage2TextAdapter = new CustomListviewImage2TextAdapter(this.container.getContext(),
						GlobalConstants.ListProgramsOnAir, "", CustomLayout.PROGRAMONAIR, -1);
				mCustomListviewImage2TextAdapter.notifyDataSetChanged();
				listview_detail.setAdapter(mCustomListviewImage2TextAdapter);
			}

		}

		public void loadNextPrograms(List<Program> listPrograms) {
			String favchannelsString = "|";
			List<Program> listProgramTemps = new ArrayList<Program>();

			for (MyFavourite fav : GlobalConstants.ListFavourites) {
				favchannelsString += fav.getId() + "|";
			}

			for (Program prg : listPrograms) {
				if (favchannelsString.indexOf("|" + prg.getRefchannel() + "|") > -1) {
					listProgramTemps.add(prg);
				}
			}
			GlobalConstants.ListProgramsNext = listProgramTemps;
			ListView listview_detail = (ListView) this.rootView.findViewById(R.id.listview_fragment_home_detail);
			TextView textview_message = (TextView) this.rootView.findViewById(R.id.textview_message);

			if (GlobalConstants.ListProgramsNext.size() == 0) {
				listview_detail.setEmptyView(textview_message);
				textview_message.setText(getResources().getString(R.string.please_add_favourite));
			} else {
				CustomListviewImage3TextAdapter mCustomListviewImage3TextAdapter = new CustomListviewImage3TextAdapter(
						this.container.getContext(), GlobalConstants.ListProgramsNext, "",
						GetChannelInListPrograms(GlobalConstants.ListProgramsNext));
				mCustomListviewImage3TextAdapter.tabname = "NEXTPROGRAM";
				listview_detail.setAdapter(null);
				mCustomListviewImage3TextAdapter.notifyDataSetChanged();
				listview_detail.setAdapter(mCustomListviewImage3TextAdapter);
			}
		}

		private Runnable CheckHomeFragment = new Runnable() {
			public void run() {
				// checkCurrentProgramOnAir();
				checkCurrentProgramNext(false);
				counterHandler.postDelayed(CheckHomeFragment, 15000);
			}
		};

		public void checkCurrentProgramNext(boolean fromFavourite) {
			try {
				if (GlobalConstants.ListProgramsNext.size() == 0)
					return;
				List<Program> temp = GlobalConstants.ListProgramsNext;
				Collections.sort(temp);
				DateTime dtnow = DateTime.now();
				DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
				DateTime dtcheck = DateTime.parse(temp.get(0).getDatestart() + " " + temp.get(0).getTimestart(),
						formatter);
				if (dtnow.compareTo(dtcheck) > 0 || fromFavourite) {
					ProgramService ps = new ProgramService();
					ps.GetProgramsNextFromUrlApi(DateTime.now().toString("dd/MM/yyyy"), this);
					ps.GetProgramOnAirFromUrlApi(DateTime.now().toString("dd/MM/yyyy"), this);
				}
			} catch (Exception ex) {
				// Log.e(ex.getStackTrace().toString(), ex.getMessage());
			}
		}

		private int GetChannelInListPrograms(List<Program> listPrograms) {
			int i = 0;
			int currentChannel = -1;
			for (Program prg : listPrograms) {
				if (prg.getRefchannel() != currentChannel) {
					i++;
					currentChannel = prg.getRefchannel();
				}
			}
			return i;
		}

		public static List<Program> GetFromServices(List<Program> listPrograms) {
			return listPrograms;
		}
	}
}
