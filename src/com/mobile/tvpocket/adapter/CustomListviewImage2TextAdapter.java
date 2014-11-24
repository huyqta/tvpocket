package com.mobile.tvpocket.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.tvpocket.R;
import com.mobile.tvpocket.activities.ProgramActionsActivity;
import com.mobile.tvpocket.models.MyChannel;
import com.mobile.tvpocket.models.Program;
import com.mobile.tvpocket.services.ChannelService;
import com.mobile.tvpocket.utils.GlobalConstants.CustomLayout;
import com.mobile.tvpocket.utils.MediaUtil;

public class CustomListviewImage2TextAdapter extends BaseAdapter {

	Context context;
	List<String[]> list_text = new ArrayList<String[]>();
	List<Integer> list_image_id = new ArrayList<Integer>();
	String channel;
	int channelid;
	CustomLayout layout;
	List<Program> listPrograms;
	int count;

	public CustomListviewImage2TextAdapter(Context _context, List<String[]> _list_text, List<Integer> _list_image_id,
			String _channel, CustomLayout _layout) {
		this.context = _context;
		this.list_text = _list_text;
		this.list_image_id = _list_image_id;
		this.channel = _channel;
		this.layout = _layout;
		this.count = _list_text.size();
	}

	public CustomListviewImage2TextAdapter(Context _context, List<Program> _listPrograms, String _channel,
			CustomLayout _layout, int _channelid) {
		this.context = _context;
		this.listPrograms = _listPrograms;
		this.channel = _channel;
		this.layout = _layout;
		this.count = _listPrograms.size();
		this.channelid = _channelid;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.count;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		final Program prg;

		ImageView imageview;
		TextView textview1;
		TextView textview2;
		Drawable drw;

		switch (this.layout) {
		case PROGRAMS:
			prg = this.listPrograms.get(position);

			convertView = mInflater.inflate(R.layout.fragment_programs_items, null);
			imageview = (ImageView) convertView.findViewById(R.id.program_poster);
			textview1 = (TextView) convertView.findViewById(R.id.time_on_air);
			textview2 = (TextView) convertView.findViewById(R.id.program_title);

			// imageview.setImageResource(R.drawable.ic_launcher);
			int groupid = ChannelService.GetChannelsById(this.listPrograms.get(position).getRefchannel()).getRefgroup();
			imageview.setImageResource(MediaUtil.GetProgramLogo(groupid));
			imageview.getLayoutParams().height = 40;
			imageview.getLayoutParams().width = 40;

			textview1.setText(this.listPrograms.get(position).getTimestart());
			textview2.setText(this.listPrograms.get(position).getName().trim());

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, ProgramActionsActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Bundle args = new Bundle();
					MyChannel mcn = new MyChannel();
					mcn = ChannelService.GetChannelsById(prg.getRefchannel());
					args.putInt("CHANNELID", channelid);
					args.putString("CHANNEL", channel);
					args.putString("TIME", prg.getDatestart() + " " + prg.getTimestart());
					args.putString("TITLE", prg.getName());
					args.putString("URLONLINE", mcn.getUrllogo());
					args.putInt("POSTER", R.drawable.ic_launcher);
					intent.putExtra("BUNDLE", args);
					context.startActivity(intent);
				}
			});
			break;
		case SEARCH_CHANNEL:
		case PROGRAMONAIR:
			prg = this.listPrograms.get(position);

			convertView = mInflater.inflate(R.layout.fragment_programs_items, null);
			imageview = (ImageView) convertView.findViewById(R.id.program_poster);
			imageview.getLayoutParams().height = 80;
			imageview.getLayoutParams().width = 150;

			textview1 = (TextView) convertView.findViewById(R.id.time_on_air);
			textview2 = (TextView) convertView.findViewById(R.id.program_title);

			drw = MediaUtil.GetLocalLogo(this.listPrograms.get(position).getRefchannel());
			if (drw != null)
				imageview.setImageDrawable(drw);
			else
				imageview.setImageResource(R.drawable.ic_channel_catalog_blue);
			textview1.setText(this.listPrograms.get(position).getTimestart());
			textview2.setText(this.listPrograms.get(position).getName().trim());

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, ProgramActionsActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Bundle args = new Bundle();
					MyChannel mcn = new MyChannel();
					mcn = ChannelService.GetChannelsById(prg.getRefchannel());
					args.putInt("CHANNELID", mcn.getId());
					args.putString("CHANNEL", mcn.getName());
					args.putString("URLONLINE", mcn.getUrllogo());
					args.putString("TIME", prg.getDatestart() + " " + prg.getTimestart());
					args.putString("TITLE", prg.getName());
					intent.putExtra("BUNDLE", args);
					context.startActivity(intent);
				}
			});

			break;
		case REMINDERS:
		case FAVOURITES:
			convertView = mInflater.inflate(R.layout.fragment_programs_items, null);

			imageview = (ImageView) convertView.findViewById(R.id.program_poster);
			imageview.getLayoutParams().height = 80;
			imageview.getLayoutParams().width = 150;

			textview1 = (TextView) convertView.findViewById(R.id.time_on_air);
			textview2 = (TextView) convertView.findViewById(R.id.program_title);
			drw = MediaUtil.GetLocalLogo(this.list_image_id.get(position));
			if (drw != null)
				imageview.setImageDrawable(drw);
			else {
				if (this.layout == CustomLayout.REMINDERS)
					imageview.setImageResource(R.drawable.ic_reminder_blue);
				else
					imageview.setImageResource(R.drawable.ic_favourite_blue);
			}
			textview1.setText(this.list_text.get(position)[0].trim());
			textview2.setText(this.list_text.get(position)[1].trim());
			break;
		default:
			break;
		}

		return convertView;
	}
}
