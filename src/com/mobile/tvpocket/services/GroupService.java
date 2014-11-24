package com.mobile.tvpocket.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobile.tvpocket.models.MyChannel;
import com.mobile.tvpocket.models.MyGroup;
import com.mobile.tvpocket.utils.FileUtility;
import com.mobile.tvpocket.utils.GlobalConstants;
import com.mobile.tvpocket.utils.RestUtil;

public class GroupService {
	public static void GetAllGroupsFromWS() {
		String ret = RestUtil.get("http://huyqta.esy.es/index.php/api/groups/GetAllGroups/format/json");
		Gson gson = new Gson();
		Type collectionType = new TypeToken<Collection<MyGroup>>() {
		}.getType();
		Collection<MyGroup> enums = gson.fromJson(ret, collectionType);
		GlobalConstants.ListGroups = new ArrayList<MyGroup>(enums);
		SetAllGroupsToFile();
	}

	private static void SetAllGroupsToFile() {
		try {
			Gson gson = new Gson();
			// path: data/data/com.mobile.tvpocket/files/<Filename>
			FileUtility.save(GlobalConstants.DataFileName.GROUPS.toString(), gson.toJson(GlobalConstants.ListGroups));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void GetAllGroupsFromFile() {

		String json = FileUtility.read(GlobalConstants.DataFileName.GROUPS.toString());
		if (json != "") {
			Gson gson = new Gson();
			Type collectionType = new TypeToken<Collection<MyGroup>>() {
			}.getType();
			Collection<MyGroup> enums = gson.fromJson(json, collectionType);
			GlobalConstants.ListGroups = new ArrayList<MyGroup>(enums);
		}
	}

	public static MyGroup GetGroupByChannelId(int channelid) {
		MyChannel mcn = ChannelService.GetChannelsById(channelid);
		MyGroup grp = new MyGroup();
		for (MyGroup mMyGroup : GlobalConstants.ListGroups) {
			if (mMyGroup.getId() == mcn.getRefgroup()) {
				grp = mMyGroup;
				break;
			}
		}
		return grp;
	}
}
