package com.mobile.tvpocket.activities;

import io.vov.vitamio.widget.VideoView;

import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobile.tvpocket.R;
import com.mobile.tvpocket.fragments.AdFragment;
import com.mobile.tvpocket.fragments.HomeFragment;
import com.mobile.tvpocket.fragments.ProgramsFragment;
import com.mobile.tvpocket.models.MyFavourite;
import com.mobile.tvpocket.models.MyReminder;
import com.mobile.tvpocket.services.ChannelService;
import com.mobile.tvpocket.utils.FileUtility;
import com.mobile.tvpocket.utils.GlobalConstants;
import com.mobile.tvpocket.utils.MediaUtil;
import com.splunk.mint.Mint;

public class ProgramActionsActivity extends FragmentActivity {
	private String sChannel;
	private int iChannelid;
	private String sProgramTime;
	private String sProgramTitle;
	private String sUrlWatchOnline;
	private VideoView mVideoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Mint.initAndStartSession(ProgramActionsActivity.this, "46a7ecb3");
		setContentView(R.layout.activity_program_actions);
		Intent intent = getIntent();

		iChannelid = intent.getBundleExtra("BUNDLE").getInt("CHANNELID");
		sChannel = intent.getBundleExtra("BUNDLE").getString("CHANNEL");
		sProgramTime = intent.getBundleExtra("BUNDLE").getString("TIME");
		sProgramTitle = intent.getBundleExtra("BUNDLE").getString("TITLE");
		sUrlWatchOnline = intent.getBundleExtra("BUNDLE").getString("URLONLINE");
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");

		ImageView imvChannel = (ImageView) findViewById(R.id.program_action_channel_logo);

		Drawable drw = MediaUtil.GetLocalLogo(iChannelid);
		if (drw != null)
			imvChannel.setImageDrawable(drw);
		else
			imvChannel.setImageResource(R.drawable.ic_channel_catalog_blue);

		TextView txtChannel = (TextView) findViewById(R.id.program_action_txtChannel);
		txtChannel.setTypeface(font);
		txtChannel.setText(sChannel);

		TextView txtProgramTime = (TextView) findViewById(R.id.program_action_txtProgramTime);
		txtProgramTime.setTypeface(font);
		txtProgramTime.setText(sProgramTime);

		TextView txtProgramTitle = (TextView) findViewById(R.id.program_action_txtProgramTitle);
		txtProgramTitle.setTypeface(font);
		txtProgramTitle.setText(sProgramTitle);

		ImageButton btnAddReminder = (ImageButton) findViewById(R.id.program_action_btnAddReminder);
		registerForContextMenu(btnAddReminder);
		btnAddReminder.setVisibility(View.INVISIBLE);

		if (!sProgramTime.equals("")) {
			DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
			DateTime dtCheck = formatter.parseDateTime(sProgramTime);

			if (dtCheck.compareTo(DateTime.now()) < 0) {
				btnAddReminder.setVisibility(View.INVISIBLE);
			} else {
				btnAddReminder.setVisibility(View.VISIBLE);
			}
		}

		ImageButton btnWatchOnline = (ImageButton) findViewById(R.id.program_action_btnWatchOnline);

