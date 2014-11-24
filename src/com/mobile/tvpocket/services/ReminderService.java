package com.mobile.tvpocket.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobile.tvpocket.R;
import com.mobile.tvpocket.activities.ProgramActionsActivity;
import com.mobile.tvpocket.models.MyReminder;
import com.mobile.tvpocket.utils.GlobalConstants;

public class ReminderService extends Service {

	String message;
	private NotificationManager mManager;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "ReminderService.onBind()", Toast.LENGTH_LONG).show();
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);

		Context context = this.getApplicationContext();
		Bundle extras = intent.getExtras();
		message = extras.getString("PROGRAM");

		mManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		Intent intent1 = new Intent(this.getApplicationContext(), ProgramActionsActivity.class);

		Notification notification = new Notification(R.drawable.tvpocketlogo, message, System.currentTimeMillis());
		intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

		PendingIntent pendingNotificationIntent = PendingIntent.getActivity(context, 0, intent1,
				PendingIntent.FLAG_UPDATE_CURRENT);
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.ledARGB = Color.CYAN;
		notification.ledOnMS = 500;
		notification.ledOffMS = 500;
		notification.vibrate = new long[] { 100, 250, 100, 500 };
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.setLatestEventInfo(context, "TV Pocket", message, pendingNotificationIntent);

		mManager.notify(0, notification);

		// int icon = R.drawable.ic_launcher;
		// long when = System.currentTimeMillis();
		// NotificationManager notificationManager = (NotificationManager) context
		// .getSystemService(Context.NOTIFICATION_SERVICE);
		// @SuppressWarnings("deprecation")
		// Notification notification = new Notification(icon, message, when);
		// String title = context.getString(com.mobile.tvpocket.R.string.app_name);
		// String subTitle = context.getString(R.string.app_name);
		// Intent notificationIntent = new Intent(context, OutPut.class);
		// notificationIntent.putExtra("content", message);
		// PendingIntent pendintent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		// notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		//
		// notification.setLatestEventInfo(context, title, subTitle, pendintent);
		// // To play the default sound with your notification:
		// notification.defaults |= Notification.DEFAULT_SOUND;
		// notification.flags |= Notification.FLAG_AUTO_CANCEL;
		// notification.defaults |= Notification.DEFAULT_VIBRATE;
		// notificationManager.notify(0, notification);
	}

	private void stopNotification() {

	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG).show();
		return super.onUnbind(intent);
	}

	public static void GetAllRemindersFromFile() {

		String jsonReminder = "";
		try {
			// open the file for reading
			InputStream instream = GlobalConstants.ApplicationContext
					.openFileInput(GlobalConstants.DataFileName.REMINDERS.toString());

			// if file the available for reading
			if (instream != null) {
				// prepare the file for reading
				InputStreamReader inputreader = new InputStreamReader(instream);
				BufferedReader buffreader = new BufferedReader(inputreader);

				String line;

				// read every line of the file into the line-variable, on line
				// at the time
				while ((line = buffreader.readLine()) != null) {
					// do something with the settings from the file
					jsonReminder = jsonReminder + line;
				}

			}

			// close the file again
			instream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (jsonReminder != "") {
			Gson gson = new Gson();
			Type collectionType = new TypeToken<Collection<MyReminder>>() {
			}.getType();
			Collection<MyReminder> enums = gson.fromJson(jsonReminder, collectionType);
			GlobalConstants.ListReminders = new ArrayList<MyReminder>(enums);
		}
	}
}