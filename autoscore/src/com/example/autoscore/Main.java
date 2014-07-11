package com.example.autoscore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {
	/*
	
	 변수 규칙
	 1. 클래스명 대문자로 시작
	 2. 레이아웃 소문자로 시작
	 3. 레이아웃 소문자는 단어별로 _로 나눈다
	 4. 다른것들은 _로 나누지 않고 붙여서 쓴다.
	 
	 */
	
	
	public static int nHeight,nWidth;
	Button startButton;
	Button useButton;
	Button developButton;
	TextView title;
	
	public static Typeface face;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		title = (TextView) findViewById(R.id.maintext);
		startButton = (Button) findViewById(R.id.start_button);
		useButton = (Button) findViewById(R.id.use_button);
		
		face = Typeface.createFromAsset(getAssets(),
				"fonts/Daum_Regular.ttf");
		title.setTypeface(face);
		startButton.setTypeface(face);
		useButton.setTypeface(face);

	//////디스플레이 초기 설정//////	100으로 나눠줬
		Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		nHeight = display.getHeight()/100;
		nWidth=display.getWidth()/100;
	/////////////////////////////////	
	
		
		title.setTextSize(nHeight * 10);
		startButton.setTextSize(nHeight *4);
		useButton.setTextSize(nHeight * 4);
		//글씨 사이즈 변경
		
		title.setPadding(0,0,nWidth*5, 0);
	
		//startButton.setMargins(Main.nHeight * 10, Main.nHeight * 30, Main.nHeight * 10, Main.nHeight * 30);
		//useButton.setPadding(Main.nHeight * 10, Main.nHeight * 30, Main.nHeight * 10, Main.nHeight * 30);
		///버튼 이벤트 
		
		
		startButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Main.this, Setting.class);
				startActivity(intent);
			}
		});
		useButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				useButton.setText("coming soon");
			}
		});
	

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
