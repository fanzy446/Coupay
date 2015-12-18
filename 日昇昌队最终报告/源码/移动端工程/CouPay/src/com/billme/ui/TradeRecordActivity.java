package com.billme.ui;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.billme.logic.BillMeActivity;
import com.billme.logic.MainService;
import com.billme.util.DateUtil;
import com.billme.widget.TradingRecordAdapter;
import com.billme.widget.TradingRecordListView;
import com.billme.widget.TradingRecordListView.IXListViewListener;
import com.futurePayment.constant.Task;
import com.futurePayment.model.TradeRecord;

public class TradeRecordActivity extends BaseActivity implements
		BillMeActivity, IXListViewListener {

	public static final int INITRECORD_SECCUSS = -100;
	public static final int REFRESHRECORD_SECCUSS = -102;
	public static final int LOADRECORD_SECCUSS = -103;
	public static final int GETRECORD_FAILED = -101;

	private TradingRecordListView expandableListView;
	private TradingRecordAdapter adapter;
	private Dialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trade_record);
		addTitle("TradingRecord");

		expandableListView = (TradingRecordListView) findViewById(R.id.lv_trading_records);

		init();

		// expandableListView.setPinnedHeaderView(getLayoutInflater().inflate(
		// R.layout.trading_record_group_header, expandableListView, false));
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
//		if (expandableListView != null && !expandableListView.isGroupExpanded(0))
//			expandableListView.expandGroup(0);
	}

	@Override
	public void init() {
		showRoundProcessDialog(this, R.layout.loading_process_dialog_color);

		Task task = new Task(Task.TASK_GET_TRADING_REACORD);
		MainService.newTask(task);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... param) {
		switch (((Integer) param[0]).intValue()) {
		case INITRECORD_SECCUSS: {
			Log.i("error", "refresh trade record");
			Log.i("error", ((ArrayList<TradeRecord>) param[1]).toString());
			adapter = new TradingRecordAdapter(this,
					(ArrayList<TradeRecord>) param[1], expandableListView);

			expandableListView.setAdapter(adapter);
			expandableListView.setXListViewListener(this);
			pd.cancel();
		}
			break;
		case REFRESHRECORD_SECCUSS: {
			adapter.refreshRecord((ArrayList<TradeRecord>) param[1]);
			adapter.notifyDataSetChanged();
			expandableListView.stopRefresh();
			expandableListView.setRefreshTime(DateUtil.getDate());
		}
			break;
		case LOADRECORD_SECCUSS: {
			adapter.loadRecord((ArrayList<TradeRecord>) param[1]);
			adapter.notifyDataSetChanged();
			expandableListView.stopLoadMore();
		}
			break;
		case GETRECORD_FAILED:

			break;
		}

	}

	@Override
	public void onRefresh() {
		Task task = new Task(Task.TASK_REFRESH_TRADING_REACORD);
		MainService.newTask(task);
	}

	@Override
	public void onLoadMore() {
		Task task = new Task(Task.TASK_LOAD_TRADING_REACORD);
		MainService.newTask(task);
	}

	public void showRoundProcessDialog(Context mContext, int layout) {
		OnKeyListener keyListener = new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_HOME
						|| keyCode == KeyEvent.KEYCODE_SEARCH) {
					return true;
				}
				return false;
			}
		};

		pd = new AlertDialog.Builder(mContext).create();
		pd.setOnKeyListener(keyListener);
		pd.show();
		// 注意此处要放在show之后 否则会报异常
		pd.setContentView(layout);
	}
}
