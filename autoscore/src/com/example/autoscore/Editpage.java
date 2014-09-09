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

public class Editpage extends Activity{

	int inputHz;
	scoreView score;
	Context context;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.edit_page);
		score = (scoreView)findViewById(R.id.scoreview);		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

