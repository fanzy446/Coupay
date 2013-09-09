package com.billme.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.billme.logic.BillMeActivity;
import com.billme.logic.MainService;
import com.billme.widget.MyListViewAdapter;
import com.futurePayment.constant.ResultCode;
import com.futurePayment.constant.Task;
import com.futurePayment.model.BankCard;
import com.futurePayment.model.PaymentException;

public class BankCardActivity extends BaseActivity implements BillMeActivity {
	public static final int GET_BANK_CARD_SECCUSS = 1;
	public static final int ADD_BANK_CARD_SUCCESS = 2;
	public static final int GET_BANK_CARD_FAILURE = -1;
	public static final int ADD_BANK_CARD_FAILURE = -2;

	private ListView bankCardList = null;
	private Button bandCardBindButton = null;
	private ProgressDialog pd = null;
	private MyListViewAdapter adapter = null;
	private ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bank_card);
		addLayout();
		addTitle("银行卡管理");

		bankCardList = (ListView) findViewById(R.id.lv_bankcard_bankcards);
		bandCardBindButton = (Button) findViewById(R.id.btn_bankcard_bind);

		bandCardBindButton.setOnClickListener(new Button.OnClickListener() {
			private Dialog addBankCard = null;
			private EditText cardNumber = null;
			private EditText password = null;
			private Button add = null;
			private Button cancel = null;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addBankCard = new Dialog(BankCardActivity.this, R.style.Dialog);
				View temp = getLayoutInflater().inflate(
						R.layout.dialog_add_bank_card, null);
				password = (EditText) temp
						.findViewById(R.id.et_addbankcard_password);
				cardNumber = (EditText) temp
						.findViewById(R.id.et_addbankcard_card_number);
				add = (Button) temp.findViewById(R.id.btn_addbankcard_add);
				cancel = (Button) temp
						.findViewById(R.id.btn_addbankcard_cancel);
				add.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (pd == null) {
							pd = new ProgressDialog(BankCardActivity.this);
						}
						pd.setMessage("Adding..");
						pd.show();
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("password", password.getText().toString());
						map.put("cardNumber", cardNumber.getText().toString());
						Task task = new Task(Task.TASK_ADD_BANK_CARD, map);
						MainService.newTask(task);
						// HashMap<String, Object> map = new HashMap<String,
						// Object>();
						// map.put("text", bank.getText().toString() + ":" +
						// cardNumber.getText().toString());
						// al.add(map);
						// adapter.notifyDataSetChanged();
						addBankCard.cancel();
					}

				});
				cancel.setOnClickListener(new Button.OnClickListener() {

					@Override
					public void onClick(View v) {
						addBankCard.cancel();
					}
				});
				WindowManager.LayoutParams winlp = addBankCard.getWindow()
						.getAttributes();
				winlp.alpha = 0.9f; // 0.0-1.0
				addBankCard.addContentView(temp, winlp);
				addBankCard.show();

			}

		});
		bindAdapter();

		if (MainService.getFuturePayment().getUser().getBankCardList().size() == 0) {
			if (this.pd == null) {
				this.pd = new ProgressDialog(this);
			}
			pd.setMessage("Loading..");
			pd.show();

			Task task = new Task(Task.TASK_GET_BANK_CARD);
			MainService.newTask(task);
		} else {
			bindAdapter();
		}

	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu)
	// {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.activity_bank_card, menu);
	// return true;
	// }
	private void bindAdapter() {
		LinkedList<BankCard> bcl = MainService.getFuturePayment().getUser()
				.getBankCardList();
		al = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < bcl.size(); i++) {
			BankCard bc = bcl.get(i);
			HashMap<String, Object> map = new HashMap<String, Object>();
			// 加入图片
			map.put("text", bc.getBankName() + ":" + bc.getCardNumber());
			al.add(map);
		}
		adapter = new MyListViewAdapter(this, al);
		bankCardList.setAdapter(adapter);
		bankCardList.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// 选中某张银行卡
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
		case GET_BANK_CARD_SECCUSS:
			bindAdapter();
			pd.cancel();
			break;
		case GET_BANK_CARD_FAILURE:
			int state = ((PaymentException) param[1]).getResultCode();
			String hint = null;
			switch (state) {
			case ResultCode.EMPTY:
				hint = "No account binded";
				pd.cancel();
				Toast.makeText(this, hint, Toast.LENGTH_SHORT).show();
			default:
				pd.cancel();
				Toast.makeText(this, state + "", Toast.LENGTH_SHORT).show();
			}
			break;
		case ADD_BANK_CARD_SUCCESS:
			BankCard bc = (BankCard) param[1];
			MainService.getFuturePayment().getUser().appentBankCardList(bc);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("text", bc.getBankName() + ":" + bc.getCardNumber());
			al.add(map);
			adapter.notifyDataSetChanged();
			pd.cancel();
			break;
		case ADD_BANK_CARD_FAILURE:
			hint = "Failure to add the bank card";
			Toast.makeText(this, hint, Toast.LENGTH_SHORT).show();
			pd.cancel();
			break;
		}

	}

}
