package com.example.autoscore;

import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

	ImageButton playButton, stopButton;
	TextView keyText,meterText,scoreText;
	TextView key,meter,score;
	SoundManager s_manager;
	ImageView note;
	LinearLayout l1,l2,l3,l4;
	CharSequence[] keys = { "A", "A♭", "B", "B♭", "C", "D", "D♭", "E",
			"E♭", "F", "F#", "G" };
	CharSequence[] meters={"3/4","4/4","6/8"};
	Boolean isPlay = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected void onResume() {
		super.onResume();
		setContentView(R.layout.set_key);

		// Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
		playButton = (ImageButton) findViewById(R.id.key_play);
		stopButton = (ImageButton) findViewById(R.id.key_stop);
		note = (ImageView) findViewById(R.id.key_note);
		keyText = (TextView) findViewById(R.id.key_text);
		meterText = (TextView) findViewById(R.id.meter_text);
		scoreText = (TextView) findViewById(R.id.score_text);
		key = (TextView) findViewById(R.id.key);
		meter = (TextView) findViewById(R.id.meter);
		score = (TextView) findViewById(R.id.score);
		
		LinearLayout l1=(LinearLayout)findViewById(R.id.other_linear);
		LinearLayout l2=(LinearLayout)findViewById(R.id.linear1);
		LinearLayout l3=(LinearLayout)findViewById(R.id.linear2);
		LinearLayout l4=(LinearLayout)findViewById(R.id.linear3);
		
		note.setMinimumHeight(Main.nHeight * 50);
		note.setMinimumWidth(Main.nWidth * 30);
		keyText.setTextSize(Main.nHeight * 5);
		meterText.setTextSize(Main.nHeight * 5);
		scoreText.setTextSize(Main.nHeight * 5);
		key.setTextSize(Main.nHeight * 10);
		meter.setTextSize(Main.nHeight * 7);
		score.setTextSize(Main.nHeight * 6);

		// /////////////////////여백 조절////////////////////
		note.setPadding(Main.nHeight * 10, 0, Main.nHeight * 5, 0);
		keyText.setPadding(Main.nHeight, 0, Main.nHeight, 0);
		playButton.setPadding(Main.nHeight * 5, Main.nHeight * 5,
				Main.nHeight * 5, Main.nHeight * 5);
		stopButton.setPadding(Main.nHeight * 5, Main.nHeight * 5,
				Main.nHeight * 5, Main.nHeight * 5);
		l2.setPadding(Main.nHeight, Main.nHeight,Main.nHeight, Main.nHeight);
		l3.setPadding(Main.nHeight, Main.nHeight,Main.nHeight, Main.nHeight);
		l4.setPadding(Main.nHeight, Main.nHeight,Main.nHeight, Main.nHeight);
		l1.setPadding(Main.nHeight*5, 0,Main.nHeight*5, 0);

		keyText.setWidth(Main.nWidth * 20);
		scoreText.setWidth(Main.nWidth * 20);
		meterText.setWidth(Main.nWidth * 20);
		initSound();

		AlertDialog.Builder builder1 = new AlertDialog.Builder(this); 

		// 여기서 부터는 알림창의 속성 설정
		builder1.setTitle("Key setting") // 제목 설정
				.setItems(keys, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int index) {
						// Toast.makeText(getApplicationContext(),
						// keys[index],Toast.LENGTH_SHORT).show();
						switch (index + 1) {
						case 1:// A
							note.setImageResource(R.drawable.key_4);
							key.setText("A");
							break;
						case 2:// Ab
							note.setImageResource(R.drawable.key_9);
							key.setText("Ab");
							break;
						case 3:// B
							note.setImageResource(R.drawable.key_6);
							key.setText("B");
							break;
						case 4:// Bb
							note.setImageResource(R.drawable.key_13);
							key.setText("Bb");
							break;
						case 5:// C
							note.setImageResource(R.drawable.key_1);
							key.setText("C");
							break;
						case 6:// D
							note.setImageResource(R.drawable.key_3);
							key.setText("D");
							break;
						case 7:// Db
							note.setImageResource(R.drawable.key_11);
							key.setText("Db");
							break;
						case 8:// E
							note.setImageResource(R.drawable.key_5);
							key.setText("E");
							break;
						case 9:// Eb
							note.setImageResource(R.drawable.key_10);
							key.setText("Eb");
							break;
						case 10:// F
							note.setImageResource(R.drawable.key_12);
							key.setText("F");
							break;
						case 11:// F#
							note.setImageResource(R.drawable.key_7);
							key.setText("F#");
							break;
						case 12:// G
							note.setImageResource(R.drawable.key_2);
							key.setText("G");
							break;

						default:
							break;

						}
					}
				});
		final AlertDialog dialog1 = builder1.create(); // 알림창 객체 생성
		// 알림창 띄우기

		key.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog1.show();
			}

		});
		AlertDialog.Builder builder2 = new AlertDialog.Builder(this); 

		builder2.setTitle("Meter setting").setItems(meters, new DialogInterface.OnClickListener(){
		public void onClick(DialogInterface dialog, int index) {
				meter.setText(meters[index].toString());
			}
		});
		
		
		
		final AlertDialog dialog2 = builder2.create(); 
		meter.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				dialog2.show();
				
			}
			
		});
		playButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isPlay) {
					stopKey();
				} else if (!isPlay) {
					isPlay = true;
				}
				//s_manager.play(3);
				playKey(keyText.getText().toString());
			}

		});

		stopButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (isPlay == true) {
					isPlay = false;
				}
				stopKey();
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

	private void playKey(String key) {
		int keyNum=1;
		if(key.equals("A"))
		{
			keyNum=4;
		}
		else if(key.equals("Ab"))
		{
			keyNum=9;
		}
		else if(key.equals("B"))
		{
			keyNum=6;
		}
		else if(key.equals("Bb"))
		{
			keyNum=13;
		}
		else if(key.equals("C"))
		{
			keyNum=1;
		}
		else if(key.equals("D"))
		{
			keyNum=3;
		}
		else if(key.equals("Db"))
		{
			keyNum=11;
		}
		else if(key.equals("E"))
		{
			keyNum=5;
		}
		else if(key.equals("Eb"))
		{
			keyNum=10;
		}
		else if(key.equals("F"))
		{
			keyNum=12;
		}
		else if(key.equals("F#"))
		{
			keyNum=7;
		}
		else if(key.equals("G"))
		{
			keyNum=2;
		}

		s_manager.play(keyNum);
	}

	private void stopKey() {
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
			setTextSize(Main.nHeight * 5);
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
			stopKey();
		}
	}

	protected void onStop() {
		super.onStop();
		// Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
		if (isPlay) {
			stopKey();
		}
	}

	protected void onDestory() {
		super.onDestroy();
		// Toast.makeText(this, "onDestory", Toast.LENGTH_SHORT).show();
		if (isPlay) {
			stopKey();
		}
	}
}