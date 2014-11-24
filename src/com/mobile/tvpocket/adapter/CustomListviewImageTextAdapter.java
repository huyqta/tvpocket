package com.mobile.tvpocket.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.mobile.tvpocket.R;
import com.mobile.tvpocket.activities.ChannelCatalogActivity;
import com.mobile.tvpocket.activities.FavouriteActivity;
import com.mobile.tvpocket.activities.IntroductionActivity;
import com.mobile.tvpocket.activities.ProgramsActivity;
import com.mobile.tvpocket.activities.ReminderActivity;
import com.mobile.tvpocket.activities.SearchActivity;
import com.mobile.tvpocket.models.MyChannel;
import com.mobile.tvpocket.utils.GlobalConstants;
import com.mobile.tvpocket.utils.GlobalConstants.CustomLayout;

public class CustomListviewImageTextAdapter extends BaseAdapter {

	Context context;
	List<String> list_text = new ArrayList<String>();
	List<Integer> list_image_id = new ArrayList<Integer>();
	CustomLayout layout;
	FragmentManager fragmentManager;
	List<MyChannel> listMyChannels;
	int count;

	public void setFragmentManager(FragmentManager _fragmentManager) {
		this.fragmentManager = _fragmentManager;
	}

	public CustomListviewImageTextAdapter(Context _context, List<String> _list_text, List<Integer> _list_image_id,
			CustomLayout _layout, int _count) {
		this.context = _context;
		this.list_text = _list_text;
		this.list_image_id = _list_image_id;
		this.layout = _layout;
		this.count = _count;
	}

	public CustomListviewImageTextAdapter(Context _context, List<MyChannel> _listMyChannels, CustomLayout _layout,
			int _count) {
		this.context = _context;
		this.listMyChannels = _listMyChannels;
		this.layout = _layout;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (mInflater == null)
			return null;

		Typeface fontRobotoRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");

		ImageView imageview = null;
		TextView textview = null;

		switch (this.layout) {
		case APPLICATION_MENU:
			convertView = mInflater.inflate(R.layout.fragment_application_menu, null);

			imageview = (ImageView) convertView.findViewById(R.id.application_menu_icon);
			textview = (TextView) convertView.findViewById(R.id.application_menu);

			imageview.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					loadFragment(position);
				}
			});

			textview.setTypeface(fontRobotoRegular);
			textview.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					loadFragment(position);
				}
			});
			if (imageview != null)
				imageview.setImageResource(this.list_image_id.get(position));
			if (textview != null)
				textview.setText(this.list_text.get(position));
			break;
		case CHANNEL_CATALOG_ITEM:
			convertView = mInflater.inflate(R.layout.fragment_channel_catalog_item, null);
			if (this.layout == CustomLayout.SEARCH_CHANNEL) {
				LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.progam_item_layout);
				layout.setOrientation(LinearLayout.HORIZONTAL);
			}
			imageview = (ImageView) convertView.findViewById(R.id.channel_catalog_item_icon);
			textview = (TextView) convertView.findViewById(R.id.channel_catalog_item_text);
			textview.setTypeface(fontRobotoRegular);
			imageview.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					loadFragment(listMyChannels.get(position));
				}
			});

			textview.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					loadFragment(listMyChannels.get(position));
				}
			});

			if (imageview != null && this.listMyChannels.get(position).getLocalLogo() != null) {
				imageview.setImageDrawable(this.listMyChannels.get(position).getLocalLogo());
			} else {
				imageview.setImageResource(R.drawable.ic_channel_catalog_blue);
			}

			textview.setText(this.listMyChannels.get(position).getName());
			if (this.listMyChannels.get(position).getUrllogo() != null
					&& !this.listMyChannels.get(position).getUrllogo().equals("")) {
				textview.setTextColor(convertView.getResources().getColor(R.color.Maroon));
			}
			textview.getLayoutParams().height = 70;

			break;
		case SEARCH_CHANNEL:
			convertView = mInflater.inflate(R.layout.fragment_channel_catalog_item, null);
			LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.progam_item_layout);
			layout.setOrientation(LinearLayout.HORIZONTAL);

			imageview = (ImageView) convertView.findViewById(R.id.channel_catalog_item_icon);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, 80);
			params.gravity = Gravity.CENTER;
			params.leftMargin = 10;
			imageview.setLayoutParams(params);

			textview = (TextView) convertView.findViewById(R.id.channel_catalog_item_text);
			LinearLayout.LayoutParams paramstv = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			paramstv.gravity = Gravity.LEFT;
			paramstv.leftMargin = 10;
			paramstv.topMargin = 5;
			textview.setLayoutParams(paramstv);
			textview.setTypeface(fontRobotoRegular);
			textview.setTextSize(25);
			imageview.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					loadFragment(listMyChannels.get(position));
				}
			});

			textview.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					loadFragment(listMyChannels.get(position));
				}
			});

			if (imageview != null && this.listMyChannels.get(position).getLocalLogo() != null) {
				imageview.setImageDrawable(this.listMyChannels.get(position).getLocalLogo());
			} else {
				imageview.setImageResource(R.drawable.ic_launcher);
			}

			if (textview != null) {
				textview.setText(this.listMyChannels.get(position).getName());
			}
			break;
		default:
			break;

		}

		return convertView;
	}

	private void loadFragment(Object obj) {
		try {
			Intent intent = null;
			switch (this.layout) {
			case APPLICATION_MENU:
				int position = Integer.parseInt(obj.toString());
				GlobalConstants.HomeActivityDrawerLayout.closeDrawers();
				switch (position) {
				case 0:
					intent = new Intent(this.context, ChannelCatalogActivity.class);
					break;
				case 1:
					intent = new Intent(this.context, ReminderActivity.class);
					break;
				case 2:
					intent = new Intent(this.context, FavouriteActivity.class);
					break;
				case 3:
					intent = new Intent(this.context, IntroductionActivity.class);
					break;
				case 4:
					intent = new Intent(this.context, SearchActivity.class);
					break;

				default:
					break;
				}

				break;
			case SEARCH_CHANNEL:
			case CHANNEL_CATALOG_ITEM:
				intent = new Intent(this.context, ProgramsActivity.class);
				Bundle args = new Bundle();
				MyChannel channel = (MyChannel) obj;
				args.putInt("CHANNELID", channel.getId());
				args.putString("CHANNEL", channel.getName());
				args.putString("URLCRAWL", channel.getUrlcrawl());
				args.putString("URLAPI", channel.getUrlapi());
				args.putString("URLTV", channel.getUrllogo());
				intent.putExtra("BUNDLE", args);
				break;
			default:
				break;
			}
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			this.context.startActivity(intent);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
