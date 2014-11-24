package com.mobile.tvpocket.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.tvpocket.R;

public class StreamAudioActivity extends Activity implements OnPreparedListener, OnErrorListener, OnCompletionListener {

	MediaPlayer mp;
	TextView tvStatus;
	ImageButton btPlay;
	ImageButton btStop;
	String sUrlWatchOnline;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio_stream);

		Intent intent = getIntent();

		sUrlWatchOnline = intent.getBundleExtra("BUNDLE").getString("URLTV");

		btPlay = (ImageButton) findViewById(R.id.play);
		btStop = (ImageButton) findViewById(R.id.stop);
		tvStatus = (TextView) findViewById(R.id.status);
		try {
			mp = new MediaPlayer();
			mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mp.setOnPreparedListener(this);
			mp.setOnErrorListener(this);
			mp.setDataSource(sUrlWatchOnline);
		} catch (Exception e) {
			Log.e("StreamAudioDemo", e.getMessage());
		}
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		Log.i("StreamAudioDemo", "prepare finished");
		tvStatus.setText("Playing.....");
		mp.start();
	}

	// @Override
	public void onPlayClick() {
		try {
			tvStatus.setText("Playing.....");
			if (!mp.isPlaying()) {
				mp.prepareAsync();
				mp.setOnCompletionListener(this);
			}
		} catch (Exception e) {
			Log.e("StreamAudioDemo", e.getMessage());
		}
	}

	public void onStopClick() {
		try {
			mp.stop();
			tvStatus.setText("STOP!");
		} catch (Exception e) {
			Log.e("StreamAudioDemo", e.getMessage());
		}
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		Toast.makeText(getApplicationContext(), "Completed", Toast.LENGTH_LONG).show();
	}
}
