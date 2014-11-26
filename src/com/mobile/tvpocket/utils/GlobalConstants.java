package com.mobile.tvpocket.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.DrawerLayout;

import com.mobile.tvpocket.R;
import com.mobile.tvpocket.adapter.HomePagerAdapter;
import com.mobile.tvpocket.fragments.AdFragment;
import com.mobile.tvpocket.models.MyChannel;
import com.mobile.tvpocket.models.MyFavourite;
import com.mobile.tvpocket.models.MyGroup;
import com.mobile.tvpocket.models.MyReminder;
import com.mobile.tvpocket.models.Program;

public class GlobalConstants {
	
	public static AdFragment adfragment;

	public static DrawerLayout HomeActivityDrawerLayout;
	
	public static String PathFileDir;

	public static Context ApplicationContext;

	public static Context MainContext;

	public static HomePagerAdapter.HomeDetailFragment HomeDetailFragment;

	public static Thread ThreadNextPrograms;
	
	public static int RangeNextPrograms = 3;

	public static List<String> ApplicationMenus = Arrays.asList("Danh sách kênh", "Nhắc nhở", "Yêu thích",
			"Giới thiệu", "Tìm kiếm");

	public static List<Integer> ApplicationMenuIcons = Arrays.asList(R.drawable.ic_channel_catalog_blue,
			R.drawable.ic_reminder_blue, R.drawable.ic_favourite_blue, R.drawable.ic_introduction_blue,
			R.drawable.ic_search_blue);

	// public static List<String> ChannelGroups = Arrays.asList("Sports",
	// "Movies", "Kids", "News", "Adults", "Styles");

	// Add more if you need
	public enum CustomLayout {
		APPLICATION_MENU, CHANNEL_CATALOG_ITEM, REMINDERS, PROGRAMS, FAVOURITES, PROGRAMONAIR, NEXTPROGRAMS, SEARCH_CHANNEL
	}

	// Add more if you need
	public enum DataFileName {
		REMINDERS, PROGRAMS, CHANNELS, FAVOURITES, GROUPS;

		@Override
		public String toString() {
			String ret = "";
			switch (this) {
			case REMINDERS:
				ret = "reminders.json";
				break;
			case PROGRAMS:
				ret = "programs.json";
				break;
			case CHANNELS:
				ret = "channels.json";
				break;
			case FAVOURITES:
				ret = "favourites.json";
				break;
			case GROUPS:
				ret = "groups.json";
				break;
			}
			return ret;
		}
	}

	public static List<MyReminder> ListReminders = new ArrayList<MyReminder>();

	public static List<MyChannel> ListChannels = new ArrayList<MyChannel>();

	public static List<MyGroup> ListGroups = new ArrayList<MyGroup>();

	public static List<Program> ListProgramsOnAir = new ArrayList<Program>();

	public static List<Program> ListProgramsNext = new ArrayList<Program>();

	public static List<MyFavourite> ListFavourites = new ArrayList<MyFavourite>();

	public static boolean isConnectingToInternet(Context _context) {
		ConnectivityManager connectivity = (ConnectivityManager) _context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
		}
		return false;
	}
}
