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

	// 4���� ��Ƽ��Ƽ�� �����ִ� �׺�Ƽ�� ��
	public void onCreate(Bundle savedInstanceState) {
		Intent intent;
		TabSpec tab;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		mainTab = (TabHost) findViewById(R.id.tab_host);
		mainTab.setup(getLocalActivityManager()); // ������ ����

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

		// //����Ʈ �߰� ��

		// ///////////////////////////////////////////////////////////////////
		// ////////////////////////////��ȣ��Ʈ ��////////////////////////////

		// //�ʱ� ����////
		for (int i = 0; i < mainTab.getTabWidget().getChildCount(); i++) {
			mainTab.getTabWidget().getChildAt(i)
					.setBackgroundColor(Color.parseColor("#000000"));
			tv = (TextView) mainTab.getTabWidget().getChildAt(i)
					.findViewById(android.R.id.title);
			tv.setTextColor(Color.parseColor("#FFFFFF"));
			mainTab.getTabWidget().getChildAt(i).getLayoutParams().height = Main.nHeight  * 15;// ��ȣ��Ʈ����
			tv.setTextSize(Main.nHeight * 4);
		}
		//����� ���� ���ڴ� ��� ��ũ��� ȭ���� 15% ���� ũ��� 4%
		
		//���õ� �Ǹ� ���� ���� ���� �ʱ�� tempo�� �Ǿ��ִ�.
		mainTab.getTabWidget().getChildAt(mainTab.getCurrentTab())
				.setBackgroundColor(Color.parseColor("#FFFFFF"));
		tv = (TextView) mainTab.getTabWidget()
				.getChildAt(mainTab.getCurrentTab())
				.findViewById(android.R.id.title);
		tv.setTextColor(Color.parseColor("#000000"));

		//���� Ŭ���Ҷ� ���� �ٲٰ� ���ִ� ������
		//�������� �ʱ�� ����.
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
