package com.mobile.tvpocket.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobile.tvpocket.R;
import com.mobile.tvpocket.adapter.CustomListviewImage2TextAdapter;
import com.mobile.tvpocket.models.MyReminder;
import com.mobile.tvpocket.utils.FileUtility;
import com.mobile.tvpocket.utils.GlobalConstants;
import com.mobile.tvpocket.utils.GlobalConstants.CustomLayout;
import com.splunk.mint.Mint;

public class ReminderActivity extends Activity {

	List<MyReminder> lstReminder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Mint.initAndStartSession(ReminderActivity.this, "46a7ecb3");
		setContentView(R.layout.activity_reminder);
		loadAllReminders();
		registerForContextMenu(findViewById(R.id.listview_reminds));

		this.getActionBar().setHomeButtonEnabled(true);
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		this.getActionBar().setBackgroundDrawable(
				new ColorDrawable(getResources().getColor(R.color.Combo77GreenUnderline)));
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.listview_reminds) {
			menu.setHeaderTitle(getResources().getString(R.string.header_option));
			menu.add(getResources().getString(R.string.remove));
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		GlobalConstants.ListReminders.remove(menuInfo.position);
		Gson gson = new Gson();
		String data = gson.toJson(GlobalConstants.ListFavourites);

		FileUtility.save(GlobalConstants.DataFileName.FAVOURITES.toString(), data);
		Toast.makeText(ReminderActivity.this, getResources().getString(R.string.remove_reminder), Toast.LENGTH_LONG)
				.show();
		loadAllReminders();
		return true;
	}

	private void loadAllReminders() {

		List<String[]> _list_text = new ArrayList<String[]>();
		List<Integer> _list_image_id = new ArrayList<Integer>();

		for (MyReminder rem : GlobalConstants.ListReminders) {
			_list_text.add(new String[] { rem.getProgramtime(), rem.getProgram() });
			_list_image_id.add(rem.getChannelId());
		}

		CustomListviewImage2TextAdapter mCustomListviewImage2TextAdapter = new CustomListviewImage2TextAdapter(this,
				_list_text, _list_image_id, "", CustomLayout.REMINDERS);
		ListView listviewReminds = (ListView) findViewById(R.id.listview_reminds);
		listviewReminds.setAdapter(mCustomListviewImage2TextAdapter);
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
}
