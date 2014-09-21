package com.example.autoscore;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class HowToUse extends Activity {

	int progress = 1;
	ImageView useExplain;
	RelativeLayout HTUBG;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		HTUBG = new RelativeLayout(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		useExplain = new ImageView(this);
		useExplain.setBackgroundResource(R.drawable.p2_1);
		useExplain
		.setScaleType(ImageView.ScaleType.FIT_CENTER);
		HTUBG.addView(useExplain);
		//fit_center

		setContentView(HTUBG);

		useExplain.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Toast.makeText(getBaseContext(), Integer.toString(event.getAction()), 10).show();
						
				if (MotionEvent.ACTION_UP == event.getAction()) {
					if (progress == 1) {
						useExplain.setBackgroundResource(R.drawable.p2_2);
						useExplain
								.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						progress++;
					} else if (progress == 2) {
						useExplain.setBackgroundResource(R.drawable.p2_3);
						useExplain
								.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						progress++;
					} else if (progress == 3) {
						useExplain.setBackgroundResource(R.drawable.p2_4);
						useExplain
								.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						progress++;
					} else if (progress == 4) {
						Toast.makeText(getBaseContext(), "3번째라능", 10).show();
						useExplain.setBackgroundResource(R.drawable.p3_1);
						useExplain
								.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						progress++;

					} else if (progress == 5) {
						useExplain.setBackgroundResource(R.drawable.p3_2);
						useExplain
								.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						progress++;

					} else if (progress == 6) {
						useExplain.setBackgroundResource(R.drawable.p3_3);
						useExplain
								.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						progress++;

					} else if (progress == 7) {
						useExplain.setBackgroundResource(R.drawable.p3_4);
						useExplain
								.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						progress++;

					} else if (progress == 8) {
						useExplain.setBackgroundResource(R.drawable.p3_5);
						useExplain
								.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						progress++;

					} else if (progress == 9) {
						useExplain.setBackgroundResource(R.drawable.p3_6);
						useExplain
								.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						progress++;

					} else if (progress == 10) {
						useExplain.setBackgroundResource(R.drawable.p3_7);
						useExplain
								.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						progress++;

					}
					else if (progress == 11) {
						useExplain.setBackgroundResource(R.drawable.p3_8);
						useExplain
								.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						progress++;

					} 
					else if (progress == 12) {
						Toast.makeText(getBaseContext(), "4번째라능", 10).show();
						useExplain.setBackgroundResource(R.drawable.p4_1);
						useExplain
								.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						progress++;

					} 
					else if (progress == 13) {
						useExplain.setBackgroundResource(R.drawable.p4_2);
						useExplain
								.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						progress++;

					} 
					else if (progress == 14) {
						useExplain.setBackgroundResource(R.drawable.p4_3);
						useExplain
								.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						progress++;

					} 
					else if (progress == 15) {
						useExplain.setBackgroundResource(R.drawable.p4_4);
						useExplain
								.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						progress++;

					} else {
						finish();
					}

					
				}
				return true; // return is important...
			}
		});
		/*
		 * useExplain.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub
		 * 
		 * 
		 * }
		 * 
		 * });
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
