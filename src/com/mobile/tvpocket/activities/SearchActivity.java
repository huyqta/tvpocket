package com.mobile.tvpocket.activities;

import java.util.List;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mobile.tvpocket.R;
import com.mobile.tvpocket.adapter.CustomListviewImage2TextAdapter;
import com.mobile.tvpocket.adapter.CustomListviewImageTextAdapter;
import com.mobile.tvpocket.models.MyChannel;
import com.mobile.tvpocket.models.Program;
import com.mobile.tvpocket.services.ChannelService;
import com.mobile.tvpocket.services.ProgramService;
import com.mobile.tvpocket.utils.GlobalConstants.CustomLayout;
import com.splunk.mint.Mint;

public class SearchActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Mint.initAndStartSession(SearchActivity.this, "46a7ecb3");
		setContentView(R.layout.activity_search);

		final ListView list_result = (ListView) findViewById(R.id.listview_fragment_search);
		final RadioButton rdChannel = (RadioButton) findViewById(R.id.radioChannel);
		final RadioButton rdProgram = (RadioButton) findViewById(R.id.radioProgram);
		final EditText search_input = (EditText) findViewById(R.id.search_input);

		search_input.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (search_input.getText().length() > 2) {
					if (rdChannel.isChecked()) {
						List<MyChannel> resChannels = ChannelService.FilterChannelsByName(search_input.getText()
								.toString());
						loadChannels(resChannels);
					}
				}
				if (search_input.getText().equals("")) {
					list_result.setAdapter(null);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		final ImageButton imgButton = (ImageButton) findViewById(R.id.search_button);
		imgButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (rdChannel.isChecked()) {
					List<MyChannel> resChannels = ChannelService
							.FilterChannelsByName(search_input.getText().toString());
					loadChannels(resChannels);
				}
				if (rdProgram.isChecked()) {
					ProgramService ps = new ProgramService();
					ps.SearchPrograms(SearchActivity.this, search_input.getText().toString());
				}
			}
		});

		this.getActionBar().setHomeButtonEnabled(true);
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		this.getActionBar().setBackgroundDrawable(
				new ColorDrawable(getResources().getColor(R.color.Combo77GreenUnderline)));
	}

	public void loadChannels(List<MyChannel> listChannels) {
		ListView listview_detail = (ListView) this.findViewById(R.id.listview_fragment_search);
		TextView textview_message = (TextView) this.findViewById(R.id.textview_message);

		if (listChannels == null) {
			listview_detail.setEmptyView(textview_message);
			textview_message.setText(getResources().getString(R.string.no_program));
		} else {
			CustomListviewImageTextAdapter mCustomListviewImageTextAdapter = new CustomListviewImageTextAdapter(this,
					listChannels, CustomLayout.SEARCH_CHANNEL, listChannels.size());
			listview_detail.setAdapter(mCustomListviewImageTextAdapter);
		}
	}

	public void loadPrograms(List<Program> listPrograms) {
		ListView listview_detail = (ListView) this.findViewById(R.id.listview_fragment_search);
		TextView textview_message = (TextView) this.findViewById(R.id.textview_message);

		if (listPrograms == null) {
			listview_detail.setEmptyView(textview_message);
			textview_message.setText(getResources().getString(R.string.no_program));
		} else {
			CustomListviewImage2TextAdapter mCustomListviewImage2TextAdapter = new CustomListviewImage2TextAdapter(
					this.getBaseContext(), listPrograms, "", CustomLayout.SEARCH_CHANNEL, -1);
			listview_detail.setAdapter(mCustomListviewImage2TextAdapter);
		}
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
