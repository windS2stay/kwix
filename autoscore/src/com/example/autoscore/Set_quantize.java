package com.example.autoscore;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class Set_quantize extends Activity {

	// ////////Layout///////////////// ----->이거 수정하
	// tempoPicker는 숫자조절하는 numberPicker
	// metronumeText metronume이라고 써있는 textView
	// metronomePlayButton = 플레이버튼
	// ///////////////////////////////
	WheelView quantize;
	CheckBox triple;
	ImageView tripleImage;
	protected void onCreate(Bundle bun) {
		super.onCreate(bun);
		setContentView(R.layout.set_quantize);

		// /////////////////////////////
		// playButton = 플레이 버튼
		// tempo = numberPicker
		// ///////////////////////////

	} // end

	protected void onResume() {
		super.onResume();

		quantize = (WheelView) findViewById(R.id.quantizeWheel);
		triple=(CheckBox)findViewById(R.id.tripleCheckBox);
		tripleImage=(ImageView)findViewById(R.id.triple_image);
		
		quantize.setVisibleItems(3);
		quantize.setViewAdapter(new QuantizeAdapter(this));
		tripleImage.setMinimumHeight(Main.nHeight* 30);
		tripleImage.setMinimumWidth(Main.nWidth* 30);
		triple.setTextSize(Main.nHeight* 7);
		triple.setTextColor(Color.parseColor("#000000"));
		quantize.setPadding(0,0,Main.nHeight* 1000,0);
		triple.setPadding(Main.nHeight* 10, Main.nHeight* 10, Main.nHeight* 10, Main.nHeight* 10);

	}

	/**
	 * Adapter for countries
	 */
	private class QuantizeAdapter extends AbstractWheelTextAdapter {
		// Countries names
		private String countries[] = new String[] { "2분음표", "ㅇ", "Ukraine",
				"France" };
		// Countries flags
		private int flags[] = new int[] { R.drawable.note_2, R.drawable.note_4,
				R.drawable.note_8, R.drawable.note_16 };

		/**
		 * Constructor
		 */
		protected QuantizeAdapter(Context context) {
			super(context, R.layout.note_layout, NO_RESOURCE);
		
		//	setItemTextResource(R.id.metronume_text);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			ImageView img = (ImageView) view.findViewById(R.id.note_imageview);
			img.setImageResource(flags[index]);
			return view;
		}

		@Override
		public int getItemsCount() {
			return countries.length;
		}

		@Override
		protected CharSequence getItemText(int index) {
			return countries[index];
		}
	}

}