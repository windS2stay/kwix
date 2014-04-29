package com.example.autoscore;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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


	int nHeight;
	WheelView key;
	ImageButton playButton;
	SoundManager s_manager;
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
		Display display = ((WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		nHeight = display.getHeight();
		
		key.setViewAdapter(new KeyArrayAdapter(this, keys, -1));
		key.setCurrentItem(4);//기본값 C
		initSound();

		playButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				isPlay = !isPlay;
				if (isPlay) {
					playKey(key.getCurrentItem() + 1);
					playButton.setImageResource(R.drawable.pause);
				} else {
					stopKey(key.getCurrentItem() + 1);
					playButton.setImageResource(R.drawable.play);
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
		for(int i=1;i<13;i++)
		{
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
			setTextSize(100);
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
			setTextSize(16);
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
			playButton.setImageResource(R.drawable.play);
		}
	}

	protected void onStop() {
		super.onStop();
		// Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
		if (isPlay) {
			stopKey(key.getCurrentItem() + 1);
			playButton.setImageResource(R.drawable.play);
		}
	}

	protected void onDestory() {
		super.onDestroy();
		// Toast.makeText(this, "onDestory", Toast.LENGTH_SHORT).show();
		if (isPlay) {
			stopKey(key.getCurrentItem() + 1);
			playButton.setImageResource(R.drawable.play);
		}
	}
}