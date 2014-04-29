package com.example.autoscore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Set_meter extends Activity {
	int nHeight;
	protected void onCreate(Bundle bun) {
		super.onCreate(bun);
		setContentView(R.layout.set_meter);
		Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		nHeight = display.getHeight();
		
		Button start=(Button)findViewById(R.id.recordbtn);
		
		
		start.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Set_meter.this, Playing.class);
				startActivity(intent);
			}
		});
	} // end

}