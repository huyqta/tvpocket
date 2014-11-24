package com.mobile.tvpocket.utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;

public class FileUtility extends Activity {

	Context context;

	public FileUtility() {
	}

	public FileUtility(Context _context) {
		this.context = _context;
	}

	public static void save(String file, String data) {
		try {
			FileOutputStream fOut = GlobalConstants.ApplicationContext.openFileOutput(file, MODE_PRIVATE);
			fOut.write(data.getBytes());
			fOut.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String read(String file) {
		String json = "";
		try {
			// open the file for reading
			InputStream instream = GlobalConstants.ApplicationContext.openFileInput(file);
			if (instream != null) {
				InputStreamReader inputreader = new InputStreamReader(instream);
				BufferedReader buffreader = new BufferedReader(inputreader);
				String line;
				while ((line = buffreader.readLine()) != null) {
					json = json + line;
				}
			}
			instream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	// public void initAllDataFile(){
	// for (DataFileName filename : GlobalConstants.DataFileName.values()){
	// String filenamee = getFilesDir() + filename.toString();
	// File file = new File(getFilesDir() + filename.toString());
	// if (!file.exists()){
	// try {
	// file.createNewFile();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	// }
}
