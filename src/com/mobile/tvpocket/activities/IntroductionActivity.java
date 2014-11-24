package com.mobile.tvpocket.activities;

import com.mobile.tvpocket.R;
import com.splunk.mint.Mint;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class IntroductionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//Mint.initAndStartSession(IntroductionActivity.this, "46a7ecb3");
		setContentView(R.layout.activity_introduction);

		Typeface fontRobotoBlack = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");

		TextView product_name = (TextView) findViewById(R.id.product_name);
		TextView copyright = (TextView) findViewById(R.id.copyright);

		product_name.setTypeface(fontRobotoBlack);
		copyright.setTypeface(fontRobotoBlack);

		this.getActionBar().setHomeButtonEnabled(true);
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		this.getActionBar().setBackgroundDrawable(
				new ColorDrawable(getResources().getColor(R.color.Combo77GreenUnderline)));
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
