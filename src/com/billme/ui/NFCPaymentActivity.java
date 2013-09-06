package com.billme.ui;

import com.billme.logic.BillMeActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NFCPaymentActivity extends BaseActivity implements BillMeActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfcpayment);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_nfcpayment, menu);
		return true;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		
	}

}