		ImageButton btnAddFavourite = (ImageButton) findViewById(R.id.program_action_btnAddFavourite);
		btnAddFavourite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyFavourite mMyFavourite = new MyFavourite();
				mMyFavourite.setChannel(sChannel);
				mMyFavourite.setId(iChannelid);
				if (GlobalConstants.ListFavourites.indexOf(mMyFavourite) > -1) {
					Toast.makeText(ProgramActionsActivity.this, getResources().getString(R.string.added_favourite),
							Toast.LENGTH_LONG).show();
					return;
				}
				GlobalConstants.ListFavourites.add(mMyFavourite);
				Gson gson = new Gson();
				String data = gson.toJson(GlobalConstants.ListFavourites);
				FileUtility.save(GlobalConstants.DataFileName.FAVOURITES.toString(), data);
				Toast.makeText(ProgramActionsActivity.this,
						String.format(getResources().getString(R.string.exist_favourite), sChannel), Toast.LENGTH_LONG)
						.show();
				GlobalConstants.HomeDetailFragment.checkCurrentProgramNext(true);
			}
		});
		this.getActionBar().setTitle(this.sChannel);
		this.getActionBar().setHomeButtonEnabled(true);
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		this.getActionBar().setBackgroundDrawable(
				new ColorDrawable(getResources().getColor(R.color.Combo77GreenUnderline)));

		// Goi fragment Home
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		AdFragment adfragmentProgramAction = new AdFragment();
		fragmentTransaction.add(R.id.program_action_adFragment, adfragmentProgramAction, "AdFragment");
		fragmentTransaction.commit();
	}

	public void startPlay(View view) {
		Intent intent = new Intent(this, VideoViewActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Bundle args = new Bundle();
		args.putString("URLTV", sUrlWatchOnline);
		if (ChannelService.GetChannelsById(iChannelid).getRefgroup() == 999) {
			args.putBoolean("ISAUDIO", true);
		} else {
			args.putBoolean("ISAUDIO", false);
		}
		intent.putExtra("BUNDLE", args);
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.program_action_btnAddReminder) {
			menu.setHeaderTitle("Options");
			String[] menuRemindItems = getResources().getStringArray(R.array.remind_option_items);
			for (int i = 0; i < menuRemindItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuRemindItems[i]);
			}
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int menuItemIndex = item.getItemId();
		MyReminder mMyReminder = new MyReminder();
		mMyReminder.setChannel(sChannel);
		mMyReminder.setChannelId(iChannelid);
		mMyReminder.setProgram(sProgramTitle);
		mMyReminder.setProgramtime(sProgramTime);
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime dt = formatter.parseDateTime(sProgramTime);
		switch (menuItemIndex) {
		case 0:
			dt = dt.minusSeconds(0);
			break;
		case 1:
			dt = dt.minusMinutes(5);

			break;
		case 2:
			dt = dt.minusMinutes(15);
			break;
		case 3:
			dt = dt.minusMinutes(30);
			break;
		case 4:
			dt = dt.minusMinutes(1);
			break;
		case 5:
			dt = dt.minusMinutes(2);
			break;
		case 6:
			dt = dt.minusMinutes(24);
			break;
		}
		mMyReminder.setRemindtime(dt.toString(formatter));
		if (GlobalConstants.ListReminders.indexOf(mMyReminder) > -1) {
			Toast.makeText(ProgramActionsActivity.this, "Chương trình này đã được thêm vào danh sách nhắc nhở.",
					Toast.LENGTH_LONG).show();
			return true;
		}
		GlobalConstants.ListReminders.add(mMyReminder);
		Gson gson = new Gson();
		String data = gson.toJson(GlobalConstants.ListReminders);
		FileUtility.save(GlobalConstants.DataFileName.REMINDERS.toString(), data);
		setReminderService(mMyReminder);
		Toast.makeText(this, "Nhắc nhở lúc: " + dt.toString(formatter), Toast.LENGTH_LONG).show();
		return true;
	}

	private void setReminderService(MyReminder mr) {
		Calendar calNow = Calendar.getInstance();
		Calendar calSet = (Calendar) calNow.clone();

		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime dt = formatter.parseDateTime(mr.getRemindtime());

		calSet.set(Calendar.DAY_OF_MONTH, dt.dayOfMonth().get());
		calSet.set(Calendar.MONTH, dt.monthOfYear().get() - 1);
		calSet.set(Calendar.YEAR, dt.year().get());

		calSet.set(Calendar.HOUR_OF_DAY, dt.hourOfDay().get());
		calSet.set(Calendar.MINUTE, dt.minuteOfHour().get());
		calSet.set(Calendar.SECOND, dt.secondOfMinute().get());
		calSet.set(Calendar.MILLISECOND, dt.millisOfSecond().get());

		setAlarm(calSet, mr);
	}

	private void setAlarm(Calendar targetCal, MyReminder mr) {
		Intent intent = new Intent(getBaseContext(), com.mobile.tvpocket.services.AlarmReceiver.class);
		intent.putExtra("PROGRAM", mr.getChannel() + " : " + mr.getProgram());
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC, targetCal.getTimeInMillis(), pendingIntent);
	}

}
