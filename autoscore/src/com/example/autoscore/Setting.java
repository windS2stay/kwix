package com.example.autoscore;

import kankan.wheel.widget.OnWheelClickedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
//재생 수정 ,type수정 , tempo 휠수
public class Setting extends Activity {

	private static final String DebugTag = "ScalableLayout_TestAndroid";

	private static void log(String pLog) {
		Log.e(DebugTag, "MainActivity] " + pLog);
	}

	private ScalableLayout mSL; 
	RelativeLayout setttingBG;

	///////////////////
	SettingData data = new SettingData();
	/////////////////

	PowerManager.WakeLock mWakeLock;
	TickPlayer tp;

	Button playButton, stopButton;
	Button nextBtn;

	TextView set_note, set_tempo1, set_tempo2, key, meter, tempo, type,
			quantizer, triplet;
	TextView keyText, meterText, tempoText, typeText, quantizerText,
			tripletText;
	AlertDialog.Builder keyBuilder, typeBuilder;
	AlertDialog keyDialog;
	NumericWheelAdapter tempoNumericWheelAdapter;
	Dialog quantizerDialog, tempoDialog, meterDialog, typeDialog;
	CheckBox tripleCheck;
	SoundManager s_manager;

	CharSequence[] keys = { "A", "Ab", "B", "Bb", "C", "D", "Db", "E", "Eb",
			"F", "F#", "G" };
	TextView[] quantizers = new TextView[3];
	TextView[] meters = new TextView[3];
	TextView[] touchText = new TextView[5];
	TextView[] types = new TextView[4];
	String[] scores = { "G", "?" };
	int keyCount = 0;
	String selectMeter = "4";
	String selectScore = "G";
	String selectKey1 = "";
	String selectKey2 = "r==========";
	boolean mRunning = false;
	OnClickArray handler = new OnClickArray(this);
	WheelView tempoWheel;
	Typeface note1, note2;

	int i = 0;

	public void onCreate(Bundle savedInstanceState) {
		// super.onCreate(savedInstanceState);
		// setContentView(R.layout.settting);

		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		ScalableLayout.setLoggable(DebugTag);

		initSound(); // sound 
		noteSetting(); // btn location,color,size, dialog... etc setting
		btnSetting(); // btn event setting

	}

