package com.example.autoscore;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Set_key extends Activity {

	// ////////Layout///////////////// ----->이거 수정하
	// tempoPicker는 숫자조절하는 numberPicker
	// metronumeText metronume이라고 써있는 textView
	// metronomePlayButton = 플레이버튼
	// ///////////////////////////////
	// /////음계 바꾸기
	// ///// 글씨 크기 조절
	// ////초기값 설정
	// /// 단조에 따라 화면에 표현해주는것 변경하기
	// // 플레이 버튼 추가하기

	// 악보 화면 추가하
	// 1. 다른 화면 갔다와서도 변하지 않는 값 유지하기

	int nHeight, nWidth;
	WheelView key;
	ImageButton playButton, stopButton;
	SoundManager s_manager;
	ImageView note;
	String keys[] = new String[] { "A", "A♭", "B", "B♭", "C", "D", "D♭", "E",
			"E♭", "F", "F#", "G" };

	Boolean isPlay = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	protected void onResume() {
		super.onResume();
		setContentView(R.layout.set_key);

		// Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
		key = (WheelView) findViewById(R.id.keyWheel);
		playButton = (ImageButton) findViewById(R.id.keyPlay);
		stopButton = (ImageButton) findViewById(R.id.keyStop);
		note = (ImageView) findViewById(R.id.keyNote);
		Display display = ((WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		nHeight = display.getHeight();
		nWidth = display.getWidth();

		key.setViewAdapter(new KeyArrayAdapter(this, keys, -1));
		key.setCurrentItem(4);// 기본값 C

		// ////////////////////////////////////////// Wheel 부분 가로 세로 여백조
		key.setMinimumHeight(nHeight / 100 * 30);
		key.setMinimumWidth(nWidth / 100 * 20);
		;
		// key.setPadding(0, 0, nWidth / 100 * 5, 0);
		note.setMinimumHeight(nHeight / 100 * 50);
		note.setMinimumWidth(nWidth / 100 * 50);
		// note.setPadding(nWidth / 100 * 10, 0, 0, 0);

		// /////////////////////////////////////////
		initSound();

		key.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				key.onTouchEvent(event);
				// Button tv = (Button) findViewById(R.id.recordbtn);
				// tv.setText(Integer.toString(key.getCurrentItem()));
				switch (key.getCurrentItem() + 1) {
				case 1:
					note.setImageResource(R.drawable.key_1);
					break;
				case 2:
					note.setImageResource(R.drawable.key_2);
					break;
				case 3:
					note.setImageResource(R.drawable.key_3);
					break;

				case 4:
					note.setImageResource(R.drawable.key_4);
					break;

				case 5:
					note.setImageResource(R.drawable.key_5);
					break;

				case 6:
					note.setImageResource(R.drawable.key_6);
					break;

				case 7:
					note.setImageResource(R.drawable.key_7);
					break;

				case 8:
					note.setImageResource(R.drawable.key_8);
					break;

				case 9:
					note.setImageResource(R.drawable.key_9);
					break;

				case 10:
					note.setImageResource(R.drawable.key_10);
					break;

				case 11:
					note.setImageResource(R.drawable.key_11);
					break;

				default:
					break;

				}
				return false;
			}

		});
		playButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				isPlay = !isPlay;
				if (isPlay) {
					playKey(key.getCurrentItem() + 1);
				}
			}
		});
		stopButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				isPlay = !isPlay;
				if (!isPlay) {
					stopKey(key.getCurrentItem() + 1);
				}
			}
		});
	}

	private void initSound() {
		s_manager = SoundManager.getInstance();
		// 사운드 매니져 초기화
		s_manager.init(this);
		// 사운드 등록
		s_manager.addSound(1, R.raw.a_key);
		s_manager.addSound(2, R.raw.ab_key);
		s_manager.addSound(3, R.raw.b_key);
		s_manager.addSound(4, R.raw.bb_key);
		s_manager.addSound(5, R.raw.c_key);
		s_manager.addSound(6, R.raw.d_key);
		s_manager.addSound(7, R.raw.db_key);
		s_manager.addSound(8, R.raw.e_key);
		s_manager.addSound(9, R.raw.eb_key);
		s_manager.addSound(10, R.raw.f_key);
		s_manager.addSound(11, R.raw.fs_key);
		s_manager.addSound(12, R.raw.g_key);
	}

	private void playKey(int key) {
		s_manager.play(key);
	}

	private void stopKey(int key) {
		for (int i = 1; i < 13; i++) {
			s_manager.stopSound(i);
		}
	}

	private class KeyWheelAdapter extends NumericWheelAdapter {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public KeyWheelAdapter(Context context, int minValue, int maxValue,
				int current) {
			super(context, minValue, maxValue);
			this.currentValue = current;
			setTextSize(10);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(0xFF0000F0);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	/**
	 * Adapter for string based wheel. Highlights the current value.
	 */
	private class KeyArrayAdapter extends ArrayWheelAdapter<String> {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public KeyArrayAdapter(Context context, String[] items, int current) {
			super(context, items);
			this.currentValue = current;

			// ////////////
			// /글자 크기////
			setTextSize(nHeight / 100 * 5);
			// ///////////
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(0xFF0000F0);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	protected void onPause() {
		super.onPause();
		// Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
		if (isPlay) {
			stopKey(key.getCurrentItem() + 1);
		}
	}

	protected void onStop() {
		super.onStop();
		// Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
		if (isPlay) {
			stopKey(key.getCurrentItem() + 1);
		}
	}

	protected void onDestory() {
		super.onDestroy();
		// Toast.makeText(this, "onDestory", Toast.LENGTH_SHORT).show();
		if (isPlay) {
			stopKey(key.getCurrentItem() + 1);
		}
	}
}