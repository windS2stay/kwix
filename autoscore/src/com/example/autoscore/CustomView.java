package com.example.autoscore;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class CustomView extends View{
	Typeface musical;
	Typeface lassus;
	Typeface nanum;
	int diswidth;
	int disheight;
	int j = 1;
	Context context;
	List<Integer> hztable = new ArrayList<Integer>();
	int tablecount = 0;	
	
	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);	
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		diswidth = dm.heightPixels;
		disheight = dm.widthPixels;
		musical = Typeface.createFromAsset(context.getAssets(), "fonts/Musical.ttf");
		lassus = Typeface.createFromAsset(context.getAssets(), "fonts/lassus.ttf");
		nanum = Typeface.createFromAsset(context.getAssets(), "fonts/nanumpen.ttf");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas){
		j = 1;
		String line = "4444444-444444-";
		Paint paint = new Paint();	
		paint.setColor(Color.BLACK);
		paint.setTextSize((float)(diswidth/16*7.5));
		paint.setTypeface(lassus);
		canvas.drawText(line, diswidth/80, diswidth/3 , paint);
		float viewwidth = paint.measureText(line, 0, line.length());
		getLayoutParams().width = (int)viewwidth+50;
		paint.setTypeface(musical);
		paint.setTextSize((float)(diswidth/16*3));
		canvas.drawText("&", disheight/120, diswidth/3, paint);
		canvas.drawText("4", (float)(disheight/8.5), (float)(diswidth/40*11.7), paint);
		canvas.drawText("4", (float)(disheight/8.5), (float)(diswidth/40*8.4), paint);
		canvas.drawText("#", (float)((disheight/120)*10), (float)(diswidth/40*6.55), paint);
		for(int i = 0; i < hztable.size(); i++)
		{
			if(j < 5)
			{
				if(hztable.get(i) < 207)
				{
					canvas.drawText("_", (float)(((disheight/120)*15*j)+110), (float)(diswidth/40*15.05), paint);
					canvas.drawText("q", (float)(((disheight/120)*15*j)+120), (float)(diswidth/40*15.9), paint);
					j++;
				}
				else if(hztable.get(i) < 233 && hztable.get(i) >= 207)
				{
					canvas.drawText("_", (float)(((disheight/120)*15*j)+110), (float)(diswidth/40*15.05), paint);
					canvas.drawText("q", (float)(((disheight/120)*15*j)+120), (float)(diswidth/40*15.05), paint);
					j++;
				}
				else if(hztable.get(i) < 253 && hztable.get(i) >= 233)
				{
					canvas.drawText("q", (float)(((disheight/120)*15*j)+120), (float)(diswidth/40*14.2), paint);
					j++;
				}
				else if(hztable.get(i) < 277 && hztable.get(i) >= 253)
				{
					canvas.drawText("q", (float)(((disheight/120)*15*j)+120), (float)(diswidth/40*13.35), paint);
					j++;
				}
				else if(hztable.get(i) < 311 && hztable.get(i) >= 277)
				{
					canvas.drawText("q", (float)(((disheight/120)*15*j)+120), (float)(diswidth/40*12.5), paint);
					j++;
				}
				else if(hztable.get(i) < 339 && hztable.get(i) >= 311)
				{
					canvas.drawText("q", (float)(((disheight/120)*15*j)+120), (float)(diswidth/40*11.65), paint);
					j++;
				}
				else if(hztable.get(i) < 370 && hztable.get(i) >= 339)
				{
					canvas.drawText("q", (float)(((disheight/120)*15*j)+120), (float)(diswidth/40*10.8), paint);
					j++;
				}
				else if(hztable.get(i) < 415 && hztable.get(i) >= 370)
				{
					canvas.drawText("Q", (float)(((disheight/120)*15*j)+120), (float)(diswidth/40*9.95), paint);
					j++;
				}
				else if(hztable.get(i) < 466 && hztable.get(i) >= 415)
				{
					canvas.drawText("Q", (float)(((disheight/120)*15*j)+120), (float)(diswidth/40*9.1), paint);
					j++;
				}
				else if(hztable.get(i) < 508 && hztable.get(i) >= 466)
				{
					canvas.drawText("Q", (float)(((disheight/120)*15*j)+120), (float)(diswidth/40*8.25), paint);
					j++;
				}
				else if(hztable.get(i) < 554 && hztable.get(i) >= 508)
				{
					canvas.drawText("Q", (float)(((disheight/120)*15*j)+120), (float)(diswidth/40*7.4), paint);
					j++;
				}
				else if(hztable.get(i) < 622 && hztable.get(i) >= 554)
				{
					canvas.drawText("Q", (float)(((disheight/120)*15*j)+120), (float)(diswidth/40*6.55), paint);
					j++;
				}
				else if(hztable.get(i) < 678 && hztable.get(i) >= 622)
				{
					canvas.drawText("Q", (float)(((disheight/120)*15*j)+120), (float)(diswidth/40*5.7), paint);
					j++;
				}
				else if(hztable.get(i) >= 678)
				{
					canvas.drawText("_", (float)(((disheight/120)*15*j)+110), (float)(diswidth/40*4.85), paint);
					canvas.drawText("Q", (float)(((disheight/120)*15*j)+120), (float)(diswidth/40*4.85), paint);
					j++;
				}
			}
			else if(j < 9)
			{
				if(hztable.get(i) < 207)
				{
					canvas.drawText("_", (float)(((disheight/120)*15*j)+230), (float)(diswidth/40*15.05), paint);
					canvas.drawText("q", (float)(((disheight/120)*15*j)+240), (float)(diswidth/40*15.9), paint);
					j++;
				}
				else if(hztable.get(i) < 233 && hztable.get(i) >= 207)
				{
					canvas.drawText("_", (float)(((disheight/120)*15*j)+230), (float)(diswidth/40*15.05), paint);
					canvas.drawText("q", (float)(((disheight/120)*15*j)+240), (float)(diswidth/40*15.05), paint);
					j++;
				}
				else if(hztable.get(i) < 253 && hztable.get(i) >= 233)
				{
					canvas.drawText("q", (float)(((disheight/120)*15*j)+240), (float)(diswidth/40*14.2), paint);
					j++;
				}
				else if(hztable.get(i) < 277 && hztable.get(i) >= 253)
				{
					canvas.drawText("q", (float)(((disheight/120)*15*j)+240), (float)(diswidth/40*13.35), paint);
					j++;
				}
				else if(hztable.get(i) < 311 && hztable.get(i) >= 277)
				{
					canvas.drawText("q", (float)(((disheight/120)*15*j)+240), (float)(diswidth/40*12.5), paint);
					j++;
				}
				else if(hztable.get(i) < 339 && hztable.get(i) >= 311)
				{
					canvas.drawText("q", (float)(((disheight/120)*15*j)+240), (float)(diswidth/40*11.65), paint);
					j++;
				}
				else if(hztable.get(i) < 370 && hztable.get(i) >= 339)
				{
					canvas.drawText("q", (float)(((disheight/120)*15*j)+240), (float)(diswidth/40*10.8), paint);
					j++;
				}
				else if(hztable.get(i) < 415 && hztable.get(i) >= 370)
				{
					canvas.drawText("Q", (float)(((disheight/120)*15*j)+240), (float)(diswidth/40*9.95), paint);
					j++;
				}
				else if(hztable.get(i) < 466 && hztable.get(i) >= 415)
				{
					canvas.drawText("Q", (float)(((disheight/120)*15*j)+240), (float)(diswidth/40*9.1), paint);
					j++;
				}
				else if(hztable.get(i) < 508 && hztable.get(i) >= 466)
				{
					canvas.drawText("Q", (float)(((disheight/120)*15*j)+240), (float)(diswidth/40*8.25), paint);
					j++;
				}
				else if(hztable.get(i) < 554 && hztable.get(i) >= 508)
				{
					canvas.drawText("Q", (float)(((disheight/120)*15*j)+240), (float)(diswidth/40*7.4), paint);
					j++;
				}
				else if(hztable.get(i) < 622 && hztable.get(i) >= 554)
				{
					canvas.drawText("Q", (float)(((disheight/120)*15*j)+240), (float)(diswidth/40*6.55), paint);
					j++;
				}
				else if(hztable.get(i) < 678 && hztable.get(i) >= 622)
				{
					canvas.drawText("Q", (float)(((disheight/120)*15*j)+240), (float)(diswidth/40*5.7), paint);
					j++;
				}
				else if(hztable.get(i) >= 678)
				{
					canvas.drawText("_", (float)(((disheight/120)*15*j)+230), (float)(diswidth/40*4.85), paint);
					canvas.drawText("Q", (float)(((disheight/120)*15*j)+240), (float)(diswidth/40*4.85), paint);
					j++;
				}
			}
			else
			{
			
			}
		}
	}
	
	public void transHz(int inputhz)
	{
		hztable.add(inputhz);
		invalidate();
	}
}
