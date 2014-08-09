package com.example.autoscore;

import android.os.*;
import android.app.*;
import android.widget.*;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.*;
import android.content.*;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;

public class Scoremake extends Activity{

	int inputHz;
	EditText hz;
	Button setup;
	CustomView score;
	Context context;
	TextView text;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score);
		Intent intent = getIntent();
		String tempo = intent.getExtras().getString("tempo");
		String type = intent.getExtras().getString("type");
		String key = intent.getExtras().getString("key");
		String triple = intent.getExtras().getString("triple");
		String quanizer = intent.getExtras().getString("quanizer");
		String meter = intent.getExtras().getString("meter");
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		
		int diswidth = displayMetrics.widthPixels;
		int disheight = displayMetrics.heightPixels;
		
		score = (CustomView)findViewById(R.id.customview);
		setup = (Button)findViewById(R.id.setup);
		text.setTextSize(TypedValue.COMPLEX_UNIT_SP, diswidth/24);
		
		setup.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {				
				
			}
		});
	}
}

