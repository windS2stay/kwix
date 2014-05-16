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
	TextView tv;

	// 4개의 액티비티를 묶어주는 액비티비 그
	public void onCreate(Bundle savedInstanceState) {
		Intent intent;
		TabSpec tab;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		mainTab = (TabHost) findViewById(R.id.tab_host);
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

		/*intent = new Intent(this, Set_quantize.class);
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
		mainTab.addTab(tab);*/

		// //인텐트 추가 완

		// ///////////////////////////////////////////////////////////////////
		// ////////////////////////////탭호스트 설////////////////////////////

		// //초기 설정////
		for (int i = 0; i < mainTab.getTabWidget().getChildCount(); i++) {
			mainTab.getTabWidget().getChildAt(i)
					.setBackgroundColor(Color.parseColor("#000000"));
			tv = (TextView) mainTab.getTabWidget().getChildAt(i)
					.findViewById(android.R.id.title);
			tv.setTextColor(Color.parseColor("#FFFFFF"));
			mainTab.getTabWidget().getChildAt(i).getLayoutParams().height = Main.nHeight  * 15;// 탭호스트비율
			tv.setTextSize(Main.nHeight * 4);
		}
		//배경은 검정 글자는 흰색 탭크기는 화면의 15% 글자 크기는 4%
		
		//선택된 탭만 색깔 변경 현재 초기는 tempo로 되어있다.
		mainTab.getTabWidget().getChildAt(mainTab.getCurrentTab())
				.setBackgroundColor(Color.parseColor("#FFFFFF"));
		tv = (TextView) mainTab.getTabWidget()
				.getChildAt(mainTab.getCurrentTab())
				.findViewById(android.R.id.title);
		tv.setTextColor(Color.parseColor("#000000"));

		//탭을 클릭할때 마다 바꾸게 해주는 리스너
		//설정값은 초기와 같다.
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
