package com.billme.ui;

import java.sql.Date;
import java.util.HashMap;

import com.billme.logic.BillMeActivity;
import com.billme.logic.MainService;
import com.futurePayment.constant.Task;

import android.os.Bundle;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegistActivity extends BaseActivity implements BillMeActivity {

	private EditText name = null;
	private EditText loginPassword1 = null;
	private EditText loginPassword2 = null;
	private EditText payPassword1 = null;
	private EditText payPassword2 = null;
	private EditText realName = null;
	private EditText phone = null;
	private EditText email = null;
	private ImageButton boyButton = null;
	private ImageButton girlButton = null;
	private Button submitButton = null;
	private DatePicker birthday = null;

	private Drawable click = null;
	private Drawable blank = null;
	private Drawable wrong = null;

	public static final int REGIST_SUCCESS = 1;
	public static final int NAME_AVAILABLE = 2;
	public static final int REGIST_FAILURE = -1;
	public static final int NAME_NOT_AVAILABLE = -2;

	boolean lpSame = false;
	boolean ppSame = false;
	boolean nAvailable = false;
	boolean sexChoice = true;// true represents boy,false represents girl

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		setTitle("ע��");

		name = (EditText) findViewById(R.id.et_regist_name);
		loginPassword1 = (EditText) findViewById(R.id.et_regist_password1);
		loginPassword2 = (EditText) findViewById(R.id.et_regist_password2);
		payPassword1 = (EditText) findViewById(R.id.et_regist_pay_password1);
		payPassword2 = (EditText) findViewById(R.id.et_regist_pay_password2);
		realName = (EditText) findViewById(R.id.et_regist_realName);
		phone = (EditText) findViewById(R.id.et_regist_phone);
		email = (EditText) findViewById(R.id.et_regist_email);
		boyButton = (ImageButton) findViewById(R.id.btn_regist_boy);
		girlButton = (ImageButton) findViewById(R.id.btn_regist_girl);
		submitButton = (Button) findViewById(R.id.regist_submit_button);
		birthday = (DatePicker) findViewById(R.id.regist_birthday);
		name.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if (!arg1) {
					String temp = name.getText().toString();
					if (temp != null) {
						HashMap<String, String> param = new HashMap<String, String>();
						param.put("name", temp);
						Task task = new Task(Task.TASK_CHECK_NAME_AVAILABLE,
								param);
						MainService.newTask(task);
					}
				}

			}

		});
		loginPassword2.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean arg1) {
				// TODO Auto-generated method stub
				if (!arg1) {
					String temp1 = loginPassword1.getText().toString();
					String temp2 = loginPassword2.getText().toString();
					if (!"".equals(temp1) && !"".equals(temp2)) {
						if (temp1.equals(temp2)) {
							click = getResources()
									.getDrawable(R.drawable.click);
							loginPassword1.setCompoundDrawables(null, null,
									click, null);
							lpSame = true;
						} else {
							wrong = getResources()
									.getDrawable(R.drawable.wrong);
							loginPassword1.setCompoundDrawables(null, null,
									wrong, null);
							lpSame = false;
						}
					}
				}
			}

		});
		payPassword2.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean arg1) {
				// TODO Auto-generated method stub
				if (!arg1) {
					String temp1 = payPassword1.getText().toString();
					String temp2 = payPassword2.getText().toString();
					if (!"".equals(temp1) && !"".equals(temp2)) {
						if (temp1.equals(temp2)) {
							click = getResources()
									.getDrawable(R.drawable.click);
							payPassword1.setCompoundDrawables(null, null,
									click, null);
							ppSame = true;
						} else {
							wrong = getResources()
									.getDrawable(R.drawable.wrong);
							payPassword1.setCompoundDrawables(null, null,
									wrong, null);
							ppSame = false;
						}
					}
				}
			}
		});
		boyButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!boyButton.getDrawable().equals(click)) {
					click = getResources().getDrawable(R.drawable.click);
					blank = getResources().getDrawable(R.drawable.blank);
					boyButton.setImageDrawable(click);
					girlButton.setImageDrawable(blank);
					sexChoice = true;
				}
			}
		});
		girlButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!girlButton.getDrawable().equals(click)) {
					click = getResources().getDrawable(R.drawable.click);
					blank = getResources().getDrawable(R.drawable.blank);
					girlButton.setImageDrawable(click);
					boyButton.setImageDrawable(blank);
					sexChoice = false;
				}
			}
		});
		submitButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String n = name.getText().toString();
				String lp1 = loginPassword1.getText().toString();
				String lp2 = loginPassword2.getText().toString();
				String pp1 = payPassword1.getText().toString();
				String pp2 = payPassword2.getText().toString();
				String rn = realName.getText().toString();
				String p = phone.getText().toString();
				String e = email.getText().toString();
				String s = null;
				if (sexChoice)
					s = "��";
				else
					s = "Ů";
				@SuppressWarnings("deprecation")
				Date date = new Date(birthday.getYear() - 1900, birthday
						.getMonth(), birthday.getDayOfMonth());
				Log.i("test", date.toString());

				if (n != null && lp1 != null && lp2 != null && pp1 != null
						&& pp2 != null && rn != null && p != null && e != null) {
					if (lpSame && ppSame && nAvailable) {
						HashMap<String, Object> param = new HashMap<String, Object>();
						param.put("name", n);
						param.put("loginPassword", lp1);
						param.put("payPassword", pp1);
						param.put("realName", rn);
						param.put("phone", p);
						param.put("email", e);
						param.put("sex", s);
						param.put("birthday", date);
						Task task = new Task(Task.TASK_USER_REGIST, param);
						MainService.newTask(task);
					}
				} else {
					Toast.makeText(RegistActivity.this, "����д����ע����Ϣ",
							Toast.LENGTH_SHORT).show();
				}

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
		case REGIST_SUCCESS:
			Toast.makeText(this, "ע��ɹ�", Toast.LENGTH_SHORT).show();
			finish();
			break;
		case NAME_AVAILABLE:
			name.setCompoundDrawables(null, null, click, null);
			nAvailable = true;
			break;
		case REGIST_FAILURE:
			Toast.makeText(this, "ע��ʧ��", Toast.LENGTH_SHORT).show();
			break;
		case NAME_NOT_AVAILABLE:
			name.setCompoundDrawables(null, null, wrong, null);
			nAvailable = false;
			break;
		}
	}

}
