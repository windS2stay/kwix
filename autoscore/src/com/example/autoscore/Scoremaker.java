package com.example.autoscore;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Scoremaker extends Activity {

	private ScalableLayout mSL;
	RelativeLayout scoremakerBG;
	ScrollView NOTE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.scoremake);

		scoremakerBG = new RelativeLayout(this);
		scoremakerBG.setBackgroundResource(R.drawable.background2);
		setContentView(scoremakerBG);

		mSL = new ScalableLayout(this, 640, 400); // 화면 사이즈 640*400

		scoremakerBG.addView(mSL);
		
		
		NOTE = new ScrollView(this);
		NOTE.setHorizontalScrollBarEnabled(true);
		NOTE.setVerticalScrollBarEnabled(false);
		mSL.addView(NOTE, 15f, 150f, 620f, 150f);
		
		
		
		//test
		TextView a;
		a=new TextView(this);
		a.setText("dasdfasdfasdfasdfasdfadfaswnrdflRjdiasdkfjasl;dfja;sldjfa;lsdkjf;alsdjkf;lasjdflkjasdlfjas;ldfa");
		a.setTextSize(100);
		
		NOTE.addView(a);
		
		

	}
}
