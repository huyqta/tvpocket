package com.mobile.tvpocket.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
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
import com.mobile.tvpocket.fragments.AdFragment;
import com.mobile.tvpocket.models.MyFavourite;
import com.mobile.tvpocket.services.GroupService;
import com.mobile.tvpocket.utils.FileUtility;
import com.mobile.tvpocket.utils.GlobalConstants;
import com.mobile.tvpocket.utils.GlobalConstants.CustomLayout;
import com.splunk.mint.Mint;

public class FavouriteActivity extends FragmentActivity {

	List<MyFavourite> lstFavourite;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Mint.initAndStartSession(FavouriteActivity.this, "46a7ecb3");
		setContentView(R.layout.activity_favourite);
		loadAllFavourites();
		registerForContextMenu(findViewById(R.id.favourite_listview_reminds));

		this.getActionBar().setHomeButtonEnabled(true);
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		this.getActionBar().setBackgroundDrawable(
				new ColorDrawable(getResources().getColor(R.color.Combo77GreenUnderline)));

		// Goi fragment Home
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		AdFragment adfragmentProgramAction = new AdFragment();
		fragmentTransaction.add(R.id.favourite_adFragment, adfragmentProgramAction, "AdFragment");
		fragmentTransaction.commit();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.favourite_listview_reminds) {
			// AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle("Options");
			menu.add(getResources().getString(R.string.remove));
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		GlobalConstants.ListFavourites.remove(menuInfo.position);
		Gson gson = new Gson();
		String data = gson.toJson(GlobalConstants.ListFavourites);
		FileUtility.save(GlobalConstants.DataFileName.FAVOURITES.toString(), data);
		Toast.makeText(FavouriteActivity.this, getResources().getString(R.string.remove_fav_channel), Toast.LENGTH_LONG)
				.show();
		loadAllFavourites();
		GlobalConstants.HomeDetailFragment.checkCurrentProgramNext(true);
		return true;
	}

	private void loadAllFavourites() {

		List<String[]> _list_text = new ArrayList<String[]>();
		List<Integer> _list_image_id = new ArrayList<Integer>();

		for (MyFavourite fav : GlobalConstants.ListFavourites) {
			_list_text.add(new String[] { fav.getChannel(), GroupService.GetGroupByChannelId(fav.getId()).getName() });
			_list_image_id.add(fav.getId());
		}

		CustomListviewImage2TextAdapter mCustomListviewImage2TextAdapter = new CustomListviewImage2TextAdapter(this,
				_list_text, _list_image_id, "", CustomLayout.FAVOURITES);
		ListView listviewReminds = (ListView) findViewById(R.id.favourite_listview_reminds);
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
