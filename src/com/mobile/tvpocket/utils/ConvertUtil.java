package com.mobile.tvpocket.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobile.tvpocket.models.Program;

public class ConvertUtil {
	public static List<Program> ConvertFromMyTVHost(String dateget, String data) {
		List<Program> result = new ArrayList<Program>();
		data = data.replace("<p><strong>", "");
		List<String> programs = Arrays.asList(data.split("</p>"));
		for (String program : programs) {
			Program prg = new Program();
			String datetimestart = dateget + " " + program.split("</strong>")[0].trim();
			prg.setDatestart(DateTime.parse(datetimestart, DateTimeFormat.forPattern("dd/MM/yyyy")).toString(
					"dd/MM/yyyy"));
			prg.setTimestart(DateTime.parse(datetimestart, DateTimeFormat.forPattern("dd/MM/yyyy"))
					.toString("HH:mm:ss"));
			prg.setName(program.split("</strong>")[1].trim());
			result.add(prg);
		}
		return result;
	}

	public static List<Program> ConvertFromAPIHost(String data) {
		List<Program> result = new ArrayList<Program>();
		Gson gson = new Gson();
		Type collectionType = new TypeToken<Collection<Program>>() {
		}.getType();
		Collection<Program> enums = gson.fromJson(data, collectionType);
		result = new ArrayList<Program>(enums);
		return result;
	}
}
