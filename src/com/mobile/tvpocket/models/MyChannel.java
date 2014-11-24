package com.mobile.tvpocket.models;

import android.graphics.drawable.Drawable;

import com.mobile.tvpocket.utils.MediaUtil;

public class MyChannel {
	private int id;
	private String name;
	private String urlcrawl;
	private String urlapi;
	private String urllogo;
	private int refgroup;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlcrawl() {
		return urlcrawl;
	}

	public void setUrlcrawl(String urlcrawl) {
		this.urlcrawl = urlcrawl;
	}

	public String getUrlapi() {
		return urlapi;
	}

	public void setUrlapi(String urlapi) {
		this.urlapi = urlapi;
	}

	public String getUrllogo() {
		return urllogo;
	}

	public void setUrllogo(String urllogo) {
		this.urllogo = urllogo;
	}

	public int getRefgroup() {
		return refgroup;
	}

	public void setRefgroup(int refgroup) {
		this.refgroup = refgroup;
	}

	public Drawable getLocalLogo() {
		return MediaUtil.GetLocalLogo(id);
	}

}
