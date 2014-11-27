package com.mobile.tvpocket.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.TextView;

import com.mobile.tvpocket.R;
import com.mobile.tvpocket.fragments.AdFragment;
import com.splunk.mint.Mint;

public class IntroductionActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Mint.initAndStartSession(IntroductionActivity.this, "46a7ecb3");
		setContentView(R.layout.activity_introduction);

		Typeface fontRobotoBlack = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");

		TextView product_name = (TextView) findViewById(R.id.introduction_product_name);
		TextView copyright = (TextView) findViewById(R.id.introduction_copyright);

		product_name.setTypeface(fontRobotoBlack);
		copyright.setTypeface(fontRobotoBlack);

		this.getActionBar().setHomeButtonEnabled(true);
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		this.getActionBar().setBackgroundDrawable(
				new ColorDrawable(getResources().getColor(R.color.Combo77GreenUnderline)));

		// Goi fragment Home
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		AdFragment adfragmentProgramAction = new AdFragment();
		fragmentTransaction.add(R.id.introduction_adFragment, adfragmentProgramAction, "AdFragment");
		fragmentTransaction.commit();
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
