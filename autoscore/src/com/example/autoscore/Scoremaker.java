package com.example.autoscore;

import kankan.wheel.widget.OnWheelClickedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Scoremaker extends Activity {

	SettingData data = new SettingData();
	private ScalableLayout mSL, sSL;
	Dialog settingDialog, tempoDialog, quantizeDialog;
	NumericWheelAdapter tempoNumericWheelAdapter;
	WheelView tempoWheel;
	RelativeLayout scoremakerBG;
	HorizontalScrollView noteScroll;
	ImageButton metronomeButton, playButton, pauseButton, stopButton,
			recordButton, exportButton, settingButton, backButton, stateButton;
	int quantizeValue;

	TextView tempo, quantize;
	ImageButton settingCancel, settingCheck;
	TextView quantizers[] = new TextView[3];
	ImageButton[] notes = new ImageButton[5];
	ImageButton[] rests = new ImageButton[5];
	ImageButton[] signs = new ImageButton[5];
	OnClickArray handler = new OnClickArray();
	ImageButton deleteButton, applyButton;
	Boolean recordMode = true, metroOn = true;;

	int playState;

	final int nothing = 0;
	final int playing = 1;
	final int pausing = 2;
	final int recording = 3;
	final int playpausing = 4;
	// 삭제할것
	SoundManager s_manager;
	TextView test;
	Typeface face, note;

	public static final int REQUEST_CODE_ANOTHER = 1001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.scoremake);

		scoremakerBG = new RelativeLayout(this);
		// scoremakerBG.setBackgroundResource(R.drawable.p3_2_back);
		setContentView(scoremakerBG);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		scoremakerBG.setBackgroundColor(Color.parseColor("#FFfcfcec"));

		mSL = new ScalableLayout(this, 1280, 800);
		mSL.setBackgroundResource(R.drawable.p3_2_back);

		scoremakerBG.addView(mSL, params);

		noteScroll = new HorizontalScrollView(this);
		mSL.addView(noteScroll, 15f, 300f, 1240f, 300f); // 수정하기

		setting();
		test = new TextView(this);
		test.setText(" &4=====================================================================");

		noteScroll.addView(test);
		test.setTypeface(note);
		test.setTextSize(getWindowManager().getDefaultDisplay().getHeight() / 5);

	}

	void setting() {

		note = Typeface.createFromAsset(getAssets(), "fonts/MusiSync.ttf");
		face = Typeface.createFromAsset(getAssets(), "fonts/Daum_Regular.ttf");
		metronomeButton = new ImageButton(this);
		playButton = new ImageButton(this);
		pauseButton = new ImageButton(this);
		stopButton = new ImageButton(this);
		recordButton = new ImageButton(this);
		exportButton = new ImageButton(this);
		settingButton = new ImageButton(this);
		backButton = new ImageButton(this);
		stateButton = new ImageButton(this);

		for (int i = 0; i < 5; i++) {
			notes[i] = new ImageButton(this);
			rests[i] = new ImageButton(this);
			signs[i] = new ImageButton(this);
		}
		deleteButton = new ImageButton(this);
		applyButton = new ImageButton(this);

		settingDialog = new Dialog(Scoremaker.this);
		tempoDialog = new Dialog(Scoremaker.this);
		quantizeDialog = new Dialog(Scoremaker.this);
		settingCancel = new ImageButton(this);
		settingCheck = new ImageButton(this);

		metronomeButton.setBackgroundResource(R.drawable.metro_on);
		playButton.setBackgroundResource(R.drawable.play_on);
		pauseButton.setBackgroundResource(R.drawable.pause_on);
		stopButton.setBackgroundResource(R.drawable.stop_on);
		recordButton.setBackgroundResource(R.drawable.btn_rec_on);
		exportButton.setBackgroundResource(R.drawable.export_on);
		backButton.setBackgroundResource(R.drawable.pre_on);
		settingButton.setBackgroundResource(R.drawable.option);
		stateButton.setBackgroundResource(R.drawable.recode_on);
		deleteButton.setBackgroundResource(R.drawable.delete_on);
		applyButton.setBackgroundResource(R.drawable.apply_on);

		notes[0].setBackgroundResource(R.drawable.note_1_non);
		notes[1].setBackgroundResource(R.drawable.note_2_non);
		notes[2].setBackgroundResource(R.drawable.note_4_non);
		notes[3].setBackgroundResource(R.drawable.note_8_non);
		notes[4].setBackgroundResource(R.drawable.note_16_non);

		rests[0].setBackgroundResource(R.drawable.rest_1_non);
		rests[1].setBackgroundResource(R.drawable.rest_2_non);
		rests[2].setBackgroundResource(R.drawable.rest_4_non);
		rests[3].setBackgroundResource(R.drawable.rest_8_non);
		rests[4].setBackgroundResource(R.drawable.rest_16_non);

		signs[0].setBackgroundResource(R.drawable.sign_natural_non);
		signs[1].setBackgroundResource(R.drawable.sign_sharp_non);
		signs[2].setBackgroundResource(R.drawable.sign_flat_non);
		signs[3].setBackgroundResource(R.drawable.sign_dot_non);
		signs[4].setBackgroundResource(R.drawable.sign_triple_non);

		mSL.addView(metronomeButton, 1153f, 44f, 91f, 63f);
		mSL.addView(playButton, 570f, 685f, 111f, 77f);
		mSL.addView(pauseButton, 710f, 685f, 111f, 77f);
		mSL.addView(stopButton, 850f, 685f, 111f, 77f);
		mSL.addView(recordButton, 390f, 685f, 111f, 77f);
		mSL.addView(exportButton, 1140f, 685f, 106f, 77f);
		mSL.addView(backButton, 16f, 685f, 77f, 75f);
		mSL.addView(settingButton, 16f, 15f, 86f, 83f);
		mSL.addView(stateButton, 133f, 685f, 77f, 77f);

		btnSetting();
	}

	void btnSetting() {

		// //////////////////////////////////////////////////

		// s_manager = SoundManager.getInstance();
		// s_manager.init(this);

		// s_manager.addSound(1, R.raw.test);

		// ///////////////////////////////

		Intent receivedIntent = getIntent();
		// 현재 액티비티를 띄우기 위해 생성한 인텐트 안에 넣은 부가 데이터 가져오기
		String receivedString = receivedIntent.getStringExtra("key");
		data.key = receivedString;
		receivedString = receivedIntent.getStringExtra("type");
		data.type = receivedString;
		receivedString = receivedIntent.getStringExtra("meter");
		data.meter = receivedString;

		int receivedInt = receivedIntent.getIntExtra("tempo", 120);
		data.tempo = receivedInt;
		receivedInt = receivedIntent.getIntExtra("quantizer", 120);
		data.quantizer = receivedInt;

		boolean receivedBoolean = receivedIntent
				.getBooleanExtra("triple", true);
		data.triple = receivedBoolean;
		/*
		 * String type; String key; string meter int tempo,int quantizer;
		 * 
		 * boolean triple; ;
		 */

		Toast.makeText(
				getBaseContext(),
				"key = " + data.key + "\ntype = " + data.type + "\nmeter = "
						+ data.meter + "\ntempo = "
						+ Integer.toString(data.tempo) + "\nquantizer = "
						+ Integer.toString(data.quantizer) + "\ntriple = "
						+ Boolean.toString(data.triple), 10).show();

		stateButtonSetting();
		playButtonSetting();
		stopButtonSetting();
		recordButtonSetting();
		pauseButtonSetting();

		editButtonTouchSetting();
		deleteButtonSetting();

		applyButtonSetting();
		settingButtonSetting();
		// ////////metro Button setting///////////
		metronomeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// applyButton.setBackgroundResource(R.drawable.apply_on);
				if (metroOn == true) {
					metronomeButton.setBackgroundResource(R.drawable.metro_off);
					metroOn = false;

				} else if (metroOn == false) {
					metronomeButton.setBackgroundResource(R.drawable.metro_on);
					metroOn = true;
				}

				/*
				 * // metronomeButton . setBackgroundResource ( R . drawable .
				 * metro_off ) ; if ( metroOn == true ) { metroOn = false ;
				 * metronomeButton . setBackgroundResource ( R . drawable .
				 * metro_on ) ; } else { metroOn = true ;
				 * 
				 * }
				 */
			}

		});
		// /////setting Button setting////

		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}

		});

		exportButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				exportButton.setBackgroundResource(R.drawable.export_on);
				Toast.makeText(getBaseContext(), "export", 10).show();
			}

		});

	}

	void stateButtonSetting() {

		stateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (recordMode == false) { // 수기모드 -> 녹음모
					stateButton.setBackgroundResource(R.drawable.recode_on);
					mSL.setBackgroundResource(R.drawable.p3_2_back);
					recordButton.setBackgroundResource(R.drawable.btn_rec_on);

					mSL.addView(metronomeButton, 1153f, 44f, 91f, 63f);

					for (int i = 0; i < 5; i++) {
						mSL.removeView(notes[i]);
						mSL.removeView(rests[i]);
						mSL.removeView(signs[i]);
					}

					mSL.removeView(deleteButton);
					mSL.removeView(applyButton);
					recordMode = true;

				} else {// 녹음모드 -> 수기모드

					stateButton.setBackgroundResource(R.drawable.edit_mode_on);
					mSL.setBackgroundResource(R.drawable.p4_back);
					recordButton.setBackgroundResource(R.drawable.btn_rec_none);
					mSL.removeView(metronomeButton);

					mSL.addView(notes[0], 145f, 60f, 90f, 66f);
					mSL.addView(notes[1], 245f, 60f, 90f, 66f);
					mSL.addView(notes[2], 345f, 60f, 90f, 66f);
					mSL.addView(notes[3], 200f, 135f, 90f, 66f);
					mSL.addView(notes[4], 300f, 135f, 90f, 66f);

					mSL.addView(rests[0], 145f + 345f, 60f, 90f, 66f);
					mSL.addView(rests[1], 245f + 345f, 60f, 90f, 66f);
					mSL.addView(rests[2], 345f + 345f, 60f, 90f, 66f);
					mSL.addView(rests[3], 200f + 345f, 135f, 90f, 66f);
					mSL.addView(rests[4], 300f + 345f, 135f, 90f, 66f);

					mSL.addView(signs[0], 145f + 685f, 60f, 90f, 66f);
					mSL.addView(signs[1], 245f + 685f, 60f, 90f, 66f);
					mSL.addView(signs[2], 345f + 685f, 60f, 90f, 66f);
					mSL.addView(signs[3], 200f + 685f, 135f, 90f, 66f);
					mSL.addView(signs[4], 300f + 685f, 135f, 90f, 66f);

					mSL.addView(applyButton, 1160f, 30f, 74f, 82f);
					mSL.addView(deleteButton, 1160f, 130f, 74f, 82f);
					recordMode = false;

				}

			}

		});
		stateButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (recordMode == false) { // 수기모드
					stateButton.setBackgroundResource(R.drawable.edit_mode_off);
				} else {// 녹음모드

					stateButton.setBackgroundResource(R.drawable.recode_sel);
				}

				return false;
			}

		});
	}

	void playButtonSetting() {
		playButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// s_manager.play(1);

				if (playState == nothing) {
					playState = playing;
					playButton.setBackgroundResource(R.drawable.play_off);
					pauseButton.setBackgroundResource(R.drawable.pause_on);
					stopButton.setBackgroundResource(R.drawable.stop_on);
					recordButton.setBackgroundResource(R.drawable.btn_rec_none);
					// 녹음 비활성화 버튼추가하
				}
			}

		});
	}

	void stopButtonSetting() {
		stopButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// s_manager.stopSound(1);
				if (playState == recording) {
				} else if (playState != recording) {
					playState = nothing;
					stopButton.setBackgroundResource(R.drawable.stop_on);
					playButton.setBackgroundResource(R.drawable.play_on);
					pauseButton.setBackgroundResource(R.drawable.pause_on);
					recordButton.setBackgroundResource(R.drawable.btn_rec_on);
				}

			}

		});

		stopButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (playState == recording) {
				} else {
					stopButton.setBackgroundResource(R.drawable.stop_off);
				}
				return false;
			}

		});
	}

	void recordButtonSetting() {
		recordButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (recordMode == false) { // 녹음모드...?

					Toast.makeText(getBaseContext(), "녹음 NO  수기모드. ", 10)
							.show();

					// recordMode = true;
				} else {

					if (playState == recording) { // 녹음
						playState = nothing;
						recordButton
								.setBackgroundResource(R.drawable.btn_rec_on);
						stopButton.setBackgroundResource(R.drawable.stop_on);
						playButton.setBackgroundResource(R.drawable.play_on);
						pauseButton.setBackgroundResource(R.drawable.pause_on);

						Toast.makeText(getBaseContext(), "녹음  ", 10).show();

					} else { // 녹음시
						playState = recording;
						recordButton
								.setBackgroundResource(R.drawable.btn_rec_off);
						stopButton.setBackgroundResource(R.drawable.stop_none);
						playButton.setBackgroundResource(R.drawable.play_none);
						pauseButton
								.setBackgroundResource(R.drawable.pause_none);

						Toast.makeText(getBaseContext(), "녹음시. ", 10).show();

					}

					// recordMode = false;

				}

			}

		});
	}

	void pauseButtonSetting() {
		pauseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (playState == recording) {
				} else if (playState == pausing) {
					pauseButton.setBackgroundResource(R.drawable.pause_on);
					playState = nothing;

				} else if (playState == playing) {
					pauseButton.setBackgroundResource(R.drawable.pause_off);
				} else if (playState != recording) {

					pauseButton.setBackgroundResource(R.drawable.pause_on);
					playState = pausing;
				}

			}

		});

		pauseButton.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (playState == recording) {
				} else {
					pauseButton.setBackgroundResource(R.drawable.pause_off);
				}
				return false;
			}

		});

	}

	void editButtonTouchSetting() {

		notes[0].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notes[0].setBackgroundResource(R.drawable.note_1_sel);
				notes[1].setBackgroundResource(R.drawable.note_2_non);
				notes[2].setBackgroundResource(R.drawable.note_4_non);
				notes[3].setBackgroundResource(R.drawable.note_8_non);
				notes[4].setBackgroundResource(R.drawable.note_16_non);

				rests[0].setBackgroundResource(R.drawable.rest_1_non);
				rests[1].setBackgroundResource(R.drawable.rest_2_non);
				rests[2].setBackgroundResource(R.drawable.rest_4_non);
				rests[3].setBackgroundResource(R.drawable.rest_8_non);
				rests[4].setBackgroundResource(R.drawable.rest_16_non);

				signs[0].setBackgroundResource(R.drawable.sign_natural_non);
				signs[1].setBackgroundResource(R.drawable.sign_sharp_non);
				signs[2].setBackgroundResource(R.drawable.sign_flat_non);
				signs[3].setBackgroundResource(R.drawable.sign_dot_non);
				signs[4].setBackgroundResource(R.drawable.sign_triple_non);

			}

		});

		notes[1].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notes[0].setBackgroundResource(R.drawable.note_1_non);
				notes[1].setBackgroundResource(R.drawable.note_2_sel);
				notes[2].setBackgroundResource(R.drawable.note_4_non);
				notes[3].setBackgroundResource(R.drawable.note_8_non);
				notes[4].setBackgroundResource(R.drawable.note_16_non);

				rests[0].setBackgroundResource(R.drawable.rest_1_non);
				rests[1].setBackgroundResource(R.drawable.rest_2_non);
				rests[2].setBackgroundResource(R.drawable.rest_4_non);
				rests[3].setBackgroundResource(R.drawable.rest_8_non);
				rests[4].setBackgroundResource(R.drawable.rest_16_non);

				signs[0].setBackgroundResource(R.drawable.sign_natural_non);
				signs[1].setBackgroundResource(R.drawable.sign_sharp_non);
				signs[2].setBackgroundResource(R.drawable.sign_flat_non);
				signs[3].setBackgroundResource(R.drawable.sign_dot_non);
				signs[4].setBackgroundResource(R.drawable.sign_triple_non);

			}

		});

		notes[2].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notes[0].setBackgroundResource(R.drawable.note_1_non);
				notes[1].setBackgroundResource(R.drawable.note_2_non);
				notes[2].setBackgroundResource(R.drawable.note_4_sel);
				notes[3].setBackgroundResource(R.drawable.note_8_non);
				notes[4].setBackgroundResource(R.drawable.note_16_non);

				rests[0].setBackgroundResource(R.drawable.rest_1_non);
				rests[1].setBackgroundResource(R.drawable.rest_2_non);
				rests[2].setBackgroundResource(R.drawable.rest_4_non);
				rests[3].setBackgroundResource(R.drawable.rest_8_non);
				rests[4].setBackgroundResource(R.drawable.rest_16_non);

				signs[0].setBackgroundResource(R.drawable.sign_natural_non);
				signs[1].setBackgroundResource(R.drawable.sign_sharp_non);
				signs[2].setBackgroundResource(R.drawable.sign_flat_non);
				signs[3].setBackgroundResource(R.drawable.sign_dot_non);
				signs[4].setBackgroundResource(R.drawable.sign_triple_non);

			}

		});

		notes[3].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notes[0].setBackgroundResource(R.drawable.note_1_non);
				notes[1].setBackgroundResource(R.drawable.note_2_non);
				notes[2].setBackgroundResource(R.drawable.note_4_non);
				notes[3].setBackgroundResource(R.drawable.note_8_sel);
				notes[4].setBackgroundResource(R.drawable.note_16_non);

				rests[0].setBackgroundResource(R.drawable.rest_1_non);
				rests[1].setBackgroundResource(R.drawable.rest_2_non);
				rests[2].setBackgroundResource(R.drawable.rest_4_non);
				rests[3].setBackgroundResource(R.drawable.rest_8_non);
				rests[4].setBackgroundResource(R.drawable.rest_16_non);

				signs[0].setBackgroundResource(R.drawable.sign_natural_non);
				signs[1].setBackgroundResource(R.drawable.sign_sharp_non);
				signs[2].setBackgroundResource(R.drawable.sign_flat_non);
				signs[3].setBackgroundResource(R.drawable.sign_dot_non);
				signs[4].setBackgroundResource(R.drawable.sign_triple_non);

			}

		});

		notes[4].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notes[0].setBackgroundResource(R.drawable.note_1_non);
				notes[1].setBackgroundResource(R.drawable.note_2_non);
				notes[2].setBackgroundResource(R.drawable.note_4_non);
				notes[3].setBackgroundResource(R.drawable.note_8_non);
				notes[4].setBackgroundResource(R.drawable.note_16_sel);

				rests[0].setBackgroundResource(R.drawable.rest_1_non);
				rests[1].setBackgroundResource(R.drawable.rest_2_non);
				rests[2].setBackgroundResource(R.drawable.rest_4_non);
				rests[3].setBackgroundResource(R.drawable.rest_8_non);
				rests[4].setBackgroundResource(R.drawable.rest_16_non);

				signs[0].setBackgroundResource(R.drawable.sign_natural_non);
				signs[1].setBackgroundResource(R.drawable.sign_sharp_non);
				signs[2].setBackgroundResource(R.drawable.sign_flat_non);
				signs[3].setBackgroundResource(R.drawable.sign_dot_non);
				signs[4].setBackgroundResource(R.drawable.sign_triple_non);

			}

		});

		rests[0].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notes[0].setBackgroundResource(R.drawable.note_1_non);
				notes[1].setBackgroundResource(R.drawable.note_2_non);
				notes[2].setBackgroundResource(R.drawable.note_4_non);
				notes[3].setBackgroundResource(R.drawable.note_8_non);
				notes[4].setBackgroundResource(R.drawable.note_16_non);

				rests[0].setBackgroundResource(R.drawable.rest_1_sel);
				rests[1].setBackgroundResource(R.drawable.rest_2_non);
				rests[2].setBackgroundResource(R.drawable.rest_4_non);
				rests[3].setBackgroundResource(R.drawable.rest_8_non);
				rests[4].setBackgroundResource(R.drawable.rest_16_non);

				signs[0].setBackgroundResource(R.drawable.sign_natural_non);
				signs[1].setBackgroundResource(R.drawable.sign_sharp_non);
				signs[2].setBackgroundResource(R.drawable.sign_flat_non);
				signs[3].setBackgroundResource(R.drawable.sign_dot_non);
				signs[4].setBackgroundResource(R.drawable.sign_triple_non);

			}

		});
		rests[1].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notes[0].setBackgroundResource(R.drawable.note_1_non);
				notes[1].setBackgroundResource(R.drawable.note_2_non);
				notes[2].setBackgroundResource(R.drawable.note_4_non);
				notes[3].setBackgroundResource(R.drawable.note_8_non);
				notes[4].setBackgroundResource(R.drawable.note_16_non);

				rests[0].setBackgroundResource(R.drawable.rest_1_non);
				rests[1].setBackgroundResource(R.drawable.rest_2_sel);
				rests[2].setBackgroundResource(R.drawable.rest_4_non);
				rests[3].setBackgroundResource(R.drawable.rest_8_non);
				rests[4].setBackgroundResource(R.drawable.rest_16_non);

				signs[0].setBackgroundResource(R.drawable.sign_natural_non);
				signs[1].setBackgroundResource(R.drawable.sign_sharp_non);
				signs[2].setBackgroundResource(R.drawable.sign_flat_non);
				signs[3].setBackgroundResource(R.drawable.sign_dot_non);
				signs[4].setBackgroundResource(R.drawable.sign_triple_non);

			}

		});

		rests[2].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notes[0].setBackgroundResource(R.drawable.note_1_non);
				notes[1].setBackgroundResource(R.drawable.note_2_non);
				notes[2].setBackgroundResource(R.drawable.note_4_non);
				notes[3].setBackgroundResource(R.drawable.note_8_non);
				notes[4].setBackgroundResource(R.drawable.note_16_non);

				rests[0].setBackgroundResource(R.drawable.rest_1_non);
				rests[1].setBackgroundResource(R.drawable.rest_2_non);
				rests[2].setBackgroundResource(R.drawable.rest_4_sel);
				rests[3].setBackgroundResource(R.drawable.rest_8_non);
				rests[4].setBackgroundResource(R.drawable.rest_16_non);

				signs[0].setBackgroundResource(R.drawable.sign_natural_non);
				signs[1].setBackgroundResource(R.drawable.sign_sharp_non);
				signs[2].setBackgroundResource(R.drawable.sign_flat_non);
				signs[3].setBackgroundResource(R.drawable.sign_dot_non);
				signs[4].setBackgroundResource(R.drawable.sign_triple_non);

			}

		});

		rests[3].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notes[0].setBackgroundResource(R.drawable.note_1_non);
				notes[1].setBackgroundResource(R.drawable.note_2_non);
				notes[2].setBackgroundResource(R.drawable.note_4_non);
				notes[3].setBackgroundResource(R.drawable.note_8_non);
				notes[4].setBackgroundResource(R.drawable.note_16_non);

				rests[0].setBackgroundResource(R.drawable.rest_1_non);
				rests[1].setBackgroundResource(R.drawable.rest_2_non);
				rests[2].setBackgroundResource(R.drawable.rest_4_non);
				rests[3].setBackgroundResource(R.drawable.rest_8_sel);
				rests[4].setBackgroundResource(R.drawable.rest_16_non);

				signs[0].setBackgroundResource(R.drawable.sign_natural_non);
				signs[1].setBackgroundResource(R.drawable.sign_sharp_non);
				signs[2].setBackgroundResource(R.drawable.sign_flat_non);
				signs[3].setBackgroundResource(R.drawable.sign_dot_non);
				signs[4].setBackgroundResource(R.drawable.sign_triple_non);

			}

		});

		rests[4].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notes[0].setBackgroundResource(R.drawable.note_1_non);
				notes[1].setBackgroundResource(R.drawable.note_2_non);
				notes[2].setBackgroundResource(R.drawable.note_4_non);
				notes[3].setBackgroundResource(R.drawable.note_8_non);
				notes[4].setBackgroundResource(R.drawable.note_16_non);

				rests[0].setBackgroundResource(R.drawable.rest_1_non);
				rests[1].setBackgroundResource(R.drawable.rest_2_non);
				rests[2].setBackgroundResource(R.drawable.rest_4_non);
				rests[3].setBackgroundResource(R.drawable.rest_8_non);
				rests[4].setBackgroundResource(R.drawable.rest_16_sel);

				signs[0].setBackgroundResource(R.drawable.sign_natural_non);
				signs[1].setBackgroundResource(R.drawable.sign_sharp_non);
				signs[2].setBackgroundResource(R.drawable.sign_flat_non);
				signs[3].setBackgroundResource(R.drawable.sign_dot_non);
				signs[4].setBackgroundResource(R.drawable.sign_triple_non);

			}

		});

		signs[0].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notes[0].setBackgroundResource(R.drawable.note_1_non);
				notes[1].setBackgroundResource(R.drawable.note_2_non);
				notes[2].setBackgroundResource(R.drawable.note_4_non);
				notes[3].setBackgroundResource(R.drawable.note_8_non);
				notes[4].setBackgroundResource(R.drawable.note_16_non);

				rests[0].setBackgroundResource(R.drawable.rest_1_non);
				rests[1].setBackgroundResource(R.drawable.rest_2_non);
				rests[2].setBackgroundResource(R.drawable.rest_4_non);
				rests[3].setBackgroundResource(R.drawable.rest_8_non);
				rests[4].setBackgroundResource(R.drawable.rest_16_non);

				signs[0].setBackgroundResource(R.drawable.sign_natural_sel);
				signs[1].setBackgroundResource(R.drawable.sign_sharp_non);
				signs[2].setBackgroundResource(R.drawable.sign_flat_non);
				signs[3].setBackgroundResource(R.drawable.sign_dot_non);
				signs[4].setBackgroundResource(R.drawable.sign_triple_non);

			}

		});

		signs[1].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notes[0].setBackgroundResource(R.drawable.note_1_non);
				notes[1].setBackgroundResource(R.drawable.note_2_non);
				notes[2].setBackgroundResource(R.drawable.note_4_non);
				notes[3].setBackgroundResource(R.drawable.note_8_non);
				notes[4].setBackgroundResource(R.drawable.note_16_non);

				rests[0].setBackgroundResource(R.drawable.rest_1_non);
				rests[1].setBackgroundResource(R.drawable.rest_2_non);
				rests[2].setBackgroundResource(R.drawable.rest_4_non);
				rests[3].setBackgroundResource(R.drawable.rest_8_non);
				rests[4].setBackgroundResource(R.drawable.rest_16_non);

				signs[0].setBackgroundResource(R.drawable.sign_natural_non);
				signs[1].setBackgroundResource(R.drawable.sign_sharp_sel);
				signs[2].setBackgroundResource(R.drawable.sign_flat_non);
				signs[3].setBackgroundResource(R.drawable.sign_dot_non);
				signs[4].setBackgroundResource(R.drawable.sign_triple_non);

			}

		});

		signs[2].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notes[0].setBackgroundResource(R.drawable.note_1_non);
				notes[1].setBackgroundResource(R.drawable.note_2_non);
				notes[2].setBackgroundResource(R.drawable.note_4_non);
				notes[3].setBackgroundResource(R.drawable.note_8_non);
				notes[4].setBackgroundResource(R.drawable.note_16_non);

				rests[0].setBackgroundResource(R.drawable.rest_1_non);
				rests[1].setBackgroundResource(R.drawable.rest_2_non);
				rests[2].setBackgroundResource(R.drawable.rest_4_non);
				rests[3].setBackgroundResource(R.drawable.rest_8_non);
				rests[4].setBackgroundResource(R.drawable.rest_16_non);

				signs[0].setBackgroundResource(R.drawable.sign_natural_non);
				signs[1].setBackgroundResource(R.drawable.sign_sharp_non);
				signs[2].setBackgroundResource(R.drawable.sign_flat_sel);
				signs[3].setBackgroundResource(R.drawable.sign_dot_non);
				signs[4].setBackgroundResource(R.drawable.sign_triple_non);

			}

		});

		signs[3].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notes[0].setBackgroundResource(R.drawable.note_1_non);
				notes[1].setBackgroundResource(R.drawable.note_2_non);
				notes[2].setBackgroundResource(R.drawable.note_4_non);
				notes[3].setBackgroundResource(R.drawable.note_8_non);
				notes[4].setBackgroundResource(R.drawable.note_16_non);

				rests[0].setBackgroundResource(R.drawable.rest_1_non);
				rests[1].setBackgroundResource(R.drawable.rest_2_non);
				rests[2].setBackgroundResource(R.drawable.rest_4_non);
				rests[3].setBackgroundResource(R.drawable.rest_8_non);
				rests[4].setBackgroundResource(R.drawable.rest_16_non);

				signs[0].setBackgroundResource(R.drawable.sign_natural_non);
				signs[1].setBackgroundResource(R.drawable.sign_sharp_non);
				signs[2].setBackgroundResource(R.drawable.sign_flat_non);
				signs[3].setBackgroundResource(R.drawable.sign_dot_sel);
				signs[4].setBackgroundResource(R.drawable.sign_triple_non);

			}

		});

		signs[4].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notes[0].setBackgroundResource(R.drawable.note_1_non);
				notes[1].setBackgroundResource(R.drawable.note_2_non);
				notes[2].setBackgroundResource(R.drawable.note_4_non);
				notes[3].setBackgroundResource(R.drawable.note_8_non);
				notes[4].setBackgroundResource(R.drawable.note_16_non);

				rests[0].setBackgroundResource(R.drawable.rest_1_non);
				rests[1].setBackgroundResource(R.drawable.rest_2_non);
				rests[2].setBackgroundResource(R.drawable.rest_4_non);
				rests[3].setBackgroundResource(R.drawable.rest_8_non);
				rests[4].setBackgroundResource(R.drawable.rest_16_non);

				signs[0].setBackgroundResource(R.drawable.sign_natural_non);
				signs[1].setBackgroundResource(R.drawable.sign_sharp_non);
				signs[2].setBackgroundResource(R.drawable.sign_flat_non);
				signs[3].setBackgroundResource(R.drawable.sign_dot_non);
				signs[4].setBackgroundResource(R.drawable.sign_triple_sel);

			}

		});

	}

	void deleteButtonSetting() {
		deleteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notes[0].setBackgroundResource(R.drawable.note_1_non);
				notes[1].setBackgroundResource(R.drawable.note_2_non);
				notes[2].setBackgroundResource(R.drawable.note_4_non);
				notes[3].setBackgroundResource(R.drawable.note_8_non);
				notes[4].setBackgroundResource(R.drawable.note_16_non);

				rests[0].setBackgroundResource(R.drawable.rest_1_non);
				rests[1].setBackgroundResource(R.drawable.rest_2_non);
				rests[2].setBackgroundResource(R.drawable.rest_4_non);
				rests[3].setBackgroundResource(R.drawable.rest_8_non);
				rests[4].setBackgroundResource(R.drawable.rest_16_non);

				signs[0].setBackgroundResource(R.drawable.sign_natural_non);
				signs[1].setBackgroundResource(R.drawable.sign_sharp_non);
				signs[2].setBackgroundResource(R.drawable.sign_flat_non);
				signs[3].setBackgroundResource(R.drawable.sign_dot_non);
				signs[4].setBackgroundResource(R.drawable.sign_triple_non);
				deleteButton.setBackgroundResource(R.drawable.delete_on);

			}

		});

		deleteButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				deleteButton.setBackgroundResource(R.drawable.delete_off);
				return false;
			}

		});
	}

	PopupWindow mPopupWindow;

	void settingButtonSetting() {

		settingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		settingDialog.setContentView(R.layout.scoremaker_setting);
		settingDialog.getWindow().setGravity(Gravity.LEFT | Gravity.TOP);

		quantizeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		quantizeDialog.setContentView(R.layout.quantize_setting);

		TextView tripleView = (TextView) settingDialog
				.findViewById(R.id.triple2);

		tempo = (TextView) settingDialog.findViewById(R.id.tempo2);
		quantize = (TextView) settingDialog.findViewById(R.id.quantize2);
		LinearLayout layout = (LinearLayout) settingDialog
				.findViewById(R.id.linearLayout1);

		settingCancel = (ImageButton) settingDialog
				.findViewById(R.id.settingCancel);
		settingCheck = (ImageButton) settingDialog
				.findViewById(R.id.settingCheck);

		settingCheck.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				// 예전값 저장하기 아러미ㅏㅇ널미;ㅏ언ㄹ미;
				// Toast.makeText(getBaseContext(), "setting", 10).show();
				settingDialog.dismiss();

			}
		});

		settingCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Toast.makeText(getBaseContext(), "setting", 10).show();
				data.quantizer = quantizeValue;
				settingDialog.dismiss();
			}
		});

		settingCancel.setScaleType(ImageView.ScaleType.FIT_CENTER);
		settingCheck.setScaleType(ImageView.ScaleType.FIT_CENTER);
		layout.setPadding(10, 10, 10, 10);

		tripleView.setTypeface(note);
		tempo.setTypeface(face);
		quantize.setTypeface(note);

		tempo.setText(Integer.toString(data.tempo));

		if (data.quantizer == 4) {
			quantize.setText("q");
			quantizeValue = 4;
		} else if (data.quantizer == 16) {
			quantize.setText("s");
			quantizeValue = 16;
		} else if (data.quantizer == 8) {
			quantize.setText("e");
			quantizeValue = 8;
		}

		settingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				settingDialog.show();
			}

		});

		tempoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		tempoDialog.setContentView(R.layout.tempo_setting);

		tempoWheel = (WheelView) tempoDialog.findViewById(R.id.tempo_wheel);
		tempoNumericWheelAdapter = new NumericWheelAdapter(this, 60, 200);
		tempoNumericWheelAdapter.setTextSize(100);
		tempoWheel.setViewAdapter(tempoNumericWheelAdapter);
		tempoWheel.setCurrentItem(60);

		// 템포 수정//

		tempoWheel.addClickingListener(new OnWheelClickedListener() {

			@Override
			public void onItemClicked(WheelView wheel, int itemIndex) {

				// tempoNumericWheelAdapter.setTextColor(Color.parseColor("#FFbb4738"));
				tempo.setText(Integer.toString(tempoWheel.getCurrentItem()));
				// Toast.makeText(getBaseContext(),tempo.toString(),10).show();
				data.tempo = Integer.parseInt(tempo.getText().toString());
				tempoDialog.dismiss();
			}

		});
		quantizers[0] = (TextView) quantizeDialog.findViewById(R.id.quantize_4);
		quantizers[1] = (TextView) quantizeDialog.findViewById(R.id.quantize_8);
		quantizers[2] = (TextView) quantizeDialog
				.findViewById(R.id.quantize_16);

		for (int i = 0; i < quantizers.length; i++) {
			quantizers[i].setTypeface(note);
			quantizers[i].setTextSize(100);
			quantizers[i].setTextColor(Color.parseColor("#FF000000"));
			quantizers[i].setOnClickListener(handler);
			quantizers[i].setOnTouchListener(handler);
		}
		quantize.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (data.quantizer == 4) {
					quantize.setText("q");
					quantizeValue = 4;
				} else if (data.quantizer == 16) {
					quantize.setText("s");
					quantizeValue = 16;
				} else if (data.quantizer == 8) {
					quantize.setText("e");
					quantizeValue = 8;
				}
				quantizeDialog.show();

			}
		});
		tempo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tempoDialog.show();
			}

		});

		settingDialog.setCanceledOnTouchOutside(true);
		quantizeDialog.setCanceledOnTouchOutside(true);

	}

	void applyButtonSetting() {

		applyButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				applyButton.setBackgroundResource(R.drawable.apply_on);
			}

		});
		applyButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				applyButton.setBackgroundResource(R.drawable.apply_off);

				return recordMode;
			}

		});

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_ANOTHER) {
			Toast.makeText(getBaseContext(), "ㅁ", 10).show();

		} else {
			Toast.makeText(getBaseContext(), "", 10).show();
		}
	}

	private class OnClickArray implements OnClickListener, OnTouchListener {

		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// TODO Auto-generated method stub
			((TextView) arg0).setTextColor(Color.parseColor("#FFbb4738"));
			return false;
		}

		@Override
		public void onClick(View v) {
			if (v == quantizers[0] | v == quantizers[1] | v == quantizers[2]) {
				if (v == quantizers[0]) {// 4
					quantize.setText("q");
					data.quantizer = 4;
				} else if (v == quantizers[1]) {// 8
					quantize.setText("e");
					data.quantizer = 8;
				} else if (v == quantizers[2]) {// 16
					quantize.setText("s");
					data.quantizer = 16;
				}
				((TextView) v).setTextColor(Color.parseColor("#FF000000"));
				quantizeDialog.dismiss();
			}
		}

	}
}
