package com.mobile.tvpocket.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.tvpocket.R;
import com.mobile.tvpocket.models.Program;
import com.mobile.tvpocket.utils.GlobalConstants;
import com.mobile.tvpocket.utils.MediaUtil;

public class CustomListviewImage3TextAdapter extends BaseAdapter {

	Context context;
	List<String[]> list_text = new ArrayList<String[]>();
	List<Integer> list_image_id = new ArrayList<Integer>();
	List<Program> listPrograms = new ArrayList<Program>();
	String tabname;
	int count;
	Drawable drw;

	public CustomListviewImage3TextAdapter(Context _context, List<String[]> _list_text, List<Integer> _list_image_id,
			int _count) {
		this.context = _context;
		this.list_text = _list_text;
		this.list_image_id = _list_image_id;
		this.count = _count;
	}

	public CustomListviewImage3TextAdapter(Context _context, List<Program> _listPrograms, String _tabname, int _count) {
		this.context = _context;
		this.listPrograms = _listPrograms;
		this.tabname = _tabname;
		this.count = _count;
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
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		if (this.tabname == "NEXTPROGRAM") {
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.fragment_program_whaton, null);
		}
		ImageView imageview = (ImageView) convertView.findViewById(R.id.channel_logo);
		imageview.getLayoutParams().height = 80;
		imageview.getLayoutParams().width = 150;

		TextView textview1 = (TextView) convertView.findViewById(R.id.next_program_1);
		TextView textview2 = (TextView) convertView.findViewById(R.id.next_program_2);
		TextView textview3 = (TextView) convertView.findViewById(R.id.next_program_3);

		int channelPosition = position * GlobalConstants.RangeNextPrograms;

		drw = MediaUtil.GetLocalLogo(this.listPrograms.get(channelPosition).getRefchannel());
		if (drw != null)
			imageview.setImageDrawable(drw);
		else
			imageview.setImageResource(R.drawable.ic_channel_catalog_blue);

		if (this.listPrograms.size() > position * GlobalConstants.RangeNextPrograms + 0) {
			textview1.setText(this.listPrograms.get(position * GlobalConstants.RangeNextPrograms + 0).getTimestart()
					+ " " + this.listPrograms.get(position * GlobalConstants.RangeNextPrograms + 0).getName());
		}
		if (this.listPrograms.size() > position * GlobalConstants.RangeNextPrograms + 1) {
			textview2.setText(this.listPrograms.get(position * GlobalConstants.RangeNextPrograms + 1).getTimestart()
					+ " " + this.listPrograms.get(position * GlobalConstants.RangeNextPrograms + 1).getName());
		}
		if (this.listPrograms.size() > position * GlobalConstants.RangeNextPrograms + 2) {
			textview3.setText(this.listPrograms.get(position * GlobalConstants.RangeNextPrograms + 2).getTimestart()
					+ " " + this.listPrograms.get(position * GlobalConstants.RangeNextPrograms + 2).getName());
		}

		return convertView;
	}

}
