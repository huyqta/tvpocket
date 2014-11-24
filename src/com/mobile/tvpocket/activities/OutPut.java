package com.mobile.tvpocket.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mobile.tvpocket.R;
import com.splunk.mint.Mint;

public class OutPut extends Activity {
	TextView textmessage;
	String stringValue;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Mint.initAndStartSession(OutPut.this, "46a7ecb3");
		setContentView(R.layout.activity_output);

		textmessage = (TextView) findViewById(R.id.textView1);

		Intent intent = getIntent();
		stringValue = intent.getStringExtra("content");
		textmessage.setText(stringValue);
		System.out.println(stringValue);

	}
}