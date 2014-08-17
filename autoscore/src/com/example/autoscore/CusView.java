package com.example.autoscore;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class CusView extends View{
	Context context;
	//Typeface musical;
	//Typeface lassus;
	//Typeface nanum;

	public CusView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context  = context;
		//musical = Typeface.createFromAsset(context.getAssets(), "fonts/Musical.ttf");
		//lassus = Typeface.createFromAsset(context.getAssets(), "fonts/lasus.ttf");
		//nanum = Typeface.createFromAsset(context.getAssets(), "fonts/nanumpen.ttf");
	}

	@Override
	protected void onDraw(Canvas canvas){
		String line = "asdfvasdflkjasdflkj";
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		//paint.setTypeface(lassus);
		paint.setTextSize(40);
		canvas.drawText(line, 100, 100, paint);	
		
	}
}
