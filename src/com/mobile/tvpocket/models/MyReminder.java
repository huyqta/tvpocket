package com.mobile.tvpocket.models;

public class MyReminder {
	private int channelid;
	private String channel;
	private String program;
	private String programtime;
	private String remindtime;

	public String getRemindtime() {
		return remindtime;
	}

	public void setRemindtime(String remindtime) {
		this.remindtime = remindtime;
	}

	public String getProgramtime() {
		return programtime;
	}

	public void setProgramtime(String programtime) {
		this.programtime = programtime;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public int getChannelId() {
		return channelid;
	}

	public void setChannelId(int channelid) {
		this.channelid = channelid;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}
}
