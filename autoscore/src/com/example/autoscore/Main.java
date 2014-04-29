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
	 * ���� ��Ģ 1. Ŭ������ �빮�ڷ� ���� 2. ���̾ƿ� �ҹ��ڷ� ���� 3. ���̾ƿ� �ҹ��ڴ� �ܾ�� _�� ������ 4. �ٸ��͵���
	 * _�� ������ �ʰ� �ٿ��� ����.
	 */
	
	
	int nHeight;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		TextView text = (TextView) findViewById(R.id.maintext);
		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/nanumpen.ttf");
		text.setTypeface(face);

		Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		nHeight = display.getHeight();
	
		text.setTextSize(nHeight / 100 * 10);
		
		Button btnstart = (Button) findViewById(R.id.startbutton);
		btnstart.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Main.this, Setting.class);
				startActivity(intent);
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
