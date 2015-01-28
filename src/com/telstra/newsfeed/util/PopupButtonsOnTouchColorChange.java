package com.telstra.newsfeed.util;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.telstra.newsfeed.R;
public class PopupButtonsOnTouchColorChange implements OnTouchListener{

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.btnUpdate){
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:					
			v.setBackgroundColor(Color.parseColor("#facc3a"));
			
			return false;				
		case MotionEvent.ACTION_CANCEL:
			v.setBackgroundColor(Color.parseColor("#33b5e5"));
			return false;
		case MotionEvent.ACTION_UP:
			v.setBackgroundColor(Color.parseColor("#33b5e5"));	
			
			return false;
		case MotionEvent.ACTION_MOVE:

			return false;
		default:
			
			return false;
		}
		}
		else if(v.getId()==R.id.btnCancel){
			switch(event.getAction())
			{
			case MotionEvent.ACTION_DOWN:					
				v.setBackgroundColor(Color.parseColor("#4f4f4f"));
				
				return false;				
			case MotionEvent.ACTION_CANCEL:
				v.setBackgroundColor(Color.parseColor("#0f1a27"));
				return false;
			case MotionEvent.ACTION_UP:
				v.setBackgroundColor(Color.parseColor("#0f1a27"));	
				
				return false;
			case MotionEvent.ACTION_MOVE:

				return false;
			default:
				
				return false;
			}
			
		}
		return false;
	}

}
