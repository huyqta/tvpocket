package com.mobile.tvpocket.activities;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import com.mobile.tvpocket.R;
import com.mobile.tvpocket.services.ChannelService;
import com.mobile.tvpocket.utils.GlobalConstants;
import com.splunk.mint.Mint;

public class VideoViewActivity extends Activity {

	VideoView mVideoView;
	String sUrlWatchOnline;
	boolean bIsAudio;
	ProgressBar spinner;

	private void Trt1() {
		if (!TextUtils.isEmpty(sUrlWatchOnline)) {
			/*
			 * Alternatively,for streaming media you can use mVideoView.setVideoURI(Uri.parse(URLstring));
			 */
			// sUrlWatchOnline = ParseUrlStream(sUrlWatchOnline);
			new ParseURL().execute(sUrlWatchOnline);
		}
	}

	public void watchTV(String url) {
		mVideoView = (VideoView) findViewById(R.id.surface);

		mVideoView.setVideoPath(sUrlWatchOnline);
		mVideoView.setMediaController(new MediaController(this));
		mVideoView.requestFocus();
		mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);

		mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mediaPlayer) {
				// optional need Vitamio 4.0
				mediaPlayer.setPlaybackSpeed(1.0f);
			}
		});
	}

	private class ParseURL extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

		}

		@Override
		protected String doInBackground(String... params) {
			String url_correct = params[0];

			if (params[0].indexOf("rtmp://") < 0 && params[0].indexOf("rtsp://") < 0) {
				String htmlsource;
				try {
					url_correct = ChannelService.GetStreamFromUrl(url_correct);
					// htmlsource = Jsoup.connect(params[0]).get().html();
					// if (url_correct.indexOf("htvonline") > -1) {
					// url_correct = getUrlStreamFrom_htvonline(htmlsource);
					// }
					// if (url_correct.indexOf("tivi360") > -1) {
					// url_correct = getUrlStreamFrom_tivi360(htmlsource);
					// }
					// if (url_correct.indexOf("http://tv.tivi24h.com") > -1) {
					// url_correct = getUrlStreamFrom_tivi24h_lvl2(htmlsource);
					// }
					//
					// if (url_correct.indexOf("http://www.tivi24h.com") > -1) {
					// // url_correct = getUrlStreamFrom_tivi24h_lvl1(htmlsource);
					// url_correct = ChannelService.GetStreamFromUrl(url_correct);
					// }
					// if (url_correct.indexOf("xemphimso.com") > -1) {
					// url_correct = ChannelService.GetStreamFromUrl(url_correct);
					// }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(VideoViewActivity.this, "This stream is not available!", Toast.LENGTH_LONG).show();
					finish();
				}
			}
			return url_correct;
		}

		protected String getUrlStreamFrom_tivi24h_lvl2(String htmlsource) {
			List<String> listMatches = new ArrayList<String>();
			List<String> patterns = new ArrayList<String>();
			patterns.add("(var responseText = ).*(;)");
			patterns.add("(file: \").*(\",image:)");
			for (String pat : patterns) {
				Pattern mPattern = Pattern.compile(pat);

				Matcher matcher = mPattern.matcher(htmlsource);

				while (matcher.find()) {
					listMatches.add(matcher.group());
				}
			}
			if (listMatches.size() == 0)
				return "";
			String[] arrayUrl = listMatches.get(0).split(",");
			String retUrl = arrayUrl[0].replace("var responseText = \"", "");
			retUrl = retUrl.replace("\";", "");
			retUrl = retUrl.replace("file: \"", "");
			retUrl = retUrl.replace("\",image:", "");
			return retUrl;
		}

		protected String getUrlStreamFrom_htvonline(String htmlsource) {
			int begin = htmlsource.indexOf("file: ");
			int end = htmlsource.indexOf(".m3u8");
			int pos_token = htmlsource.indexOf("m3u8?t=");

			String text = htmlsource.substring(begin + 7, end);
			String token = htmlsource.substring(pos_token, pos_token + 52);
			return text + "." + token;
		}

		protected String getUrlStreamFrom_tivi24h_lvl1(String htmlsource) {
			Pattern mPattern = Pattern.compile("(var responseText = ).*(;)");

			Matcher matcher = mPattern.matcher(htmlsource);
			List<String> listMatches = new ArrayList<String>();

			while (matcher.find()) {
				listMatches.add(matcher.group());
			}
			if (listMatches.size() == 0)
				return "";
			String[] arrayUrl = listMatches.get(0).split(",");
			String retUrl = arrayUrl[0].replace("var responseText = \"", "");
			retUrl = retUrl.replace("\";", "");
			return retUrl;
		}

		protected String getUrlStreamFrom_xemphimso(String htmlsource) {
			Pattern mPattern = Pattern.compile("(file: ').*(',)");

			Matcher matcher = mPattern.matcher(htmlsource);
			List<String> listMatches = new ArrayList<String>();

			while (matcher.find()) {
				listMatches.add(matcher.group());
			}
			if (listMatches.size() == 0)
				return "";
			String[] arrayUrl = listMatches.get(0).split(",");
			String retUrl = arrayUrl[0].replace("file: '", "");
			retUrl = retUrl.replace("',", "");
			return retUrl;
		}

		protected String getUrlStreamFrom_tivi360(String htmlsource) {
			Pattern mPattern = Pattern.compile("(var responseText = ).*(;)");

			Matcher matcher = mPattern.matcher(htmlsource);
			List<String> listMatches = new ArrayList<String>();

			while (matcher.find()) {
				listMatches.add(matcher.group(2));
			}
			if (listMatches.size() == 0)
				return "";
			// abc0 = abc0.replace("var responseText = \"", "");
			// abc0 = abc0.replace("\";", "");
			String[] arrayUrl = listMatches.get(0).split(",");
			return arrayUrl[0];
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				if (StringUtil.isBlank(result)) {
					Toast.makeText(VideoViewActivity.this, "This stream is not available!", Toast.LENGTH_LONG).show();
					finish();
				}
				if (!bIsAudio && result != "") {
					mVideoView = (VideoView) findViewById(R.id.surface);

					mVideoView.setVideoPath(result);
					// mVideoView.setMediaController(new MediaController(this));
					mVideoView.requestFocus();
					mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);

					// mVideoView.setBackgroundResource(R.drawable.ic_launcher);
					mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
						@Override
						public void onPrepared(MediaPlayer mediaPlayer) {
							// optional need Vitamio 4.0

							mediaPlayer.setPlaybackSpeed(1.0f);
							spinner.setVisibility(View.GONE);
						}
					});
				} else {
					// MediaPlayer mp = new MediaPlayer(VideoViewActivity.this);
					// Uri uri = Uri.parse(result);
					//
					// mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
					// mp.setOnPreparedListener(this);
					// mp.setOnErrorListener(this);
					// mp.setDataSource("http://www.robtowns.com/music/blind_willie.mp3");
					// mp.prepareAsync();
					// mp.setOnCompletionListener(this);
					finish();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(VideoViewActivity.this, "This stream is not available!", Toast.LENGTH_LONG).show();
				finish();
			}
		}

		public boolean getStatus(String url) throws IOException {

			boolean result = false;
			try {
				URL siteURL = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();

				int code = connection.getResponseCode();
				if (code == 200) {
					result = true;
				}
			} catch (Exception e) {
				result = false;
			}
			return result;
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Mint.initAndStartSession(VideoViewActivity.this, "46a7ecb3");
		setContentView(R.layout.videolayout);

		Intent intent = getIntent();

		sUrlWatchOnline = intent.getBundleExtra("BUNDLE").getString("URLTV");
		bIsAudio = intent.getBundleExtra("BUNDLE").getBoolean("ISAUDIO");

		if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
			return;

		spinner = (ProgressBar) findViewById(R.id.progressBar1);
		spinner.setVisibility(View.VISIBLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Trt1();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

			mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);

		} else {
			mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_ORIGIN, 0);

		}

	}
}
