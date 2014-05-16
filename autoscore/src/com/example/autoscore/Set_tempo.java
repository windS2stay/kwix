package com.example.autoscore;



import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.NumberPicker.OnValueChangeListener;

public class Set_tempo extends Activity {
	PowerManager.WakeLock mWakeLock;
	ImageButton playButton, stopButton;
	TickPlayer tp;
	NumberPicker tempo;
	int curTempo = 120;
	boolean mRunning = false;// 현재 메트로눔 재생중인가?
	WheelView quantize;

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
		playButton = (ImageButton) findViewById(R.id.metronome_play_button);
		stopButton = (ImageButton) findViewById(R.id.metronume_stop_button);
		tempo = (NumberPicker) findViewById(R.id.tempo_picker);

		
	//	metronumeText.setTextSize(Main.nHeight * 5);
	//	metronumeText.setTextColor(Color.parseColor("#000000"));
		quantize = (WheelView) findViewById(R.id.quantizeWheel);
		//triple=(CheckBox)findViewById(R.id.tripleCheckBox);
		//tripleImage=(ImageView)findViewById(R.id.triple_image);
		
		quantize.setVisibleItems(3);
		quantize.setViewAdapter(new QuantizeAdapter(this));

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
		quantize.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "dd", Toast.LENGTH_SHORT);
				
			}
			
		});
		
		
		//metronumeText.setPadding(0, 0, 0, Main.nHeight*10);
		playButton.setPadding(Main.nHeight * 5, Main.nHeight * 5,
				Main.nHeight * 5, Main.nHeight * 5);
		stopButton.setPadding(Main.nHeight * 5, Main.nHeight * 5,
				Main.nHeight * 5, Main.nHeight * 5);
		tempo.setPadding(0,0,Main.nHeight*10,0);
		
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

	// /화면을 바꾸거나 종료시에 소리 종료///
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
	
	private class QuantizeAdapter extends AbstractWheelTextAdapter {
		// Countries names
		private String countries[] = new String[] { "2분음표", "ㅇ", "Ukraine",
				"France" };
		// Countries flags
		private int flags[] = new int[] { R.drawable.note_2, R.drawable.note_4,
				R.drawable.note_8, R.drawable.note_16 };

		/**
		 * Constructor
		 */
		protected QuantizeAdapter(Context context) {
			super(context, R.layout.note_layout, NO_RESOURCE);
		
		//	setItemTextResource(R.id.metronume_text);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			ImageView img = (ImageView) view.findViewById(R.id.note_imageview);
			img.setImageResource(flags[index]);
			return view;
		}

		@Override
		public int getItemsCount() {
			return countries.length;
		}

		@Override
		protected CharSequence getItemText(int index) {
			return countries[index];
		}
	}
}