package com.billme.ui;

import com.billme.widget.MyChoiceButton;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class BaseActivity extends Activity {
	protected GestureDetector gd;
	protected MyChoiceButton myLayout = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (myLayout.isShown())
			return myLayout.onTouchEvent(event);
		return gd.onTouchEvent(event);
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
			myLayout.showChoice(e);
			super.onShowPress(e);
		}
	}

}
