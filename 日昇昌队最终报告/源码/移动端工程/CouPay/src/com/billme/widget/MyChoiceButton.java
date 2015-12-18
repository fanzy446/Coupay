package com.billme.widget;

import com.billme.logic.MainService;
import com.billme.ui.AccountActivity;
import com.billme.ui.MainActivity;
import com.billme.ui.ManagementActivity;
import com.billme.ui.R;
import com.billme.ui.SocietyActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MyChoiceButton extends LinearLayout implements
		View.OnTouchListener {
	private MyChoiceButton myLayout;
	private ImageButton ib[];
	private int layoutWidth = 0;
	private int layoutHeight = 0;
	private boolean isShown = false;
	private int currentX = 0;
	private int currentY = 0;
	private int m = 1;// ��Ե�жϱ���

	public MyChoiceButton(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public MyChoiceButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.my_choice_button, this);
		myLayout = this;
		ViewTreeObserver vto2 = myLayout.getViewTreeObserver();
		vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				myLayout.getViewTreeObserver().removeOnGlobalLayoutListener(
						this);
				// .removeGlobalOnLayoutListener(this);
				layoutWidth = myLayout.getWidth();
				layoutHeight = myLayout.getHeight();
			}
		});

		ib = new ImageButton[4];
		ib[0] = (ImageButton) findViewById(R.id.ib_mychoicebutton_image0);
		ib[0].setOnTouchListener(this);
		ib[1] = (ImageButton) findViewById(R.id.ib_mychoicebutton_image1);
		ib[1].setOnTouchListener(this);
		ib[2] = (ImageButton) findViewById(R.id.ib_mychoicebutton_image2);
		ib[2].setOnTouchListener(this);
		ib[3] = (ImageButton) findViewById(R.id.ib_mychoicebutton_image3);
		ib[3].setOnTouchListener(this);
	}

	@SuppressLint("NewApi")
	public MyChoiceButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.my_choice_button, this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int n = inWitchRect(event, m);
		if (n != -1) {
			// handler.removeCallbacks(timeChecker);
			handler.removeCallbacks(hider);
			handler.postDelayed(hider, 1000);
			this.onTouch(ib[n], event);
		}
		return true;
	}

	public void setImageResource(int n, int resId) {
		ib[n].setImageResource(resId);
	}

	public int getLayoutWidth() {
		return layoutWidth;
	}

	public void setLayoutWidth(int layoutWidth) {
		this.layoutWidth = layoutWidth;
	}

	public int getLayoutHeight() {
		return layoutHeight;
	}

	public void setLayoutHeight(int layoutHeight) {
		this.layoutHeight = layoutHeight;
	}

	public boolean isShown() {
		return isShown;
	}

	public void setShown(boolean isShown) {
		this.isShown = isShown;
	}

	// public void setVisible(boolean b)
	// {
	// if(b)
	// myLayout.setVisibility(View.VISIBLE);
	// else
	// myLayout.setVisibility(View.INVISIBLE);
	// }
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		int index = 0;
		int imgSource = 0;
		switch (v.getId()) {
		case R.id.ib_mychoicebutton_image0:
			index = 0;
			imgSource = R.drawable.pay_on;
			break;
		case R.id.ib_mychoicebutton_image1:
			index = 1;
			imgSource = R.drawable.society_on;
			break;
		case R.id.ib_mychoicebutton_image2:
			index = 2;
			imgSource = R.drawable.code_on;
			break;
		case R.id.ib_mychoicebutton_image3:
			index = 3;
			imgSource = R.drawable.management_on;
			break;
		default:
			break;
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_UP:
			// ѡ��
			Intent intent = new Intent();
			switch (index) {
			case 0:
				intent.setClass(getContext(), MainActivity.class);
				break;
			case 1:
				intent.setClass(getContext(), SocietyActivity.class);
				break;
			case 2:
				intent.setClass(getContext(), MainActivity.class);
				break;
			case 3:
				intent.setClass(getContext(), ManagementActivity.class);
				break;
			default:
				// intent.setClass(getContext(), AccountActivity.class);
				break;
			}
			getContext().startActivity(intent);
			this.setVisibility(View.INVISIBLE);
			isShown = false;
			break;
		case MotionEvent.ACTION_MOVE:
			// this.setFocusable(false);
			ib[0].setImageResource(R.drawable.pay_out);
			ib[1].setImageResource(R.drawable.society_out);
			ib[2].setImageResource(R.drawable.code_out);
			ib[3].setImageResource(R.drawable.management_out);
			ib[index].setImageResource(imgSource);
			break;
		}
		return true;
	}

	private Handler handler = new Handler();
	private Runnable hider = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			hideChoice();

			// handler.postDelayed(timeChecker, 1000);
		}

	};

	// private Runnable timeChecker = new Runnable()
	// {
	// @Override
	// public void run()
	// {
	// // TODO Auto-generated method stub
	// hideChoice();
	//
	// }
	//
	// };

	@SuppressLint("NewApi")
	public void showChoice(MotionEvent event) {

		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		currentX = (int) event.getX() - this.getLayoutWidth() / 2;
		currentY = (int) event.getY() - this.getLayoutHeight() / 2;
		layoutParams.setMargins(
				currentX,
				currentY - MainService.getStatusBarHeight()
						- MainService.getTitleBarHeight(), 0, 0);
		this.setLayoutParams(layoutParams);
		Animation animation = AnimationUtils.loadAnimation(getContext(),
				R.anim.show_anim);
		this.setVisibility(View.VISIBLE);
		this.setAnimation(animation);
		isShown = true;
		handler.postDelayed(hider, 2000);
		// layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);

	}

	public void hideChoice() {
		this.setVisibility(View.INVISIBLE);
		isShown = false;
		Animation animation = AnimationUtils.loadAnimation(getContext(),
				R.anim.hide_anim);
		this.setAnimation(animation);
	}

	private int inWitchRect(MotionEvent event, int m) {
		int n = -1;
		int touchX = (int) event.getX();
		int touchY = (int) event.getY();
		int tempX = currentX + this.getWidth() / 2;
		int tempY = currentY + this.getHeight() / 2;
		if (touchX >= tempX - m * this.getWidth() / 2 && touchX < tempX
				&& touchY >= tempY - m * this.getHeight() / 2 && touchY < tempY) {
			// �ڵ�һ������
			n = 0;
		} else if (touchX < tempX + m * this.getWidth() / 2 && touchX >= tempX
				&& touchY >= tempY - m * this.getHeight() / 2 && touchY < tempY) {
			// �ڵڶ�������
			n = 1;
		} else if (touchX >= tempX - m * this.getWidth() / 2 && touchX < tempX
				&& touchY < tempY + m * this.getHeight() * 3 / 2
				&& touchY >= tempY) {
			// �ڵ���������
			n = 2;
		} else if (touchX < tempX + m * this.getWidth() / 2 && touchX >= tempX
				&& touchY < tempY + m * this.getHeight() * 3 / 2
				&& touchY >= tempY) {
			// �ڵ��Ŀ�����
			n = 3;
		}
		return n;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}
}
