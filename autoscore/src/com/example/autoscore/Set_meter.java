package com.example.autoscore;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Set_meter extends Activity {
	TextView scoreText;
	TextView meterText;
	RadioGroup meter, score;
	RadioButton g_clef;
	RadioButton f_clef;
	LinearLayout g_clefLinear,f_clefLinear;

	protected void onCreate(Bundle bun) {
		super.onCreate(bun);
		setContentView(R.layout.set_meter);

		scoreText = (TextView) findViewById(R.id.score_text);
		meterText = (TextView) findViewById(R.id.meter_text);
		meter = (RadioGroup) findViewById(R.id.meter_group);
		score = (RadioGroup) findViewById(R.id.score_group);
		g_clef = (RadioButton) findViewById(R.id.g_clef_radio);
		f_clef = (RadioButton) findViewById(R.id.f_clef_radio);
		g_clefLinear=(LinearLayout)findViewById(R.id.g_linear);
		f_clefLinear=(LinearLayout)findViewById(R.id.f_linear);

		meter.setPadding(0, 0, Main.nWidth * 5, 0);
		scoreText.setTextSize(Main.nHeight * 5);
		scoreText.setTextColor(Color.parseColor("#000000"));
		meterText.setTextSize(Main.nHeight * 3);
		meterText.setTextColor(Color.parseColor("#000000"));

		
		g_clefLinear.setPadding(Main.nHeight*3, Main.nHeight*3, Main.nHeight*3, Main.nHeight*3);
		f_clefLinear.setPadding(Main.nHeight*3, Main.nHeight*3, Main.nHeight*3, Main.nHeight*3);
		g_clef.setOnClickListener(new Button.OnClickListener()
		{

			@Override
			public void onClick(View arg0) {
				//scoreText.setText("ddd");
				f_clef.setChecked(false);
				
			}
		
		});
		f_clef.setOnClickListener(new Button.OnClickListener()
		{

			@Override
			public void onClick(View arg0) {
				//scoreText.setText("fff");
				g_clef.setChecked(false);
			}
		
		});



		for (int i = 0; i < meter.getChildCount(); i++) {
			TextView tv = (TextView) meter.getChildAt(i);
			tv.setTextSize(Main.nHeight * 6);
			tv.setTextColor(Color.parseColor("#000000"));
			tv.setPadding(Main.nHeight * 15, 0, Main.nHeight * 5,
					Main.nHeight * 5);
		}

		Button start = (Button) findViewById(R.id.recordbtn);
		start.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Set_meter.this, Playing.class);
				startActivity(intent);
			}
		});
	} // end

}