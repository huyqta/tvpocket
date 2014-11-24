package com.mobile.tvpocket.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobile.tvpocket.models.MyChannel;
import com.mobile.tvpocket.models.MyFavourite;
import com.mobile.tvpocket.utils.FileUtility;
import com.mobile.tvpocket.utils.GlobalConstants;
import com.mobile.tvpocket.utils.RestUtil;

public class ChannelService {

	public static void GetAllChannelsFromWS() {
		try {
			String ret = RestUtil.get("http://huyqta.esy.es/index.php/api/channels/GetAllChannels/format/json");
			Gson gson = new Gson();
			Type collectionType = new TypeToken<Collection<MyChannel>>() {
			}.getType();
			Collection<MyChannel> enums = gson.fromJson(ret, collectionType);
			GlobalConstants.ListChannels = new ArrayList<MyChannel>(enums);
			SetAllChannelsToFile();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void SetAllChannelsToFile() {
		try {
			Gson gson = new Gson();
			// path: data/data/com.mobile.tvpocket/files/<Filename>
			FileUtility.save(GlobalConstants.DataFileName.CHANNELS.toString(),
					gson.toJson(GlobalConstants.ListChannels));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void GetAllChannelsFromFile() {

		String json = FileUtility.read(GlobalConstants.DataFileName.CHANNELS.toString());
		if (json != "") {
			Gson gson = new Gson();
			Type collectionType = new TypeToken<Collection<MyChannel>>() {
			}.getType();
			Collection<MyChannel> enums = gson.fromJson(json, collectionType);
			GlobalConstants.ListChannels = new ArrayList<MyChannel>(enums);
		}
	}

	public static void GetAllFavouritesFromFile() {
		String json = FileUtility.read(GlobalConstants.DataFileName.FAVOURITES.toString());
		if (json != "") {
			Gson gson = new Gson();
			Type collectionType = new TypeToken<Collection<MyFavourite>>() {
			}.getType();
			Collection<MyFavourite> enums = gson.fromJson(json, collectionType);
			GlobalConstants.ListFavourites = new ArrayList<MyFavourite>(enums);
		}
		if (json != "") {
			Gson gson = new Gson();
			Type collectionType = new TypeToken<Collection<MyFavourite>>() {
			}.getType();
			Collection<MyFavourite> enums = gson.fromJson(json, collectionType);
			GlobalConstants.ListFavourites = new ArrayList<MyFavourite>(enums);
		}
	}

	public static List<MyChannel> FilterChannelsByGroup(int idgroup) {
		List<MyChannel> filChannels = new ArrayList<MyChannel>();
		for (MyChannel mMyChannel : GlobalConstants.ListChannels) {
			if (mMyChannel.getRefgroup() == idgroup) {
				filChannels.add(mMyChannel);
			}
		}
		return filChannels;
	}

	public static MyChannel GetChannelsByName(String channel) {
		MyChannel filChannel = new MyChannel();
		for (MyChannel mMyChannel : GlobalConstants.ListChannels) {
			if (mMyChannel.getName().trim().toLowerCase().equals(channel.trim().toLowerCase())) {
				filChannel = mMyChannel;
				break;
			}
		}
		return filChannel;
	}

	public static List<MyChannel> FilterChannelsByName(String input) {
		List<MyChannel> filChannels = new ArrayList<MyChannel>();
		for (MyChannel mMyChannel : GlobalConstants.ListChannels) {
			if (mMyChannel.getName().trim().toLowerCase().indexOf(input.trim().toLowerCase()) > -1) {
				filChannels.add(mMyChannel);
			}
		}
		return filChannels;
	}

	public static MyChannel GetChannelsById(int channelid) {
		MyChannel filChannel = new MyChannel();
		for (MyChannel mMyChannel : GlobalConstants.ListChannels) {
			if (mMyChannel.getId() == channelid) {
				filChannel = mMyChannel;
				break;
			}
		}
		return filChannel;
	}

	public static String GetStreamFromUrl(String url) {
		String url_return = "";
		try {
			Map<String, String> par = Collections.singletonMap("url", url);

			url_return = RestUtil.post("http://128.199.167.236:5000/apis/getstreamfromhttp", par);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return url_return;
	}
}
