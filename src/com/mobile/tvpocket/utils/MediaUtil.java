package com.mobile.tvpocket.utils;

import java.io.IOException;
import java.io.InputStream;

import android.graphics.drawable.Drawable;

import com.mobile.tvpocket.R;

public class MediaUtil {
	public static Drawable GetLocalLogo(int refchannel) {
		Drawable _Drawable = null;
		try {
			InputStream ims;			
			ims = GlobalConstants.ApplicationContext.getAssets().open("logos/" + refchannel + ".png");			
			_Drawable = Drawable.createFromStream(ims, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return _Drawable;
	}

	public static int GetProgramLogo(int groupid) {
		switch (groupid) {
		case 1:
			return R.drawable.ic_group_movies_blue;
		case 2:
			return R.drawable.ic_group_news_blue;
		case 3:
			return R.drawable.ic_group_style_blue;
		case 4:
			return R.drawable.ic_group_discovery_blue;
		case 5:
			return R.drawable.ic_group_kids_blue;
		case 6:
			return R.drawable.ic_group_sport_blue;
		case 7:
			return R.drawable.ic_group_entertaiment_blue;
		default:
			return R.drawable.ic_group_default_blue;
		}
	}
}
