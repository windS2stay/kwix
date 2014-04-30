package com.example.autoscore;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

public class Set_quantize extends Activity {

	// ////////Layout///////////////// ----->�̰� ������
	// tempoPicker�� ���������ϴ� numberPicker
	// metronumeText metronume�̶�� ���ִ� textView
	// metronomePlayButton = �÷��̹�ư
	// ///////////////////////////////
	int nHeight, nWidth;

	protected void onCreate(Bundle bun) {
		super.onCreate(bun);
		setContentView(R.layout.set_quantize);

		// /////////////////////////////
		// playButton = �÷��� ��ư
		// tempo = numberPicker
		// ///////////////////////////

	} // end

	protected void onResume() {
		super.onResume();
		Display display = ((WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		nHeight = display.getHeight();
		nWidth = display.getWidth();

		final WheelView quantize = (WheelView) findViewById(R.id.quantizeWheel);

		String keys[] = new String[] { "4����ǥ", "8����ǥ", "�ٸ���ǥ" };
		quantize.setViewAdapter(new QuantizeArrayAdapter(this, keys, 1));
		quantize.setCurrentItem(0);
		quantize.setMinimumHeight(nHeight / 100 * 40);
		quantize.setMinimumWidth(nWidth / 100 * 30);
	}

	private class QuantizeWheelAdapter extends NumericWheelAdapter {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public QuantizeWheelAdapter(Context context, int minValue,
				int maxValue, int current) {
			super(context, minValue, maxValue);
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

	/**
	 * Adapter for string based wheel. Highlights the current value.
	 */
	private class QuantizeArrayAdapter extends ArrayWheelAdapter<String> {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public QuantizeArrayAdapter(Context context, String[] items, int current) {
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
}