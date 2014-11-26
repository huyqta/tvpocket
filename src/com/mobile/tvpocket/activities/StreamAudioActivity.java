package com.mobile.tvpocket.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.tvpocket.R;
import com.splunk.mint.Mint;

public class StreamAudioActivity extends Activity implements OnPreparedListener, OnErrorListener, OnCompletionListener {

	MediaPlayer mp;
	TextView tvStatus;
	ImageButton btPlay;
	ImageButton btStop;
	String sUrlWatchOnline;
	String sChannel;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio_stream);
		Mint.initAndStartSession(StreamAudioActivity.this, "46a7ecb3");
		Intent intent = getIntent();

		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");

		sUrlWatchOnline = intent.getBundleExtra("BUNDLE").getString("URLTV");
		sChannel = intent.getBundleExtra("BUNDLE").getString("CHANNEL");

		btPlay = (ImageButton) findViewById(R.id.audio_stream_play);
		btStop = (ImageButton) findViewById(R.id.audio_stream_stop);
		tvStatus = (TextView) findViewById(R.id.audio_stream_status);
		tvStatus.setText(sChannel);
		tvStatus.setTypeface(font);

		this.getActionBar().setHomeButtonEnabled(true);
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		this.getActionBar().setBackgroundDrawable(
				new ColorDrawable(getResources().getColor(R.color.Combo77GreenUnderline)));
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
		tvStatus.setText("Đang mở");
		mp.start();
	}

	// @Override
	public void onPlayClick() {
		try {
			tvStatus.setText("Đang mở");
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
			tvStatus.setText("Đã ngừng");
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
		Toast.makeText(getApplicationContext(), "Hoàn thành", Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
