package com.example.autoscore;

import android.app.Activity;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class HowToUse extends Activity {

	int progress = 1;
	ImageView useExplain;
	RelativeLayout HTUBG;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureScanner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		HTUBG = new RelativeLayout(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		useExplain = new ImageView(this);
		useExplain.setBackgroundResource(R.drawable.p2_1);
		useExplain.setScaleType(ImageView.ScaleType.FIT_CENTER);
		HTUBG.addView(useExplain);
		// fit_center

		setContentView(HTUBG);
		gestureScanner = new GestureDetector(this, mGestureListener);

	}

	public boolean onTouchEvent(MotionEvent event) {

		return gestureScanner.onTouchEvent(event); // event 전달
	}

	OnGestureListener mGestureListener = new OnGestureListener() {

		public boolean onSingleTapUp(MotionEvent e) {
			//Toast.makeText(getBaseContext(), "onSingleTapUp",
					//Toast.LENGTH_SHORT).show();
			return false;
		}

		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub

		}

		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			return false;
		}

		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub

		}

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {

			if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
				return false;
			// right to left swipe
			if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {
				if (progress == 1) {
					useExplain.setBackgroundResource(R.drawable.p2_2);
				} else if (progress == 2) {
					useExplain.setBackgroundResource(R.drawable.p2_3);

				} else if (progress == 3) {
					useExplain.setBackgroundResource(R.drawable.p2_4);

				} else if (progress == 4) {
					Toast.makeText(getBaseContext(), "3번째라능", 10).show();
					useExplain.setBackgroundResource(R.drawable.p3_1);

				} else if (progress == 5) {
					useExplain.setBackgroundResource(R.drawable.p3_2);

				} else if (progress == 6) {
					useExplain.setBackgroundResource(R.drawable.p3_3);

				} else if (progress == 7) {
					useExplain.setBackgroundResource(R.drawable.p3_4);

				} else if (progress == 8) {
					useExplain.setBackgroundResource(R.drawable.p3_5);

				} else if (progress == 9) {
					useExplain.setBackgroundResource(R.drawable.p3_6);

				} else if (progress == 10) {
					useExplain.setBackgroundResource(R.drawable.p3_7);

				} else if (progress == 11) {
					useExplain.setBackgroundResource(R.drawable.p3_8);

				} else if (progress == 12) {
					Toast.makeText(getBaseContext(), "4번째라능", 10).show();
					useExplain.setBackgroundResource(R.drawable.p4_1);

				} else if (progress == 13) {
					useExplain.setBackgroundResource(R.drawable.p4_2);

				} else if (progress == 14) {
					useExplain.setBackgroundResource(R.drawable.p4_3);

				} else if (progress == 15) {
					useExplain.setBackgroundResource(R.drawable.p4_4);

				} else {

				}
				useExplain.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				progress--;

			} else if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
				// Toast.makeText(getBaseContext(), "Right Swipe",
				// Toast.LENGTH_SHORT).show();
				if (progress == 1) {
					useExplain.setBackgroundResource(R.drawable.p2_2);

				} else if (progress == 2) {
					useExplain.setBackgroundResource(R.drawable.p2_3);

				} else if (progress == 3) {
					useExplain.setBackgroundResource(R.drawable.p2_4);

				} else if (progress == 4) {
					Toast.makeText(getBaseContext(), "3번째라능", 10).show();
					useExplain.setBackgroundResource(R.drawable.p3_1);

				} else if (progress == 5) {
					useExplain.setBackgroundResource(R.drawable.p3_2);

				} else if (progress == 6) {
					useExplain.setBackgroundResource(R.drawable.p3_3);

				} else if (progress == 7) {
					useExplain.setBackgroundResource(R.drawable.p3_4);

				} else if (progress == 8) {
					useExplain.setBackgroundResource(R.drawable.p3_5);

				} else if (progress == 9) {
					useExplain.setBackgroundResource(R.drawable.p3_6);

				} else if (progress == 10) {
					useExplain.setBackgroundResource(R.drawable.p3_7);

				} else if (progress == 11) {
					useExplain.setBackgroundResource(R.drawable.p3_8);

				} else if (progress == 12) {
					Toast.makeText(getBaseContext(), "4번째라능", 10).show();
					useExplain.setBackgroundResource(R.drawable.p4_1);

				} else if (progress == 13) {
					useExplain.setBackgroundResource(R.drawable.p4_2);

				} else if (progress == 14) {
					useExplain.setBackgroundResource(R.drawable.p4_3);

				} else if (progress == 15) {
					useExplain.setBackgroundResource(R.drawable.p4_4);

				} else {
					finish();
				}
				useExplain.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				progress++;
			}
			return false;
		}

		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		public void onGesture(GestureOverlayView overlay, MotionEvent event) {
			// TODO Auto-generated method stub

		}

		public void onGestureCancelled(GestureOverlayView overlay,
				MotionEvent event) {
			// TODO Auto-generated method stub

		}

		public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
			// TODO Auto-generated method stub

		}

		public void onGestureStarted(GestureOverlayView overlay,
				MotionEvent event) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
