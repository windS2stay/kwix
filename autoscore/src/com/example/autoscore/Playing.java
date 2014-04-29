package com.example.autoscore;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.*;
import android.content.*;

public class Playing extends Setting{
	String tempo;
	String[] quant = {"¢Û", "¢Ü"};
	int quanti;
	Boolean tri; 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playing);
		
		Typeface face = Typeface.createFromAsset(getAssets(), "fonts/nanumpen.ttf");
		TextView tempotext = (TextView)findViewById(R.id.playtempo);
		TextView tritext = (TextView)findViewById(R.id.playtri);
		TextView metrotext = (TextView)findViewById(R.id.playmetronome);
		TextView quantext = (TextView)findViewById(R.id.playquan);
		tempotext.setTypeface(face);
		tritext.setTypeface(face);
		metrotext.setTypeface(face);
		quantext.setTypeface(face);
		
	/*EditText tempoedit = (EditText)findViewById(R.id.playtempoedit);		
		Intent intent = getIntent();
		tempoedit.setText(intent.getStringExtra("tempodata"));
		tri = intent.getExtras().getBoolean("tridata");
		tempo = intent.getStringExtra("tempodata");
		quanti = intent.getExtras().getInt("quantdata");
		quanti++;
		
		ToggleButton tritoggle = (ToggleButton)findViewById(R.id.playtritoggle);
		tritoggle.setChecked(tri);
		
		Spinner playquansp = (Spinner)findViewById(R.id.playquansp);
		playquansp.setPrompt("quantize");
		ArrayAdapter<String> playquanlist;
		playquanlist = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, quant);
		playquansp.setAdapter(playquanlist);
		playquansp.setSelection(quanti);*/
	}
}
