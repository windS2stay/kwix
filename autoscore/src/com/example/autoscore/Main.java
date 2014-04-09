package com.example.autoscore;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.widget.*;
import android.content.*;
import android.view.*;

public class Main extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		TextView text = (TextView)findViewById(R.id.maintext);
		Typeface face = Typeface.createFromAsset(getAssets(), "fonts/nanumpen.ttf");
		text.setTypeface(face);
		
		Button btnstart = (Button)findViewById(R.id.startbutton);
		btnstart.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
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
