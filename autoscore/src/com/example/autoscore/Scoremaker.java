package com.example.autoscore;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Scoremaker extends Activity {

	private ScalableLayout mSL, sSL;
	RelativeLayout scoremakerBG;
	scoreView scoreview;
	AttributeSet attrs;
	Paint paint = new Paint();
	HorizontalScrollView noteScroll;
	LinearLayout linear;
	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	ImageButton metronomeButton, playButton, pauseButton, stopButton,
			recordButton, exportButton, settingButton, backButton, stateButton;
	ImageButton[] notes = new ImageButton[5];
	ImageButton[] rests = new ImageButton[5];
	ImageButton[] signs = new ImageButton[5];
	ImageButton deleteButton, applyButton;
	Dialog settingDialog;
	Typeface note;
	Typeface musical;
	Typeface lassus;
	Boolean recordMode = true;
	CusView cusview;
	int playState;

	final int nothing = 0;
	final int playing = 1;
	final int pausing = 2;
	final int recording = 3;
	final int playpausing = 4;
	// 삭제할것
	SoundManager s_manager;
	TextView test;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.scoremake);

		scoremakerBG = new RelativeLayout(this);
		// scoremakerBG.setBackgroundResource(R.drawable.p3_2_back);
		
		mSL = new ScalableLayout(this, 1280, 800);
		scoremakerBG.setBackgroundResource(R.drawable.p3_2_back);

		scoremakerBG.addView(mSL);

		scoreview = new scoreView(this);
		noteScroll = new HorizontalScrollView(this);
		linear = new LinearLayout(this);

		mSL.addView(noteScroll, 15f, 250f, 1240f, 365f); // 수정하기
		noteScroll.addView(linear, 2000, getWindowManager().getDefaultDisplay().getHeight()/2);	
		linear.addView(scoreview, 2000, getWindowManager().getDefaultDisplay().getHeight()/2);
		//악보 그리는 부분 addView
		
		setting();
		
		setContentView(scoremakerBG);
		
		
		/*test = new TextView(this);
		test.setText("&4========================!========================!========================!");

		//noteScroll.addView(test);
		test.setTypeface(note);
		
		test.setTextColor(Color.BLACK);
		test.setTextSize(getWindowManager().getDefaultDisplay().getHeight() / 10);*/
		

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

		metronomeButton.setBackgroundResource(R.drawable.metro_off);
		playButton.setBackgroundResource(R.drawable.play_on);
		pauseButton.setBackgroundResource(R.drawable.pause_on);
		stopButton.setBackgroundResource(R.drawable.stop_on);
		recordButton.setBackgroundResource(R.drawable.btn_rec_on);
		exportButton.setBackgroundResource(R.drawable.export_on);
		backButton.setBackgroundResource(R.drawable.pre_on);
		settingButton.setBackgroundResource(R.drawable.option);
		stateButton.setBackgroundResource(R.drawable.recode_on);
		deleteButton.setBackgroundResource(R.drawable.delete);
		applyButton.setBackgroundResource(R.drawable.apply);

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
		lassus = Typeface.createFromAsset(getAssets(), "fonts/lassus.ttf");	
		musical = Typeface.createFromAsset(getAssets(), "fonts/Musical.ttf");

		btnSetting();
	}

	void btnSetting() {

		// //////////////////////////////////////////////////

		// s_manager = SoundManager.getInstance();
		// s_manager.init(this);

		// s_manager.addSound(1, R.raw.test);

		// ///////////////////////////////

		stateButtonSetting();
		playButtonSetting();
		stopButtonSetting();
		recordButtonSetting();
		pauseButtonSetting();

		editButtonTouchSetting();
		deleteButtonSetting();
		settingButtonSetting();
		// /////setting Button setting////

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
				if (recordMode == false) {
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

				} else {

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
				if (recordMode == false) {
					stateButton.setBackgroundResource(R.drawable.edit_mode_off);
				} else {

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
					recordButton.setBackgroundResource(R.drawable.btn_rec_on);
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
				if (recordMode == true) {

					Toast.makeText(getBaseContext(), "녹음 NO  수기모드. ", 10)
							.show();

					// recordMode = true;
				} else {

					recordButton.setBackgroundResource(R.drawable.btn_rec_on);
					Toast.makeText(getBaseContext(), "녹음완료. ", 10).show();

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

					pauseButton.setBackgroundResource(R.drawable.pause_off);
					playState = pausing;
				}

			}

		});

		/*
		 * pauseButton.setOnTouchListener(new OnTouchListener() {
		 * 
		 * @Override public boolean onTouch(View v, MotionEvent event) {
		 * pauseButton.setBackgroundResource(R.drawable.pause_off); return
		 * false; }
		 * 
		 * });
		 */

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

			}

		});
	}

	PopupWindow mPopupWindow;

	void settingButtonSetting() {
		/*settingDialog = new Dialog(Scoremaker.this);
		settingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		settingDialog.setContentView(R.layout.scoremaker_setting);
		android.view.WindowManager.LayoutParams params = settingDialog.getWindow().getAttributes();
		params.width = 3000;//LayoutParams.MATCH_PARENT;
		params.height = LayoutParams.MATCH_PARENT;
		settingDialog.getWindow().setAttributes(
				(android.view.WindowManager.LayoutParams) params);
		//settingDialog.getWindow().setGravity(Gravity.BOTTOM);

		sSL = new ScalableLayout(getBaseContext(), 500, 500);
		RelativeLayout a = (RelativeLayout) findViewById(R.id.aaa);
*
		// a.addView(swSL);
		// /////setting Button setting////*/
		settingButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				settingButton.setBackgroundResource(R.drawable.option); // TODO
																		// Auto-generated
																		// meth
																		// //
																		// stub
				
				
				return false;
			}

		});

		settingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "setting", 10).show();

				   View popupView = getLayoutInflater().inflate(R.layout.scoremaker_setting, null);
				   
		            mPopupWindow = new PopupWindow(popupView,
		                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		 
		            /**
		             * PopupWindow Show 메서드
		             * showAsDropDown(anchor, xoff, yoff)
		             * @View anchor : anchor View를 기준으로 PopupWindow 표시 (상,하)
		             * PopupWindow가 최대한 화면에 표시되도록 시스템이 설정해 준다.
		             * xoff, yoff : anchor View를 기준으로 PopupWindow를 표시된것을
		             * 기준으로 xoff는 x좌표, yoff는 y좌표 만큼 이동 한다.
		             * @int xoff : -숫자(화면 왼쪽으로 이동), +숫자(화면 오른쪽으로 이동)
		             * @int yoff : -숫자(화면 위쪽으로 이동), +숫자(화면 아래쪽으로 이동)
		             * achor View 를 덮는 것도 가능
		             * 화면바깥 좌우, 위아래로 이동 가능 (짤린 상태로 표시됨)
		             */
		            mPopupWindow.setAnimationStyle(-1); // 애니메이션 설정(-1:설정안함, 0:설정)
		            //mPopupWindow.showAsDropDown(btn_Popup, 50, 50);
		             
		            /**
		             * update() 메서드를 통해 PopupWindow의 좌우 사이즈, x좌표, y좌표
		             * anchor View까지 재설정 해줄수 있습니다.
		             */
		            
		            mPopupWindow.update(100, 100);
//		          mPopupWindow.update(anchor, xoff, yoff, width, height)(width, height);
				//settingDialog.show();
				//
				/*
				 * noteScroll = new HorizontalScrollView(this);
				 * mSL.addView(noteScroll, 15f, 300f, 1240f, 300f); // 수정하기
				 * 
				 * setting(); test = new TextView(this); test.setText(
				 * " &4====================================================================="
				 * );
				 * 
				 * noteScroll.addView(test); test.setTypeface(note);
				 * test.setTextSize(140);
				 */
				// TODO Auto-generated method stub
			}

		});
	}
	
	protected class scoreView extends View{
		
		public scoreView(Context context) {
			super(context);
		//	this.context = context;			
			//TODO Auto-generated constructor stub			
		}

		@Override
		protected void onDraw(Canvas canvas){		
			String line = "44444444-4444444-";
	//	default 오선지
			Paint paint = new Paint();	
			paint.setColor(Color.BLACK);
			paint.setTextSize((float)(getWindowManager().getDefaultDisplay().getHeight()/2.15));			
			paint.setTypeface(lassus);
			canvas.drawText(line, (float)(getWindowManager().getDefaultDisplay().getHeight()/20.5), (float)(getWindowManager().getDefaultDisplay().getHeight()/3.3) , paint);
			float viewwidth = paint.measureText(line, 0, line.length());
			getLayoutParams().width = (int)viewwidth+50;
			paint.setTypeface(musical);
			paint.setTextSize((float)(getWindowManager().getDefaultDisplay().getHeight()/6));
			canvas.drawText("&", (float)(getWindowManager().getDefaultDisplay().getHeight()/14), (float)(getWindowManager().getDefaultDisplay().getHeight()/3.3), paint);
	//	높은음자리표
			
			paint.setTextSize((float)(getWindowManager().getDefaultDisplay().getHeight()/5.5));
			canvas.drawText("4", (float)(getWindowManager().getDefaultDisplay().getHeight()/3.9), (float)(getWindowManager().getDefaultDisplay().getHeight()/5.55), paint);
			canvas.drawText("4", (float)(getWindowManager().getDefaultDisplay().getHeight()/3.9), (float)(getWindowManager().getDefaultDisplay().getHeight()/3.75), paint);
	//	박자(4/4) ----> 넘어오는 인자 값을 받아서 교체해주면 됨
			
			paint.setTextSize((float)(getWindowManager().getDefaultDisplay().getHeight()/8.55));
			canvas.drawText("#", (float)(getWindowManager().getDefaultDisplay().getHeight()/5), (float)(getWindowManager().getDefaultDisplay().getHeight()/7.25), paint);
	//	높은 레 == 5.6, 높은 파 7.25
	//	key 값에 따라 변경 됨
					
			paint.setTextSize((float)(getWindowManager().getDefaultDisplay().getHeight()/7));
			canvas.drawText("_", (float)(getWindowManager().getDefaultDisplay().getHeight()/2.5), (float)(getWindowManager().getDefaultDisplay().getHeight()/10), paint);
			canvas.drawText("_", (float)(getWindowManager().getDefaultDisplay().getHeight()/2.5), (float)(getWindowManager().getDefaultDisplay().getHeight()/15), paint);
			canvas.drawText("_", (float)(getWindowManager().getDefaultDisplay().getHeight()/2.5), (float)(getWindowManager().getDefaultDisplay().getHeight()/30), paint);
			canvas.drawText("Q", (float)(getWindowManager().getDefaultDisplay().getHeight()/2.41), (float)(getWindowManager().getDefaultDisplay().getHeight()/30), paint);
	//	가장 높은 음 --> 6옥타브 미
			
			paint.setTextSize((float)(getWindowManager().getDefaultDisplay().getHeight()/7));
			canvas.drawText("_", (float)(getWindowManager().getDefaultDisplay().getHeight()/1.5), (float)(getWindowManager().getDefaultDisplay().getHeight()/2.4), paint);
			canvas.drawText("_", (float)(getWindowManager().getDefaultDisplay().getHeight()/1.5), (float)(getWindowManager().getDefaultDisplay().getHeight()/2.65), paint);
			canvas.drawText("_", (float)(getWindowManager().getDefaultDisplay().getHeight()/1.5), (float)(getWindowManager().getDefaultDisplay().getHeight()/2.95), paint);
			canvas.drawText("q", (float)(getWindowManager().getDefaultDisplay().getHeight()/1.48), (float)(getWindowManager().getDefaultDisplay().getHeight()/2.4), paint);
	//	가장 낮은 음 --> 3옥타브 파
			
			//			line = line + "4444444-";
			//			paint.setTextSize((float)(getWindowManager().getDefaultDisplay().getHeight()/2.15));			
			//			paint.setTypeface(lassus);
			//			canvas.drawText(line, (float)(getWindowManager().getDefaultDisplay().getHeight()/20.5), (float)(getWindowManager().getDefaultDisplay().getHeight()/3.3) , paint);
			//			viewwidth = paint.measureText(line, 0, line.length());
			//			getLayoutParams().width = (int)viewwidth+50;
//	오선지 추가시 사용 할 내용들
		}
	}
}
	