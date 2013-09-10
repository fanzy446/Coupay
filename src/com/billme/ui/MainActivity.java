package com.billme.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.billme.logic.BillMeActivity;
import com.zxing.activity.CaptureActivity;

public class MainActivity extends BaseActivity implements BillMeActivity {
	private Button codeButton = null;
	private Button nfcButton = null;
	private Button tradeButton = null;
	public static final int REFRESH_USERINFO = 1024;
	public static final int REFRESH_PAYMENT = 1025;
	public static final int REFRESH_REPAYMENT = 1026;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		addLayout();
		addTitle("选择支付方式");

		codeButton = (Button) findViewById(R.id.btn_main_code);
		nfcButton = (Button) findViewById(R.id.btn_main_nfc);
		tradeButton = (Button) findViewById(R.id.btn_main_traderecord);
		
		nfcButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, NFCPaymentActivity.class);
				// startActivityForResult(intent, 0);
				startActivity(intent);
			}

		});
		codeButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				// intent.setClass(MainActivity.this, CaptureActivity.class);
				intent.setClass(MainActivity.this, PaymentConfirmActivity.class);
				// startActivityForResult(intent, 0);
				JSONObject json = new JSONObject();
				try {

					json.put("receiver", "hello");
					json.put("money", 200);
					json.put("method", "QDCode");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				intent.putExtra("result", json.toString());
				startActivity(intent);
			}

		});
		
		tradeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, TradeRecordActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		switch (((Integer) param[0]).intValue()) {
		case -100: {
			Toast.makeText(MainActivity.this, "Something failed",
					Toast.LENGTH_SHORT).show();
			break;
		}
		case REFRESH_USERINFO: {
			Intent it = new Intent(this, TestActivity.class);

			this.startActivity(it);
			break;
		}
		case REFRESH_PAYMENT: {

			break;
		}
		default:
			break;
		}
	}
}
