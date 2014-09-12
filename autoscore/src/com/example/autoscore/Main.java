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
	 * ���� ��Ģ 1. Ŭ������ �빮�ڷ� ���� 2. ���̾ƿ� �ҹ��ڷ� ���� 3. ���̾ƿ� �ҹ��ڴ� �ܾ�� _�� ������ 4. �ٸ��͵���
	 * _�� ������ �ʰ� �ٿ��� ����.
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
				useButton.setText("coming soon");

			}
		});

		//�۾� ũ��
		mSL.setScale_TextSize(startButton, 40f);
		mSL.setScale_TextSize(useButton,40f);
		
		//�۾� ���� 
		startButton.setText("start");
		useButton.setText("how to use");

		//�۾�ü 
		startButton.setTypeface(face);
		useButton.setTypeface(face);

		//�۾���
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
