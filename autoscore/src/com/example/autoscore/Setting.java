package com.example.autoscore;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelClickedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends ActivityGroup {

	// /////////////////
	SettingData data = new SettingData();
	// ///////////////

	PowerManager.WakeLock mWakeLock;
	TickPlayer tp;

	ImageButton playButton, stopButton;

	TextView set_note, set_tempo1, set_tempo2, key, meter, tempo, type,
			quantizer, triplet;
	TextView keyText, meterText, tempoText, typeText, quantizerText,
			tripletText;
	LinearLayout keyLinear, meterLinear, tempoLinear, typeLinear,
			quantizerLinear, tripletLinear;
	AlertDialog.Builder keyBuilder, typeBuilder;
	AlertDialog keyDialog, typeDialog;
	NumericWheelAdapter tempoNumericWheelAdapter;
	Dialog quantizerDialog, tempoDialog, meterDialog;
	SoundManager s_manager;
	Button nextBtn;
	CharSequence[] keys = { "A", "Ab", "B", "Bb", "C", "D", "Db", "E", "Eb",
			"F", "F#", "G" };
	TextView[] quantizers = new TextView[3];
	TextView[] meters = new TextView[3];
	TextView[] touchText = new TextView[4];
	String[] scores = { "G", "?" };
	int keyCount = 0;
	String selectMeter = "4";
	String selectScore = "G";
	String selectKey1 = "";
	String selectKey2 = "===========";
	boolean mRunning = false;
	OnClickArray handler = new OnClickArray(this);
	WheelView tempoWheel;

	Typeface note1, note2;

	int i = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settting);

		initSound();
		noteSetting();
		btnSetting();

	}

	void btnSetting() {

		for (i = 0; i < touchText.length; i++) {
			touchText[i].setOnTouchListener(handler);
		}
		// ////////////meter//////////////

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

		// ////////////key//////////////////
		keyBuilder = new AlertDialog.Builder(this);
		keyBuilder.setItems(keys, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int index) {

				key.setText(keys[index].toString());
				switch (index + 1) {
				case 1:// A
					selectKey1 = "ÜÙÝ";
					selectKey2 = "========";
					// note.setImageResource(R.drawable.key_4);
					key.setText("A");
					break;
				case 2:// Ab
						// note.setImageResource(R.drawable.key_9);
					selectKey1 = "èëçê";
					selectKey2 = "=======";
					key.setText("Ab");
					break;
				case 3:// B
						// note.setImageResource(R.drawable.key_6);
					selectKey1 = "ÜÙÝÚ×";
					selectKey2 = "======";
					key.setText("B");
					break;
				case 4:// Bb
						// note.setImageResource(R.drawable.key_13);
					selectKey1 = "èë";
					selectKey2 = "=========";
					key.setText("Bb");
					break;
				case 5:// C
						// note.setImageResource(R.drawable.key_1);
					selectKey1 = "";
					selectKey2 = "===========";
					key.setText("C");
					break;
				case 6:// D
						// note.setImageResource(R.drawable.key_3);
					selectKey1 = "ÜÙ";
					selectKey2 = "=========";
					key.setText("D");
					break;
				case 7:// Db
						// note.setImageResource(R.drawable.key_11);
					selectKey1 = "èëçêæ";
					selectKey2 = "======";
					key.setText("Db");
					break;
				case 8:// E
						// note.setImageResource(R.drawable.key_5);
					selectKey1 = "ÜÙÝÚ";
					selectKey2 = "=======";
					key.setText("E");
					break;
				case 9:// Eb
						// note.setImageResource(R.drawable.key_10);
					selectKey1 = "èëç";
					selectKey2 = "========";
					key.setText("Eb");
					break;
				case 10:// F
					// note.setImageResource(R.drawable.key_12);
					selectKey1 = "è";
					selectKey2 = "==========";
					key.setText("F");
					break;
				case 11:// F#
					// note.setImageResource(R.drawable.key_7);
					selectKey1 = "ÜÙÝÚ×Û";
					selectKey2 = "=====";
					key.setText("F#");
					break;
				case 12:// G
					// note.setImageResource(R.drawable.key_2);
					selectKey1 = "Ü";
					selectKey2 = "==========";
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

		// //////////tempo////////////

		tempo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				tempoDialog.show();
				tempo.setTextColor(Color.parseColor("#FF1F1D2A"));

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

		// ///////////////triple//////////////

		playButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mRunning) {
					stopKey();
				} else if (!mRunning) {
					mRunning = true;
				}
				playKey(key.getText().toString());

			}

		});

		stopButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (mRunning == true) {
					mRunning = false;
				}
				stopKey();
			}
		});

		nextBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				data.tempo = tempo.getText().toString();
				data.type = type.getText().toString();
				data.key = key.getText().toString();
				data.triple = triplet.getText().toString();
				data.quanizer = quantizer.getText().toString();
				data.meter = meter.getText().toString();
				/*Toast.makeText(
						getApplicationContext(),
						"tempo=" + data.tempo + "\ntype=" + data.type
								+ "\nkey=" + data.key + "\ntriple="
								+ data.triple + "\nquantizer=" + data.quanizer
								+ "\nmeter=" + data.meter, 10).show();*/
				Intent intent = new Intent(getApplicationContext(), Scoremaker.class);
				startActivity(intent);

			}
		});

	}

	void noteSetting() {

		note2 = Typeface.createFromAsset(getAssets(), "fonts/MusiSync.ttf");
		note1 = Typeface.createFromAsset(getAssets(), "fonts/MusiQwikB.ttf");

		set_note = (TextView) findViewById(R.id.set_note);
		set_tempo1 = (TextView) findViewById(R.id.set_tempo1);
		set_tempo2 = (TextView) findViewById(R.id.set_tempo2);

		key = (TextView) findViewById(R.id.key);
		meter = (TextView) findViewById(R.id.meter);
		tempo = (TextView) findViewById(R.id.tempo);
		type = (TextView) findViewById(R.id.type);
		quantizer = (TextView) findViewById(R.id.quantizer);
		triplet = (TextView) findViewById(R.id.triplet);

		touchText[0] = key;
		touchText[1] = meter;
		touchText[2] = tempo;
		touchText[3] = type;

		keyText = (TextView) findViewById(R.id.key_text);
		meterText = (TextView) findViewById(R.id.meter_text);
		tempoText = (TextView) findViewById(R.id.tempo_text);
		typeText = (TextView) findViewById(R.id.type_text);
		quantizerText = (TextView) findViewById(R.id.quantizer_text);
		tripletText = (TextView) findViewById(R.id.triplet_text);

		keyLinear = (LinearLayout) findViewById(R.id.key_linear);
		meterLinear = (LinearLayout) findViewById(R.id.meter_linear);
		tempoLinear = (LinearLayout) findViewById(R.id.tempo_linear);
		typeLinear = (LinearLayout) findViewById(R.id.type_linear);
		quantizerLinear = (LinearLayout) findViewById(R.id.quantizer_linear);
		tripletLinear = (LinearLayout) findViewById(R.id.triplet_linear);

		playButton = (ImageButton) findViewById(R.id.key_play);
		stopButton = (ImageButton) findViewById(R.id.key_stop);

		nextBtn = (Button) findViewById(R.id.next_button);

		meterSetting();
		quantizeSetting();
		textSetting();
		tempoDialogSetting();

		// 왼쪽 위 오른쪽 아래
		set_tempo1.setPadding(Main.nHeight * 8, Main.nHeight * 5, 0, 0);
		set_tempo2.setPadding(0, Main.nHeight * 5, 0, 0);
		playButton.setPadding(0, 0, Main.nHeight * 2, 0);
		stopButton.setPadding(Main.nHeight * 2, 0, 0, 0);

	}

	void tempoDialogSetting() {
		tempoDialog = new Dialog(Setting.this);
		tempoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		tempoDialog.setContentView(R.layout.tempo_setting);

		tempoWheel = (WheelView) tempoDialog.findViewById(R.id.tempo_wheel);
		tempoNumericWheelAdapter = new NumericWheelAdapter(this, 0, 200);
		tempoNumericWheelAdapter.setTextSize(Main.nHeight * 6);
		tempoWheel.setViewAdapter(tempoNumericWheelAdapter);
		tempoWheel.setCurrentItem(120);
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
	}

	void meterSetting() {
		meterDialog = new Dialog(Setting.this);
		meterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		meterDialog.setContentView(R.layout.meter_setting);

		meters[0] = (TextView) meterDialog.findViewById(R.id.meter_34);
		meters[1] = (TextView) meterDialog.findViewById(R.id.meter_44);
		meters[2] = (TextView) meterDialog.findViewById(R.id.meter_68);

		for (i = 0; i < meters.length; i++) {
			meters[i].setTextSize(Main.nHeight * 15);
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
			quantizers[i].setTextSize(Main.nHeight * 15);
			quantizers[i].setTypeface(note2);
			quantizers[i].setTextColor(Color.parseColor("#FF1F1D2A"));
		}
	}

	void textSetting() {
		set_note.setTextSize(Main.nHeight * 15);
		set_tempo1.setTextSize(Main.nHeight * 4);
		set_tempo2.setTextSize(Main.nHeight * 2);
		keyText.setTextSize(Main.nHeight * 3);
		meterText.setTextSize(Main.nHeight * 3);
		typeText.setTextSize(Main.nHeight * 3);
		tempoText.setTextSize(Main.nHeight * 3);
		quantizerText.setTextSize(Main.nHeight * 3);
		tripletText.setTextSize(Main.nHeight * 3);
		nextBtn.setTextSize(Main.nHeight * 3);

		key.setTextSize(Main.nHeight * 3);
		meter.setTextSize(Main.nHeight * 3);
		type.setTextSize(Main.nHeight * 3);
		tempo.setTextSize(Main.nHeight * 3);
		quantizer.setTextSize(Main.nHeight * 7);
		triplet.setTextSize(Main.nHeight * 7);

		selectMeter = "4";
		selectScore = "&";
		keyCount = 1;
		set_note.setText(selectScore + selectKey1 + selectMeter + selectKey2);
		set_tempo1.setText("q"); // 임시
		set_tempo2.setText("=120");
		nextBtn.setText("Start");
		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/Daum_Regular.ttf");

		set_tempo2.setTypeface(face);
		keyText.setTypeface(face);
		meterText.setTypeface(face);
		typeText.setTypeface(face);
		tempoText.setTypeface(face);
		quantizerText.setTypeface(face);
		tripletText.setTypeface(face);
		nextBtn.setTypeface(face);

		key.setTypeface(face);
		meter.setTypeface(face);
		tempo.setTypeface(face);
		type.setTypeface(face);

		set_note.setTypeface(note1);
		set_tempo1.setTypeface(note2);
		quantizer.setTypeface(note2);
		triplet.setTypeface(note2);

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
			} else {
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

			}
			((TextView) v).setTextColor(Color.parseColor("#FF1F1D2A"));

		}
	}
}
