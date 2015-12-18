package com.billme.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.billme.logic.BillMeActivity;
import com.billme.logic.MainService;
import com.billme.widget.MyListViewAdapter;
import com.futurePayment.model.BankCard;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class ManagementActivity extends BaseActivity implements BillMeActivity {

	private ImageButton head = null;
	private TextView name = null;
	private ListView list = null;
	private MyListViewAdapter adapter = null;
	private ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_management);
		addLayout();

		head = (ImageButton) findViewById(R.id.ib_management_head);
		name = (TextView) findViewById(R.id.tv_management_name);
		list = (ListView) findViewById(R.id.lv_management_list);

		head.setBackgroundResource(R.drawable.back);
		name.setText(MainService.getUser().getName());
		bindAdapter();
	}

	private void bindAdapter() {
		al = new ArrayList<HashMap<String, Object>>();
		// ����ͼƬ
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("icon", R.drawable.back);
		map1.put("text", "�˺����	��" + MainService.getUser().getBalance());
		map1.put("end", R.drawable.back);
		al.add(map1);
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("icon", R.drawable.back);
		map2.put("text", "�˻�����	��" + MainService.getUser().getGrade());
		map2.put("end", R.drawable.back);
		al.add(map2);
		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("icon", R.drawable.back);
		map3.put("text", "���Ż�ȯ");
		map3.put("end", R.drawable.back);
		al.add(map3);
		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("icon", R.drawable.back);
		map4.put("text", "���˵�");
		map4.put("end", R.drawable.back);
		al.add(map4);
		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("icon", R.drawable.back);
		map5.put("text", "�������п�");
		map5.put("end", R.drawable.back);
		al.add(map5);
		HashMap<String, Object> map6 = new HashMap<String, Object>();
		map6.put("icon", R.drawable.back);
		map6.put("text", "�罻����");
		map6.put("end", R.drawable.back);
		al.add(map6);
		HashMap<String, Object> map7 = new HashMap<String, Object>();
		map7.put("icon", R.drawable.back);
		map7.put("text", "��������");
		map7.put("end", R.drawable.back);
		al.add(map7);

		adapter = new MyListViewAdapter(this, al);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				switch (arg2) {
				case 0: {
					// ѡ���˺����
					intent.setClass(ManagementActivity.this,
							BankCardActivity.class);
					break;
				}
				case 1: {
					// ѡ���˻�����
					intent.setClass(ManagementActivity.this,
							GradeActivity.class);
					break;
				}
				case 2: {
					// ѡ�в��Ż�ȯ
					intent.setClass(ManagementActivity.this,
							CouponActivity.class);
					break;
				}
				case 3: {
					// ѡ�в��˵�
					intent.setClass(ManagementActivity.this,
							BankCardActivity.class);
					break;
				}
				case 4: {
					// ѡ�й������п�
					intent.setClass(ManagementActivity.this,
							BankCardActivity.class);
					break;
				}
				case 5: {
					// ѡ���罻����
					intent.setClass(ManagementActivity.this,
							RelationActivity.class);
					break;
				}
				case 6: {
					// ѡ�и�������
					intent.setClass(ManagementActivity.this,
							BankCardActivity.class);
					break;
				}
				default:
					break;
				}
				startActivity(intent);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_management, menu);
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
