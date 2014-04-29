package com.example.autoscore;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class Setting extends ActivityGroup {
	TabHost mainTab;
	int nHeight;
	TextView tv;

	public void onCreate(Bundle savedInstanceState) {
		Intent intent;
		TabSpec tab;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		mainTab = (TabHost) findViewById(R.id.tabhost);
		mainTab.setup(getLocalActivityManager()); // 오류난 이유

		intent = new Intent(this, Set_tempo.class);
		tab = mainTab.newTabSpec("tempo");
		tab.setIndicator("tempo");
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		tab.setContent(intent);
		mainTab.addTab(tab);

		intent = new Intent(this, Set_key.class);
		// intent = new Intent(this, DateActivity.class);
		tab = mainTab.newTabSpec("key");
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		tab.setIndicator("key");
		tab.setContent(intent);
		mainTab.addTab(tab);

		intent = new Intent(this, Set_quantize.class);
		tab = mainTab.newTabSpec("quantize");
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		tab.setIndicator("quantize");
		tab.setContent(intent);
		mainTab.addTab(tab);

		intent = new Intent(this, Set_meter.class);
		tab = mainTab.newTabSpec("meter");
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		tab.setIndicator("meter/score");
		tab.setContent(intent);
		mainTab.addTab(tab);

		// ///////////////////////////////////////////////////////////////////
		// ////////////////////////////디스플레이 정보////////////////////////////

		Display display = ((WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		nHeight = display.getHeight();
		// Toast.makeText(this,nWidth+","+nHeight,Toast.LENGTH_LONG).show();
		// ///////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////

		// //초기값 설정////
		for (int i = 0; i < mainTab.getTabWidget().getChildCount(); i++) {
			mainTab.getTabWidget().getChildAt(i)
					.setBackgroundColor(Color.parseColor("#000000"));

			tv = (TextView) mainTab.getTabWidget().getChildAt(i)
					.findViewById(android.R.id.title);
			tv.setTextColor(Color.parseColor("#FFFFFF"));

			mainTab.getTabWidget().getChildAt(i).getLayoutParams().height = nHeight / 100 * 15;// 탭호스트비율
			tv.setTextSize(nHeight / 100 * 4);

		}
		mainTab.getTabWidget().getChildAt(mainTab.getCurrentTab())
				.setBackgroundColor(Color.parseColor("#FFFFFF"));
		tv = (TextView) mainTab.getTabWidget()
				.getChildAt(mainTab.getCurrentTab())
				.findViewById(android.R.id.title);
		tv.setTextColor(Color.parseColor("#000000"));

		// //////////////

		// // 계속 바뀔때마다 /////
		mainTab.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				for (int i = 0; i < mainTab.getTabWidget().getChildCount(); i++) {
					mainTab.getTabWidget().getChildAt(i)
							.setBackgroundColor(Color.parseColor("#000000"));
					tv = (TextView) mainTab.getTabWidget().getChildAt(i)
							.findViewById(android.R.id.title);
					tv.setTextColor(Color.parseColor("#FFFFFF"));
				}
				mainTab.getTabWidget().getChildAt(mainTab.getCurrentTab())
						.setBackgroundColor(Color.parseColor("#FFFFFF"));
				tv = (TextView) mainTab.getTabWidget()
						.getChildAt(mainTab.getCurrentTab())
						.findViewById(android.R.id.title);
				tv.setTextColor(Color.parseColor("#000000"));
			}
		});
	}
}
