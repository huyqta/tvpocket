package com.mobile.tvpocket.models;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Program implements Comparable<Program> {
	private int refchannel;
	private String name;
	private String datestart;
	private String timestart;
	private String posturl;
	private String duration;

	public int getRefchannel() {
		return refchannel;
	}

	public void setRefchannel(int refchannel) {
		this.refchannel = refchannel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDatestart() {
		return datestart;
	}

	public void setDatestart(String datestart) {
		this.datestart = datestart;
	}

	public String getTimestart() {
		return timestart;
	}

	public void setTimestart(String timestart) {
		this.timestart = timestart;
	}

	public String getPosturl() {
		return posturl;
	}

	public void setPosturl(String posturl) {
		this.posturl = posturl;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Override
	public int compareTo(Program another) {
		// TODO Auto-generated method stub
		DateTimeFormatter formmatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime dt0 = DateTime.parse(this.datestart + " " + this.timestart, formmatter);
		DateTime dt1 = DateTime.parse(another.datestart + " " + another.timestart, formmatter);
		return dt0.compareTo(dt1);
	}

}
