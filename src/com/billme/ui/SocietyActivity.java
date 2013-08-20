package com.billme.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.billme.logic.BillMeActivity;
import com.billme.logic.MainService;
import com.billme.widget.MySocietyAdapter;
import com.futurePayment.constant.ResultCode;
import com.futurePayment.constant.Task;
import com.futurePayment.model.CommentInfo;
import com.futurePayment.model.PaymentException;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class SocietyActivity extends BaseActivity implements BillMeActivity{

	public static final int GET_SECCUSS = 1;
	public static final int GET_FAILURE = -1;
	
	private Button jumpButton = null;
	private ArrayList<CommentInfo> cl = null;
	private MySocietyAdapter commentAdapter = null;
	private ListView commentList = null;
	private ProgressDialog pd = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_society);
		
		jumpButton = (Button)findViewById(R.id.btn_society_jump);
		
		jumpButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			}
			
		});
		
		if (pd == null) {
			pd = new ProgressDialog(this);
		}
		pd.setMessage("Logining..");
		pd.show();
		Task task = new Task(Task.TASK_GET_MOMENTS, null);
		MainService.newTask(task);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_society, menu);
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
		case GET_FAILURE:
//			int state = ((PaymentException) param[1]).getResultCode();
//			String hint = null;
//			switch (state) {
//			case ResultCode.WRONG_PASSWORD:
//				hint = "Password Wrong";
//				break;
//			case ResultCode.REGISTER_NOT_EXISTS:
//				hint = "User Not Exits";
//				break;
//			case ResultCode.REPEAT_LOGIN:
//				hint = "Repeatly Login";
//				break;
//			default:
//				hint = "Unknown fault";
//				break;
//			}
			pd.cancel();
			Toast.makeText(this, "refresh unsuccessfully", Toast.LENGTH_SHORT).show();
			break;
		case GET_SECCUSS:
			pd.cancel();
			ArrayList<CommentInfo> fresh = ((ArrayList<CommentInfo>) param[1]);
			cl.addAll(0, fresh);
			commentAdapter.notifyDataSetChanged();
			break;
		}
	}

}
