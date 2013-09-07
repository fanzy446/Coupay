package com.billme.ui;

import com.billme.logic.MainService;
import com.billme.widget.MyChoiceButton;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class BaseActivity extends Activity {
	protected GestureDetector gd;
	protected MyChoiceButton myLayout = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		MainService.allActivities.add(this);
		super.onCreate(savedInstanceState);

	}

	public void addLayout() {
		gd = new GestureDetector(this, new MyGestureListener());
		myLayout = new MyChoiceButton(this);
		myLayout.setVisibility(View.INVISIBLE);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, 0, 0, 0);
		this.addContentView(myLayout, layoutParams);
	}

	public void addTitle(String t) {
		ImageButton backButton = (ImageButton) findViewById(R.id.ib_mytitle_back);
		TextView title = (TextView) findViewById(R.id.tv_mytitle_title);
		title.setText(t);
		backButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		boolean b = false;
		if(gd != null)
		{
			if (myLayout != null && myLayout.isShown())
			{
				Log.i("error","myLayout");
				myLayout.onTouchEvent(event);
				b = true;
			}
			else
			{
				Log.i("error","gd");
				gd.onTouchEvent(event);
			}		
		}				
		if(b)
			return b;
		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	public void pushMessage() {

	}
 
	protected class MyGestureListener extends
			GestureDetector.SimpleOnGestureListener {
		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			if (myLayout != null)
				myLayout.showChoice(e);
			super.onShowPress(e);
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		if (MainService.getStatusBarHeight() == 0
				&& MainService.getTitleBarHeight() == 0) {
			Rect frame = new Rect();
			getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
			int statusBarHeight = frame.top;
			int contentTop = getWindow()
					.findViewById(Window.ID_ANDROID_CONTENT).getTop();
			// statusBarHeight是上面所求的状态栏的高度
			int titleBarHeight = contentTop - statusBarHeight;
			MainService.setStatusBarHeight(statusBarHeight);
			MainService.setTitleBarHeight(titleBarHeight);
			super.onWindowFocusChanged(hasFocus);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MainService.allActivities.remove(this);
	}

}
