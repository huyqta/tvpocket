package com.mobile.tvpocket.fragments;

import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobile.tvpocket.R;
import com.mobile.tvpocket.activities.ProgramActionsActivity;
import com.mobile.tvpocket.activities.StreamAudioActivity;
import com.mobile.tvpocket.activities.VideoViewActivity;
import com.mobile.tvpocket.adapter.CustomListviewImage2TextAdapter;
import com.mobile.tvpocket.models.MyChannel;
import com.mobile.tvpocket.models.MyFavourite;
import com.mobile.tvpocket.models.Program;
import com.mobile.tvpocket.services.ChannelService;
import com.mobile.tvpocket.services.ProgramService;
import com.mobile.tvpocket.utils.FileUtility;
import com.mobile.tvpocket.utils.GlobalConstants;
import com.mobile.tvpocket.utils.GlobalConstants.CustomLayout;

public class ProgramsFragment extends Fragment {

	View rootView;
	List<String[]> _list_text;
	String[] homedetail;
	List<Integer> _list_image_id;
	String channel;
	int channelid;
	String urlcrawl;
	String urlapi;
	String urltv;
	Calendar calendar = Calendar.getInstance();
	int cyear;
	int cmonth;
	int cday;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.fragment_programs, container, false);

		Bundle args = getArguments();
		this.channelid = args.getInt("CHANNELID");
		this.channel = args.getString("CHANNEL");
		this.urlcrawl = args.getString("URLCRAWL");
		this.urlapi = args.getString("URLAPI");
		this.urltv = args.getString("URLTV");

		cyear = calendar.get(Calendar.YEAR);
		cmonth = calendar.get(Calendar.MONTH);
		cday = calendar.get(Calendar.DAY_OF_MONTH);

		String currdatetime = new StringBuilder().append(cday).append("/").append(cmonth + 1).append("/").append(cyear)
				.toString();
		ProgramService ps = new ProgramService();
		if (this.urlapi != null) {
			ps.GetProgramFromUrlApi(this.channelid, this.urlapi, DateTime.now().toString("dd/MM/yyyy"), this);
		}
		// Lay ngay hien tai lam mac dinh
		TextView txtProgramDate = (TextView) rootView.findViewById(R.id.textview_date);
		txtProgramDate.setText(currdatetime);

		// Set up actionbar
		// this.getActivity().getActionBar().setHomeButtonEnabled(true);
		// this.getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		// Setup option menus
		this.setHasOptionsMenu(true);

		this.getActivity().getActionBar().setHomeButtonEnabled(true);
		this.getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		this.getActivity().getActionBar()
				.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Combo77GreenUnderline)));

		return rootView;
	}

	public void loadProgramsByChannel(List<Program> listPrograms) {
		ListView listview_detail = (ListView) rootView.findViewById(R.id.listview_fragment_programs);
		TextView textview_message = (TextView) rootView.findViewById(R.id.textview_message);

		if (listPrograms == null) {
			listview_detail.setEmptyView(textview_message);
			textview_message.setText(getResources().getString(R.string.no_program));
		} else {
			CustomListviewImage2TextAdapter mCustomListviewImage2TextAdapter = new CustomListviewImage2TextAdapter(
					rootView.getContext(), listPrograms, channel, CustomLayout.PROGRAMS, channelid);
			listview_detail.setAdapter(mCustomListviewImage2TextAdapter);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.program, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.program_calendar:
			DatePickerDialog dialog = new DatePickerDialog(this.getActivity(), new DateSetListener(this), cyear,
					cmonth, cday);
			dialog.show();
			return true;
		case android.R.id.home:
			this.getActivity().finish();
			return true;
		case R.id.watch_online:
			// Intent intent = new Intent(this.getActivity(), VideoViewActivity.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// Bundle args = new Bundle();
			// args.putString("URLTV", this.urltv);
			if (ChannelService.GetChannelsById(channelid).getRefgroup() == 999) {
				Intent intent = new Intent(this.getActivity(), StreamAudioActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Bundle args = new Bundle();
				args.putString("URLTV", this.urltv);				
				intent.putExtra("BUNDLE", args);
				startActivity(intent);

			} else {

				Intent intent = new Intent(this.getActivity(), VideoViewActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Bundle args = new Bundle();
				args.putString("URLTV", this.urltv);				
				intent.putExtra("BUNDLE", args);
				startActivity(intent);
			}

			return true;
		case R.id.add_favourite:
			MyFavourite mMyFavourite = new MyFavourite();
			mMyFavourite.setChannel(this.channel);
			mMyFavourite.setId(this.channelid);
			if (GlobalConstants.ListFavourites.indexOf(mMyFavourite) > -1) {
				Toast.makeText(ProgramsFragment.this.getActivity(), getResources().getString(R.string.added_favourite),
						Toast.LENGTH_LONG).show();
				return true;
			}
			GlobalConstants.ListFavourites.add(mMyFavourite);
			Gson gson = new Gson();
			String data = gson.toJson(GlobalConstants.ListFavourites);
			FileUtility.save(GlobalConstants.DataFileName.FAVOURITES.toString(), data);
			Toast.makeText(ProgramsFragment.this.getActivity(),
					String.format(getResources().getString(R.string.exist_favourite), this.channel), Toast.LENGTH_LONG)
					.show();
			GlobalConstants.HomeDetailFragment.checkCurrentProgramNext(true);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	class DateSetListener implements DatePickerDialog.OnDateSetListener {
		ProgramsFragment pf;

		public DateSetListener(Fragment fragment) {
			pf = (ProgramsFragment) fragment;
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			// TODO Auto-generated method stub
			// getCalender();
			int mYear = year;
			int mMonth = monthOfYear;
			int mDay = dayOfMonth;
			TextView txtProgramDate = (TextView) rootView.findViewById(R.id.textview_date);
			String dtSelected = new StringBuilder().append(mDay).append("/").append(mMonth + 1).append("/")
					.append(mYear).toString();
			txtProgramDate.setText(dtSelected);

			ProgramService ps = new ProgramService();
			ps.GetProgramFromUrlApi(channelid, urlapi, dtSelected, this.pf);
		}
	}
}
