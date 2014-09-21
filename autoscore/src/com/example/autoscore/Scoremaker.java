package com.example.autoscore;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Scoremaker extends Activity {

	SettingData data = new SettingData();
	private ScalableLayout mSL, sSL;
	RelativeLayout scoremakerBG;
	HorizontalScrollView noteScroll;
	ImageButton metronomeButton, playButton, pauseButton, stopButton,
			recordButton, exportButton, settingButton, backButton, stateButton;
	ImageButton[] notes = new ImageButton[5];
	ImageButton[] rests = new ImageButton[5];
	ImageButton[] signs = new ImageButton[5];
	ImageButton deleteButton, applyButton;
	Dialog settingDialog;
	Typeface note;
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

		note = Typeface.createFromAsset(getAssets(), "fonts/MusiQwikB.ttf");

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
		settingButtonSetting();

		applyButtonSetting();

		// ////////metro Button setting///////////
		metronomeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// applyButton.setBackgroundResource(R.drawable.apply_on);
				metronomeButton.setBackgroundResource(R.drawable.metro_off);/*
																			 * //
																			 * metronomeButton
																			 * .
																			 * setBackgroundResource
																			 * (
																			 * R
																			 * .
																			 * drawable
																			 * .
																			 * metro_off
																			 * )
																			 * ;
																			 * if
																			 * (
																			 * metroOn
																			 * ==
																			 * true
																			 * )
																			 * {
																			 * metroOn
																			 * =
																			 * false
																			 * ;
																			 * metronomeButton
																			 * .
																			 * setBackgroundResource
																			 * (
																			 * R
																			 * .
																			 * drawable
																			 * .
																			 * metro_on
																			 * )
																			 * ;
																			 * }
																			 * else
																			 * {
																			 * metroOn
																			 * =
																			 * true
																			 * ;
																			 * 
																			 * }
																			 */
			}

		});
		metronomeButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// applyButton.setBackgroundResource(R.drawable.apply_off);

				return recordMode;
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
				if (playState != recording) {
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
				stopButton.setBackgroundResource(R.drawable.stop_off);
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

				if (playState == pausing) {
					pauseButton.setBackgroundResource(R.drawable.pause_on);
					playState = nothing;

				} else if (playState == playpausing) {
					pauseButton.setBackgroundResource(R.drawable.pause_on);
					playState = playing;
				} else if (playState != recording) {

					pauseButton.setBackgroundResource(R.drawable.pause_on);
					playState = pausing;
				}

			}

		});

		pauseButton.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				pauseButton.setBackgroundResource(R.drawable.pause_off);
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
		/*
		 * settingDialog = new Dialog(Scoremaker.this);
		 * settingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		 * settingDialog.setContentView(R.layout.scoremaker_setting);
		 * android.view.WindowManager.LayoutParams params =
		 * settingDialog.getWindow().getAttributes(); params.width =
		 * 3000;//LayoutParams.MATCH_PARENT; params.height =
		 * LayoutParams.MATCH_PARENT; settingDialog.getWindow().setAttributes(
		 * (android.view.WindowManager.LayoutParams) params);
		 * //settingDialog.getWindow().setGravity(Gravity.BOTTOM);
		 * 
		 * sSL = new ScalableLayout(getBaseContext(), 500, 500); RelativeLayout
		 * a = (RelativeLayout) findViewById(R.id.aaa);
		 * 
		 * // a.addView(swSL); // /////setting Button setting////
		 */
		settingButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				settingButton.setBackgroundResource(R.drawable.option); // TODO
																		// Auto-generated

				return false;
			}

		});

		settingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "setting", 10).show();

			/*	View popupView = getLayoutInflater().inflate(
						R.layout.scoremaker_setting, null);

				mPopupWindow = new PopupWindow(popupView,
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				mPopupWindow.setAnimationStyle(-1); // 애니메이션 설정(-1:설정안함, 0:설정)
				//mPopupWindow.showAsDropDown(btn_Popup, 50, 50);


				mPopupWindow.update(100, 100);*/

			}

		});
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
}