	void btnSetting() {
	/////Text Click color change setting//////
		for (i = 0; i < touchText.length; i++) {
			touchText[i].setOnTouchListener(handler); //c
		}

		/////nextBtn setting//////
		mSL.addView(nextBtn, 530f, 330f, 117f, 38f);
		nextBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Setting.this, Scoremaker.class);
				startActivity(intent);

			}

		});

		////// playButton setting ///////

		playButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mRunning) {
					//stopKey();
				} else if (!mRunning) {
					mRunning = true;
					playButton.setBackgroundResource(R.drawable.play_on);
					stopButton.setBackgroundResource(R.drawable.stop_off);
					playKey(key.getText().toString());
				}
			}

		});
		
		///// stopButton setting////
		
		stopButton.setOnTouchListener(new Button.OnTouchListener(){

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				stopButton.setBackgroundResource(R.drawable.stop_on);
				return false;
			}
			
		});
		stopButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (mRunning == true) {
					playButton.setBackgroundResource(R.drawable.play_off);
					stopButton.setBackgroundResource(R.drawable.stop_off);
					mRunning = false;
					stopKey();
				}
				
			}
		});

		//////////////meter event setting//////////////

		meter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				meterDialog.show();
				meter.setTextColor(Color.parseColor("#FF1F1D2A"));
			}
		});
		for (i = 0; i < meters.length; i++) {
			meters[i].setOnClickListener(handler);
			meters[i].setOnTouchListener(handler);
		}

		///////////////key event setting//////////////////
		keyBuilder = new AlertDialog.Builder(this);
		keyBuilder.setItems(keys, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int index) {

				key.setText(keys[index].toString());
				switch (index + 1) {
				case 1:// A
					selectKey1 = "ÜÙÝ";
					selectKey2 = "w=======";
					// note.setImageResource(R.drawable.key_4);
					key.setText("A");
					break;
				case 2:// Ab
						// note.setImageResource(R.drawable.key_9);
					selectKey1 = "èëçê";
					selectKey2 = "w======";
					key.setText("Ab");
					break;
				case 3:// B
						// note.setImageResource(R.drawable.key_6);
					selectKey1 = "ÜÙÝÚ×";
					selectKey2 = "q=====";
					key.setText("B");
					break;
				case 4:// Bb
						// note.setImageResource(R.drawable.key_13);
					selectKey1 = "èë";
					selectKey2 = "q========";
					key.setText("Bb");
					break;
				case 5:// C
						// note.setImageResource(R.drawable.key_1);
					selectKey1 = "";
					selectKey2 = "r==========";
					key.setText("C");
					break;
				case 6:// D
						// note.setImageResource(R.drawable.key_3);
					selectKey1 = "ÜÙ";
					selectKey2 = "s========";
					key.setText("D");
					break;
				case 7:// Db
						// note.setImageResource(R.drawable.key_11);
					selectKey1 = "èëçêæ";
					selectKey2 = "s=====";
					key.setText("Db");
					break;
				case 8:// E
						// note.setImageResource(R.drawable.key_5);
					selectKey1 = "ÜÙÝÚ";
					selectKey2 = "t======";
					key.setText("E");
					break;
				case 9:// Eb
						// note.setImageResource(R.drawable.key_10);
					selectKey1 = "èëç";
					selectKey2 = "t=======";
					key.setText("Eb");
					break;
				case 10:// F
					// note.setImageResource(R.drawable.key_12);
					selectKey1 = "è";
					selectKey2 = "u=========";
					key.setText("F");
					break;
				case 11:// F#
					// note.setImageResource(R.drawable.key_7);
					selectKey1 = "ÜÙÝÚ×Û";
					selectKey2 = "u====";
					key.setText("F#");
					break;
				case 12:// G
					// note.setImageResource(R.drawable.key_2);
					selectKey1 = "Ü";
					selectKey2 = "v=========";
					key.setText("G");
					break;

				default:
					break;

				}
				set_note.setText(selectScore + selectKey1 + selectMeter
						+ selectKey2);

			}
		});
		keyDialog = keyBuilder.create();
		key.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				keyDialog.show();
				key.setTextColor(Color.parseColor("#FF1F1D2A"));
			}
		});

		////////////tempo event setting////////////

		tempo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				tempoDialog.show();
				tempo.setTextColor(Color.parseColor("#FF1F1D2A"));

			}
		});
		// 타입정
		// ////type/////////
		/*
		 * type.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) { typeDialog.show();
		 * type.setTextColor(Color.parseColor("#FF1F1D2A"));
		 * 
		 * } }); for (i = 0; i < types.length; i++) {
		 * types[i].setOnClickListener(handler);
		 * types[i].setOnTouchListener(handler); }
		 */
		///////////quantizer event setting///////////////

		quantizer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				quantizerDialog.show();
			}
		});

		for (i = 0; i < quantizers.length; i++) {
			quantizers[i].setOnClickListener(handler);
			quantizers[i].setOnTouchListener(handler);
		}

	}

	void noteSetting() {

		setttingBG = new RelativeLayout(this);
		setttingBG.setBackgroundResource(R.drawable.background2);
		setContentView(setttingBG);

		mSL = new ScalableLayout(this, 640, 400); // 화면 사이즈 640*400
		setttingBG.addView(mSL);

		set_note = new TextView(this); // 악보 Textveiw
		set_tempo1 = new TextView(this); // 악보 바로위에 음표
		set_tempo2 = new TextView(this); // 뭐지 숫
		key = new TextView(this);
		meter = new TextView(this);
		tempo = new TextView(this);
		type = new TextView(this);
		quantizer = new TextView(this);
		triplet = new TextView(this);

		keyText = new TextView(this);
		meterText = new TextView(this);
		tempoText = new TextView(this);
		typeText = new TextView(this);
		quantizerText = new TextView(this);
		tripletText = new TextView(this);
		tripleCheck = new CheckBox(this);

		playButton = new Button(this);
		stopButton = new Button(this);
		nextBtn = new Button(this);

		touchText[0] = key;
		touchText[1] = meter;
		touchText[2] = tempo;
		touchText[3] = quantizer;
		touchText[4] = type;

		textSetting();
		meterSetting();
		quantizeSetting();
		tempoDialogSetting();
		typeDialogSetting();

	}

	void typeDialogSetting() {
		/*
		 * typeDialog = new Dialog(Setting.this);
		 * typeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		 * typeDialog.setContentView(R.layout.aaa);
		 * 
		 * Typeface note3; note3 = Typeface.createFromAsset(getAssets(),
		 * "fonts/flaticon.ttf");
		 * 
		 * types[0] = (TextView) typeDialog.findViewById(R.id.male_icon);
		 * types[1] = (TextView) typeDialog.findViewById(R.id.female_icon);
		 * types[2] = (TextView) typeDialog.findViewById(R.id.piano_icon);
		 * types[3] = (TextView)
		 * typeDialog.findViewById(R.id.chordophones_icon);
		 * 
		 * for (i = 0; i < types.length; i++) {
		 * types[i].setTextSize(Main.nHeight * 15); types[i].setTypeface(note3);
		 * }
		 */
	}

	void tempoDialogSetting() {

		tempoDialog = new Dialog(Setting.this);
		tempoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		tempoDialog.setContentView(R.layout.tempo_setting);

		tempoWheel = (WheelView) tempoDialog.findViewById(R.id.tempo_wheel);
		tempoNumericWheelAdapter = new NumericWheelAdapter(this, 0, 200);
		tempoNumericWheelAdapter.setTextSize(100);
		tempoWheel.setViewAdapter(tempoNumericWheelAdapter);
		tempoWheel.setCurrentItem(120);

		// 템포 수정//

		tempoWheel.addClickingListener(new OnWheelClickedListener() {

			@Override
			public void onItemClicked(WheelView wheel, int itemIndex) {

				// tempoNumericWheelAdapter.setTextColor(Color.parseColor("#FFbb4738"));
				tempo.setText(Integer.toString(tempoWheel.getCurrentItem()));
				set_tempo2.setText("="
						+ Integer.toString(tempoWheel.getCurrentItem()));
				tempoDialog.dismiss();
			}

		});

		// /////////quantizer///////////////

		quantizer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				quantizerDialog.show();
			}
		});

		for (i = 0; i < quantizers.length; i++) {
			quantizers[i].setOnClickListener(handler);
			quantizers[i].setOnTouchListener(handler);
		}

	}

	void meterSetting() {
		meterDialog = new Dialog(Setting.this);
		meterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		meterDialog.setContentView(R.layout.meter_setting);

		meters[0] = (TextView) meterDialog.findViewById(R.id.meter_34);
		meters[1] = (TextView) meterDialog.findViewById(R.id.meter_44);
		meters[2] = (TextView) meterDialog.findViewById(R.id.meter_68);

		for (i = 0; i < meters.length; i++) {
			meters[i].setTextSize(100);
			meters[i].setTypeface(note2);
			meters[i].setTextColor(Color.parseColor("#FF1F1D2A"));
		}
	}

	void quantizeSetting() {
		quantizerDialog = new Dialog(Setting.this);
		quantizerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		quantizerDialog.setContentView(R.layout.quantize_setting);

		quantizers[0] = (TextView) quantizerDialog
				.findViewById(R.id.quantize_16);
		quantizers[1] = (TextView) quantizerDialog
				.findViewById(R.id.quantize_8);
		quantizers[2] = (TextView) quantizerDialog
				.findViewById(R.id.quantize_4);

		for (i = 0; i < quantizers.length; i++) {
			quantizers[i].setTextSize(100);
			quantizers[i].setTypeface(note2);
			quantizers[i].setTextColor(Color.parseColor("#FF1F1D2A"));
		}
	}

	void textSetting() {

		// 글씨
		note2 = Typeface.createFromAsset(getAssets(), "fonts/MusiSync.ttf");
		note1 = Typeface.createFromAsset(getAssets(), "fonts/MusiQwikB.ttf");
		selectMeter = "4";//
		selectScore = "&";// 높은 음자리
		keyCount = 1;
		set_note.setText(selectScore + selectKey1 + selectMeter + selectKey2);
		set_tempo1.setText("q"); // 4=사분음
		set_tempo2.setText("= 120");

		key.setText("C");
		meter.setText("4/4");
		tempo.setText("120");
		quantizer.setText("e"); // 8분음표
		type.setText("male");
		triplet.setText("t"); // 트리플음표

		keyText.setText("key");
		meterText.setText("meter");
		tempoText.setText("tempo");
		typeText.setText("type");
		quantizerText.setText("quantize");
		tripletText.setText("triple");

		nextBtn.setText("start");
		nextBtn.setBackgroundResource(R.drawable.start_btn);
		playButton.setBackgroundResource(R.drawable.play_off);
		stopButton.setBackgroundResource(R.drawable.stop_off);

		mSL.addView(playButton, 130f, 250f, 56f, 39f);
		mSL.addView(stopButton, 200f, 250f, 56f, 39f);

		mSL.addView(set_note, 60f, 100f, 265f, 150f); // 왼쪽 아래 넓이 높
		mSL.addView(set_tempo1, 90f, 60f, 100f, 50f); // 4분음
		mSL.addView(set_tempo2, 110f, 80f, 100f, 30f);

		// 위치지
		mSL.addView(key, 530f, 50f, 100f, 50f);
		mSL.addView(meter, 530f, 95f, 100f, 50f);
		mSL.addView(tempo, 530f, 140f, 100f, 50f);
		mSL.addView(quantizer, 540f, 170f, 100f, 70f);
		mSL.addView(type, 530f, 230f, 100f, 50f);
		mSL.addView(triplet, 480f, 260f, 100f, 70f);

		mSL.addView(keyText, 400f, 50f, 100f, 50f);
		mSL.addView(meterText, 400f, 95f, 100f, 50f);
		mSL.addView(tempoText, 400f, 140f, 100f, 50f);
		mSL.addView(quantizerText, 400f, 185f, 150f, 50f);
		mSL.addView(typeText, 400f, 230f, 100f, 50f);
		mSL.addView(tripletText, 400f, 275f, 100f, 50f);
		mSL.addView(tripleCheck,545f,275f,25f,25f);

		// 사이즈 조
		mSL.setScale_TextSize(set_note, 70f);// 큰악보
		mSL.setScale_TextSize(set_tempo1, 30f);// 악보위의 음
		mSL.setScale_TextSize(set_tempo2, 15f);
		mSL.setScale_TextSize(key, 22f);
		mSL.setScale_TextSize(meter, 22f);
		mSL.setScale_TextSize(tempo, 22f);
		mSL.setScale_TextSize(quantizer, 40f);
		mSL.setScale_TextSize(type, 22f);
		mSL.setScale_TextSize(triplet, 40f);

		mSL.setScale_TextSize(keyText, 22f);
		mSL.setScale_TextSize(meterText, 22f);
		mSL.setScale_TextSize(tempoText, 22f);
		mSL.setScale_TextSize(quantizerText, 22f);
		mSL.setScale_TextSize(typeText, 22f);
		mSL.setScale_TextSize(tripletText, 22f);

		mSL.setScale_TextSize(nextBtn, 2f);

		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/Daum_Regular.ttf");

		set_tempo1.setTextColor(Color.parseColor("#FF1F1D2A"));
		set_tempo2.setTextColor(Color.parseColor("#FF1F1D2A"));
		key.setTextColor(Color.parseColor("#FF1F1D2A"));
		meter.setTextColor(Color.parseColor("#FF1F1D2A"));
		tempo.setTextColor(Color.parseColor("#FF1F1D2A"));
		type.setTextColor(Color.parseColor("#FF1F1D2A"));
		quantizer.setTextColor(Color.parseColor("#FF1F1D2A"));
		triplet.setTextColor(Color.parseColor("#FF1F1D2A"));

		keyText.setTextColor(Color.parseColor("#FF1F1D2A"));
		meterText.setTextColor(Color.parseColor("#FF1F1D2A"));
		tempoText.setTextColor(Color.parseColor("#FF1F1D2A"));
		typeText.setTextColor(Color.parseColor("#FF1F1D2A"));
		quantizerText.setTextColor(Color.parseColor("#FF1F1D2A"));
		tripletText.setTextColor(Color.parseColor("#FF1F1D2A"));
		set_note.setTextColor(Color.parseColor("#FF1F1D2A"));

		nextBtn.setTextColor(Color.parseColor("#FF1F1D2A"));

		set_tempo2.setTypeface(face);
		keyText.setTypeface(face);
		meterText.setTypeface(face);
		typeText.setTypeface(face);
		tempoText.setTypeface(face);
		quantizerText.setTypeface(face);
		tripletText.setTypeface(face);

		key.setTypeface(face);
		meter.setTypeface(face);
		tempo.setTypeface(face);
		type.setTypeface(face);
		nextBtn.setTypeface(face);

		set_note.setTypeface(note1);
		set_tempo1.setTypeface(note2);
		quantizer.setTypeface(note2);
		triplet.setTypeface(note2);

	}

	private void initSound() {
		s_manager = SoundManager.getInstance();
		// ��ъ�대�� 留ㅻ����� 珥�湲고��
		s_manager.init(this);
		// ��ъ�대�� ��깅��
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

		// /quantize setting//
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
				"MetronomeLock");
		tp = new TickPlayer(this);

	}

	private void playKey(String key) {
		int keyNum = 1;
		if (key.equals("A")) {
			keyNum = 4;
		} else if (key.equals("Ab")) {
			keyNum = 9;
		} else if (key.equals("B")) {
			keyNum = 6;
		} else if (key.equals("Bb")) {
			keyNum = 13;
		} else if (key.equals("C")) {
			keyNum = 1;
		} else if (key.equals("D")) {
			keyNum = 3;
		} else if (key.equals("Db")) {
			keyNum = 11;
		} else if (key.equals("E")) {
			keyNum = 5;
		} else if (key.equals("Eb")) {
			keyNum = 10;
		} else if (key.equals("F")) {
			keyNum = 12;
		} else if (key.equals("F#")) {
			keyNum = 7;
		} else if (key.equals("G")) {
			keyNum = 2;
		}

		s_manager.play(keyNum);
		mWakeLock.acquire();
		tp.onStart(-1, Integer.parseInt(tempo.getText().toString()));

		// tempo.setText(Integer.toString(keyNum));
	}

	private void stopKey() {
		for (int i = 1; i < 13; i++) {
			s_manager.stopSound(i);
		}
		mWakeLock.release();
		tp.onStop();
	}

	private class OnClickArray implements OnClickListener, OnTouchListener {
		private Setting activity;

		public OnClickArray(Setting activity) {
			this.activity = activity;
		}

		public boolean onTouch(View v, MotionEvent arg1) {
			((TextView) v).setTextColor(Color.parseColor("#FFbb4738"));
			return false;
		}

		@Override
		public void onClick(View v) {
			if (v == quantizers[0] || v == quantizers[1] || v == quantizers[2]) {
				quantizer.setText(((TextView) v).getText());
				quantizerDialog.dismiss();
			} else if (v == meters[0] || v == meters[1] || v == meters[2]) {
				if (v == meters[0]) {
					selectMeter = "3";
					meter.setText("3/4");
				} else if (v == meters[1]) {
					selectMeter = "4";
					meter.setText("4/4");
				} else if (v == meters[2]) {
					meter.setText("6/8");
					selectMeter = "6";
				}
				set_note.setText(selectScore + selectKey1 + selectMeter
						+ selectKey2);
				meterDialog.dismiss();

			} else if (v == types[0] || v == types[1] || v == types[2]
					|| v == types[3]) {
				typeDialog.dismiss();
			}
			((TextView) v).setTextColor(Color.parseColor("#FF1F1D2A"));
		}
	}
}
