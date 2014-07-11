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
	
	 ���� ��Ģ
	 1. Ŭ������ �빮�ڷ� ����
	 2. ���̾ƿ� �ҹ��ڷ� ����
	 3. ���̾ƿ� �ҹ��ڴ� �ܾ�� _�� ������
	 4. �ٸ��͵��� _�� ������ �ʰ� �ٿ��� ����.
	 
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

	//////���÷��� �ʱ� ����//////	100���� ������
		Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		nHeight = display.getHeight()/100;
		nWidth=display.getWidth()/100;
	/////////////////////////////////	
	
		
		title.setTextSize(nHeight * 10);
		startButton.setTextSize(nHeight *4);
		useButton.setTextSize(nHeight * 4);
		//�۾� ������ ����
		
		title.setPadding(0,0,nWidth*5, 0);
	
		//startButton.setMargins(Main.nHeight * 10, Main.nHeight * 30, Main.nHeight * 10, Main.nHeight * 30);
		//useButton.setPadding(Main.nHeight * 10, Main.nHeight * 30, Main.nHeight * 10, Main.nHeight * 30);
		///��ư �̺�Ʈ 
		
		
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
