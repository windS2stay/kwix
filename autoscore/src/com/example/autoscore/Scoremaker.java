package com.example.autoscore;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Scoremaker extends Activity {

	private ScalableLayout mSL;
	RelativeLayout scoremakerBG;
	HorizontalScrollView noteScroll;
	ImageButton metronomeButton, playButton, pauseButton, stopButton,
			recordButton, exportButton, settingButton, backButton,stateButton;

	TextView notes;
	Typeface note;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.scoremake);

		scoremakerBG = new RelativeLayout(this);
		scoremakerBG.setBackgroundResource(R.drawable.background2);
		setContentView(scoremakerBG);

		mSL = new ScalableLayout(this, 1280, 800);

		scoremakerBG.addView(mSL);

		noteScroll = new HorizontalScrollView(this);
		 mSL.addView(noteScroll, 15f, 150f, 1240f, 300f); // 수정하기

		setting();

		
		notes = new TextView(this);
		notes.setText("&4================================");
		notes.setTypeface(note);
		notes.setTextSize(120);
		noteScroll.addView(notes);


	}

	void setting() {
		metronomeButton = new ImageButton(this);
		playButton = new ImageButton(this);
		pauseButton = new ImageButton(this);
		stopButton = new ImageButton(this);
		recordButton = new ImageButton(this);
		exportButton = new ImageButton(this);
		settingButton = new ImageButton(this);
		backButton = new ImageButton(this);
		stateButton=new ImageButton(this);

		metronomeButton.setBackgroundResource(R.drawable.metro_off);
		playButton.setBackgroundResource(R.drawable.play_off);
		pauseButton.setBackgroundResource(R.drawable.pause_off);
		stopButton.setBackgroundResource(R.drawable.stop_off);
		recordButton.setBackgroundResource(R.drawable.btn_rec_off);
		exportButton.setBackgroundResource(R.drawable.export_off);
		backButton.setBackgroundResource(R.drawable.pre_off);
		settingButton.setBackgroundResource(R.drawable.option);
		stateButton.setBackgroundResource(R.drawable.edit_mode_off);

		mSL.addView(metronomeButton, 1153f, 44f, 91f, 63f);
		mSL.addView(playButton, 570f, 685f, 111f, 77f);
		mSL.addView(pauseButton, 710f, 685f, 111f, 77f);
		mSL.addView(stopButton, 850f, 685f, 111f, 77f);
		mSL.addView(recordButton, 390f, 685f, 111f, 77f);
		mSL.addView(exportButton, 1140f, 685f, 106f, 77f);
		mSL.addView(backButton, 16f, 685f, 77f, 75f);
		mSL.addView(settingButton, 16f, 15f, 86f, 83f);
		mSL.addView(stateButton, 133f, 685f, 77f, 77f);
		
		
		note = Typeface.createFromAsset(getAssets(), "fonts/MusiQwikB.ttf");

		btnSetting();
	}

	void btnSetting() {
		
		stateButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSL.removeView(metronomeButton);
				
			}
			
		});
	

		exportButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getBaseContext(), "export", 10).show();
			}

		});
	}

}
