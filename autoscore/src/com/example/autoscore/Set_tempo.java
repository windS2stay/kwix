package com.example.autoscore;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.NumberPicker.OnValueChangeListener;

public class Set_tempo extends Activity {
	int nHeight, nWidth;
	PowerManager.WakeLock mWakeLock;
	ImageButton playButton, stopButton;
	TickPlayer tp;
	NumberPicker tempo;
	int curTempo = 120;
	boolean mRunning = false;// 현재 메트로눔 재생중인가?

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// ////////Layout/////////////////
		// tempoPicker는 숫자조절하는 numberPicker
		// metronumeText metronume이라고 써있는 textView
		// metronomePlayButton = 플레이버튼
		// ///////////////////////////////
		super.onCreate(savedInstanceState);

	}

	// 1. 템포 최대 최소 정하기
	// 2. 다른 화면 갔다와서도 변하지 않는 값 유지하기
	// 3. 메트로눔 관련 약간 자잘한 오류 고치기

	protected void onResume() {
		super.onResume();
		// Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
		setContentView(R.layout.set_tempo);
		// /////////////////////////////
		// playButton = 플레이 버튼
		// tempo = numberPicker
		// metronumeText
		// ///////////////////////////
		mRunning = false;
		playButton = (ImageButton) findViewById(R.id.metronomePlayButton);
		stopButton = (ImageButton) findViewById(R.id.metronumeStopButton);
		tempo = (NumberPicker) findViewById(R.id.tempoPicker);
		Display display = ((WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		nHeight = display.getHeight();
		nWidth = display.getWidth();
		TextView metronumeText = (TextView) findViewById(R.id.metronumeText);
		metronumeText.setTextSize(nHeight / 100 * 5);
		metronumeText.setTextColor(Color.parseColor("#000000"));

		// /////////////////////////////메트로눔
		// 관련/////////////////////////////////////
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
				"MetronomeLock");
		tp = new TickPlayer(this);
		// /////////////////////////////메트로눔
		// 관련/////////////////////////////////////
		tempo.setMaxValue(200);
		tempo.setMinValue(50);
		tempo.setValue(curTempo);
		/*
		 * tempo.setOnValueChangedListener(new OnValueChangeListener() {
		 * 
		 * @Override public void onValueChange(NumberPicker picker, int oldVal,
		 * int newVal) { Toast.makeText(getApplicationContext(),"oldVal:" +
		 * oldVal + ", newVal:" + newVal,Toast.LENGTH_SHORT).show(); } });
		 */// numberPicker에서 바뀐값 확인할때 사용

		// ////numberpick 크기//////
		tempo.setMinimumHeight(nHeight / 100 * 40);
		tempo.setMinimumWidth(nWidth / 100 * 30);
		// //////////

		playButton.setOnClickListener(new OnClickListener() { // 플레이 버튼을 눌렀을 때
					public void onClick(View v) {
						mRunning = !mRunning;
						if (mRunning) {
							mWakeLock.acquire();
							tp.onStart(-1, tempo.getValue());
							curTempo = tempo.getValue();
						}
					}
				});
		stopButton.setOnClickListener(new OnClickListener() { // 플레이 버튼을 눌렀을 때
					public void onClick(View v) {
						mRunning = !mRunning;
						if (!mRunning) {
							mWakeLock.release();
							tp.onStop();
						}
					}
				});
	}

	protected void onPause() {
		super.onPause();
		// Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
		if (mRunning) {
			mWakeLock.release();
			tp.onStop();
		}
	}

	protected void onStop() {
		super.onStop();
		// Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
		if (mRunning) {
			mWakeLock.release();
			tp.onStop();
		}
	}

	protected void onDestory() {
		super.onDestroy();
		// Toast.makeText(this, "onDestory", Toast.LENGTH_SHORT).show();
		if (mRunning) {
			mWakeLock.release();
			tp.onStop();
	
		}
	}
}