package com.billme.ui;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import com.billme.logic.BillMeActivity;
import com.billme.logic.MainService;
import com.billme.util.FileUtil;
import com.billme.widget.MySocietyAdapter;
import com.futurePayment.constant.Task;
import com.futurePayment.model.CommentInfo;
import com.google.gson.Gson;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class SocietyActivity extends BaseActivity implements BillMeActivity {

	public static final int GET_SECCUSS = 1;
	public static final int GET_FAILURE = -1;
	public static final int INITIAL_SECCUSS = 2;
	public static final int INITIAL_FAILURE = -2;
	public static final int REFRESH_SECCUSS = 3;
	public static final int REFRESH_FAILURE = -3;

	private ImageButton jumpButton = null;
	private ArrayList<CommentInfo> cl = new ArrayList<CommentInfo>();
	private MySocietyAdapter commentAdapter = null;
	private ListView commentList = null;
	private ProgressDialog pd = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_society);

		jumpButton = (ImageButton) findViewById(R.id.ib_society_jump);
		commentList = (ListView) findViewById(R.id.lv_society_list);

		jumpButton.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SocietyActivity.this, SurroundActivity.class);
				startActivity(intent);
			}

		});

		bindAdapter();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_society, menu);
		return true;
	}

	private void bindAdapter() {
		commentAdapter = new MySocietyAdapter(this, cl);
		commentList.setAdapter(commentAdapter);
		// commentList.setOnItemClickListener(new ListView.OnItemClickListener()
		// {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// // TODO Auto-generated method stub
		// // 选中某个商家
		// // Intent intent = new Intent();
		// // intent.setClass(SurroundActivity.this, );
		// }
		// });
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		if (pd == null) {
			pd = new ProgressDialog(this);
		}
		pd.setMessage("Loading..");
		pd.show();
		Task task = new Task(Task.INIT_SOCIETY, null);
		MainService.newTask(task);
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		switch (((Integer) param[0]).intValue()) {
		case GET_FAILURE: {
			// int state = ((PaymentException) param[1]).getResultCode();
			// String hint = null;
			// switch (state) {
			// case ResultCode.WRONG_PASSWORD:
			// hint = "Password Wrong";
			// break;
			// case ResultCode.REGISTER_NOT_EXISTS:
			// hint = "User Not Exits";
			// break;
			// case ResultCode.REPEAT_LOGIN:
			// hint = "Repeatly Login";
			// break;
			// default:
			// hint = "Unknown fault";
			// break;
			// }
			pd.cancel();
			Toast.makeText(this, "refresh unsuccessfully", Toast.LENGTH_SHORT)
					.show();
			break;
		}

		case GET_SECCUSS: {
			pd.cancel();
			ArrayList<CommentInfo> fresh = (ArrayList<CommentInfo>) param[1];
			cl.addAll(fresh);
			commentAdapter.notifyDataSetChanged();
			break;
		}
		case INITIAL_FAILURE: {
			pd.cancel();
			break;
		}

		case INITIAL_SECCUSS: {
			pd.cancel();
			ArrayList<CommentInfo> fresh = (ArrayList<CommentInfo>) param[1];
			cl.addAll(fresh);
			commentAdapter.notifyDataSetChanged();
			break;
		}
		case REFRESH_SECCUSS: {
			ArrayList<CommentInfo> fresh = (ArrayList<CommentInfo>) param[1];
			cl.addAll(0, fresh);
			// 不确定，待改进
			FileUtil fileUtil = new FileUtil(MainService.getUser().getName());
			Gson gson = new Gson();
			String temp = gson.toJson(fresh.subList(0, 10));
			StringBufferInputStream sbis = new StringBufferInputStream(temp);
			fileUtil.writeToSDFromInputStream("", "society", sbis, true);
			commentAdapter.notifyDataSetChanged();
			break;
		}
		case REFRESH_FAILURE: {

		}

		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
}
