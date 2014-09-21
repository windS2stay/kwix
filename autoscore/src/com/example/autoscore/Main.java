package com.example.autoscore;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main extends Activity {
	/*
	 * 
	 * 변수 규칙 1. 클래스명 대문자로 시작 2. 레이아웃 소문자로 시작 3. 레이아웃 소문자는 단어별로 _로 나눈다 4. 다른것들은
	 * _로 나누지 않고 붙여서 쓴다.
	 */

	public static int nHeight, nWidth;
	Button startButton;
	Button useButton;
	Button developButton;
	TextView title;

	LinearLayout.LayoutParams btnSize;

	public static Typeface face;

	private TextView mTV_Text;
	private ScalableLayout mSL;

	private static final String DebugTag = "ScalableLayout_TestAndroid";

	private static void log(String pLog) {
		Log.e(DebugTag, "MainActivity] " + pLog);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		ScalableLayout.setLoggable(DebugTag);
		face = Typeface.createFromAsset(getAssets(), "fonts/Daum_Regular.ttf");

		RelativeLayout lLL = new RelativeLayout(this);
		// lLL.setOrientation(LinearLayout.VERTICAL);
		lLL.setBackgroundResource(R.drawable.background1);
		setContentView(lLL);

		mSL = new ScalableLayout(this, 640, 400);

		lLL.addView(mSL);

		startButton = new Button(this);
		startButton.setBackgroundResource(R.drawable.title_btn);
		mSL.addView(startButton, 360f, 100f, 250f, 100f);
		startButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Main.this, Setting.class);
				startActivity(intent);

			}
		});

		useButton = new Button(this);
		useButton.setBackgroundResource(R.drawable.title_btn);
		mSL.addView(useButton, 360f, 220f, 250f, 100f);
		useButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent =  new Intent(getBaseContext(),HowToUse.class);
				startActivity(intent);

			}
		});

		//글씨 크기
		mSL.setScale_TextSize(startButton, 40f);
		mSL.setScale_TextSize(useButton,40f);
		
		//글씨 내용 
		startButton.setText("start");
		useButton.setText("how to use");

		//글씨체 
		startButton.setTypeface(face);
		useButton.setTypeface(face);

		//글씨색
		startButton.setTextColor(Color.parseColor("#FFFFFFFF"));
		useButton.setTextColor(Color.parseColor("#FFFFFFFF"));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
