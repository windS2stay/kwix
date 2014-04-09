package com.example.autoscore;

import android.os.*;
import android.app.*;
import android.content.Intent;
import android.graphics.Typeface;
import android.widget.*;
import android.view.*;

public class Setting extends Main{
	String[] key1 = {"A", "B", "C", "D", "E", "F", "G"};
	String[] key2 = {" ", "#"};
	String[] quant = {"♩", "♪"};
	String quantslt;
	String[] denom = {"4", "3", "6"};
	String[] numer = {"4", "8"};
	String[] tune = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
	int standardi = 3;
	int quanti = 0;
	Boolean tri = false; 
	Boolean score;
	String tempo;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		
		Typeface face = Typeface.createFromAsset(getAssets(), "fonts/nanumpen.ttf");
		TextView text1 = (TextView)findViewById(R.id.tempo);
		TextView text2 = (TextView)findViewById(R.id.key);
		TextView text3 = (TextView)findViewById(R.id.quantize);
		TextView text4 = (TextView)findViewById(R.id.triple);
		TextView text5 = (TextView)findViewById(R.id.meter);
		TextView text6 = (TextView)findViewById(R.id.score);
		text1.setTypeface(face);
		text2.setTypeface(face);
		text3.setTypeface(face);
		text4.setTypeface(face);
		text5.setTypeface(face);
		text6.setTypeface(face);
		
		final EditText tempoet = (EditText)findViewById(R.id.tempoedit);		

		Spinner keysp = (Spinner)findViewById(R.id.key1);
		keysp.setPrompt("Key 선택");
		ArrayAdapter<String> key1list;
		key1list = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, key1);
		keysp.setAdapter(key1list);
		
		Spinner keysp2 = (Spinner)findViewById(R.id.key2);
		keysp2.setPrompt("올림 선택");
		ArrayAdapter<String> key2list;
		key2list = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, key2);
		keysp2.setAdapter(key2list);
		
		final Spinner quansp = (Spinner)findViewById(R.id.quant);
		quansp.setPrompt("quantize");
		ArrayAdapter<String> quanlist;
		quanlist = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, quant);
		quansp.setAdapter(quanlist);
		
		quantslt = quansp.getSelectedItem().toString();
		for(int quanti = 0;quanti<1;quanti++)
		{
			if(quant[quanti] == quantslt)
			{
				break;
			}
		}
		
		ToggleButton tricheck = (ToggleButton)findViewById(R.id.Triplecheck);
		tricheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
				if(isChecked){
					tri = true;
				}
				else{
					tri = false;
				}
			}
		});
		
		Spinner denomsp = (Spinner)findViewById(R.id.denom);
		denomsp.setPrompt("denom");
		ArrayAdapter<String> denomlist;
		denomlist = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, denom);
		denomsp.setAdapter(denomlist);
		
		Spinner numersp = (Spinner)findViewById(R.id.numer);
		numersp.setPrompt("numer");
		ArrayAdapter<String> numerlist;
		numerlist = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, numer);
		numersp.setAdapter(numerlist);
		
		RadioButton scorebtn1 = (RadioButton)findViewById(R.id.highscore);
		scorebtn1.setTypeface(face);
		scorebtn1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					score = true;
				}
				else{
					score = false;
				}
					
			}
		});
		
		RadioButton scorebtn2 = (RadioButton)findViewById(R.id.lowscore);
		scorebtn2.setTypeface(face);
		scorebtn2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					score = false;
				}
				else{
					score = true;
				}
			}
		});
		
		final TextView tunetext = (TextView)findViewById(R.id.tune);
		tunetext.setText(tune[standardi]);
		
		Button stanleftbtn = (Button)findViewById(R.id.stanleft);
		stanleftbtn.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				if(standardi != 0)
				{
					standardi--;
					tunetext.setText(tune[standardi]);
				}
				else
				{
					standardi = 11;
					tunetext.setText(tune[standardi]);
				}
			}
		});
		
		Button stanrightbtn = (Button)findViewById(R.id.stanright);
		stanrightbtn.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				if(standardi != 11)
				{
					standardi++;
					tunetext.setText(tune[standardi]);
				}
				else
				{
					standardi = 0;
					tunetext.setText(tune[standardi]);
				}
			}
		});
		
		Button stanbtn = (Button)findViewById(R.id.standard);
		stanbtn.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				standardi = 3;
				tunetext.setText(tune[standardi]);				
			}
		});
		
		Button btnplay = (Button)findViewById(R.id.playbtn);
		btnplay.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				Intent intent = new Intent(Setting.this, Playing.class);
				tempo = tempoet.getText().toString();
				intent.putExtra("tempodata", tempo);
				intent.putExtra("tridata", tri);
				intent.putExtra("quandata", quanti);
				startActivity(intent);
			}
		});
	}
}
