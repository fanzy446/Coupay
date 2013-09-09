package com.billme.ui;

import java.util.HashMap;

import com.billme.logic.BillMeActivity;
import com.billme.logic.MainService;
import com.futurePayment.constant.ResultCode;
import com.futurePayment.constant.Task;
import com.futurePayment.model.ImageHelper;
import com.futurePayment.model.PaymentException;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ShareActivity extends BaseActivity implements BillMeActivity {
	public static final int SHARE_SECCUSS = 1;
	public static final int SHARE_FAILURE = -1;

	private Button highButton = null;
	private Button middleButton = null;
	private Button lowButton = null;
	private EditText content = null;
	private ImageView photo = null;
	private Button okButton = null;
	private ProgressDialog pd = null;

	private int grade = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);

		highButton = (Button) findViewById(R.id.btn_share_high);
		middleButton = (Button) findViewById(R.id.btn_share_middle);
		lowButton = (Button) findViewById(R.id.btn_share_low);
		content = (EditText) findViewById(R.id.et_share_content);
		photo = (ImageView) findViewById(R.id.iv_share_photo);

		highButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				lowButton.setCompoundDrawables(null, null, null, null);
				middleButton.setCompoundDrawables(null, null, null, null);
				Drawable click = getResources().getDrawable(R.drawable.click);
				highButton.setCompoundDrawables(null, null, click, null);
				grade = 3;
			}

		});
		middleButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				lowButton.setCompoundDrawables(null, null, null, null);
				highButton.setCompoundDrawables(null, null, null, null);
				Drawable click = getResources().getDrawable(R.drawable.click);
				middleButton.setCompoundDrawables(null, null, click, null);
				grade = 2;
			}

		});
		lowButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				highButton.setCompoundDrawables(null, null, null, null);
				middleButton.setCompoundDrawables(null, null, null, null);
				Drawable click = getResources().getDrawable(R.drawable.click);
				lowButton.setCompoundDrawables(null, null, click, null);
				grade = 1;
			}

		});
		okButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 传递各种信息
				if (pd == null) {
					pd = new ProgressDialog(ShareActivity.this);
				}
				pd.setMessage("Sharing..");
				pd.show();
				Intent intent = new Intent();
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("receiver", intent.getStringExtra("receiver"));
				param.put("grade", grade);
				param.put("content", content.getText().toString());
				param.put("money", intent.getDoubleExtra("money", 0));
				param.put(
						"photo",
						MainService.getImageHelper().drawableToByte(
								photo.getDrawable()));
				Task task = new Task(Task.TASK_USER_LOGIN, param);
				MainService.newTask(task);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_share, menu);
		return true;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		switch (((Integer) param[0]).intValue()) {
		case SHARE_FAILURE:
			pd.cancel();
			Toast.makeText(this, "Share unsuccessfully", Toast.LENGTH_SHORT)
					.show();
			break;
		case SHARE_SECCUSS:
			pd.cancel();
			Toast.makeText(this, "Share successfully", Toast.LENGTH_SHORT)
					.show();
			Intent it = new Intent(this, MainActivity.class);
			this.startActivity(it);
			this.finish();
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
