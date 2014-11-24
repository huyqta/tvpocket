package com.mobile.tvpocket.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.mobile.tvpocket.R;
import com.mobile.tvpocket.activities.OutPut;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();

		// generateNotification(context, extras.getString("PROGRAM"));
		Toast.makeText(context, extras.getString("PROGRAM"), Toast.LENGTH_LONG).show();
		Intent service1 = new Intent(context, ReminderService.class);
		service1.putExtra("PROGRAM", extras.getString("PROGRAM"));
		context.startService(service1);

		try {
			Thread.sleep(15000);
			context.stopService(service1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	private void generateNotification(Context context, String message) {

		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, message, when);
		String title = context.getString(com.mobile.tvpocket.R.string.app_name);
		String subTitle = context.getString(R.string.app_name);
		Intent notificationIntent = new Intent(context, OutPut.class);
		notificationIntent.putExtra("content", message);
		PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

		notification.setLatestEventInfo(context, title, subTitle, intent);
		// To play the default sound with your notification:
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notificationManager.notify(0, notification);
	}

}
