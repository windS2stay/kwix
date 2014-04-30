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
	boolean mRunning = false;// ���� ��Ʈ�δ� ������ΰ�?

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// ////////Layout/////////////////
		// tempoPicker�� ���������ϴ� numberPicker
		// metronumeText metronume�̶�� ���ִ� textView
		// metronomePlayButton = �÷��̹�ư
		// ///////////////////////////////
		super.onCreate(savedInstanceState);

	}

	// 1. ���� �ִ� �ּ� ���ϱ�
	// 2. �ٸ� ȭ�� ���ٿͼ��� ������ �ʴ� �� �����ϱ�
	// 3. ��Ʈ�δ� ���� �ణ ������ ���� ��ġ��

	protected void onResume() {
		super.onResume();
		// Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
		setContentView(R.layout.set_tempo);
		// /////////////////////////////
		// playButton = �÷��� ��ư
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

		// /////////////////////////////��Ʈ�δ�
		// ����/////////////////////////////////////
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
				"MetronomeLock");
		tp = new TickPlayer(this);
		// /////////////////////////////��Ʈ�δ�
		// ����/////////////////////////////////////
		tempo.setMaxValue(200);
		tempo.setMinValue(50);
		tempo.setValue(curTempo);
		/*
		 * tempo.setOnValueChangedListener(new OnValueChangeListener() {
		 * 
		 * @Override public void onValueChange(NumberPicker picker, int oldVal,
		 * int newVal) { Toast.makeText(getApplicationContext(),"oldVal:" +
		 * oldVal + ", newVal:" + newVal,Toast.LENGTH_SHORT).show(); } });
		 */// numberPicker���� �ٲﰪ Ȯ���Ҷ� ���

		// ////numberpick ũ��//////
		tempo.setMinimumHeight(nHeight / 100 * 40);
		tempo.setMinimumWidth(nWidth / 100 * 30);
		// //////////

		playButton.setOnClickListener(new OnClickListener() { // �÷��� ��ư�� ������ ��
					public void onClick(View v) {
						mRunning = !mRunning;
						if (mRunning) {
							mWakeLock.acquire();
							tp.onStart(-1, tempo.getValue());
							curTempo = tempo.getValue();
						}
					}
				});
		stopButton.setOnClickListener(new OnClickListener() { // �÷��� ��ư�� ������ ��
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