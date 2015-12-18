package com.billme.ui;

import java.util.LinkedList;

import com.billme.logic.MainService;
import com.billme.widget.MySurroundAdapter;
import com.futurePayment.constant.ResultCode;
import com.futurePayment.constant.Task;
import com.futurePayment.model.EnterpriseBasicInfo;
import com.futurePayment.model.PaymentException;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class EnterpriseFragment extends Fragment {

	private View view = null;
	private ListView list = null;
	private ProgressDialog pd = null;
	private MySurroundAdapter adapter = null;
	private LinkedList<EnterpriseBasicInfo> el = new LinkedList<EnterpriseBasicInfo>();

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_enterprise, container, false);
		list = (ListView) view.findViewById(R.id.lv_fragment_enterprise);

		if (MainService.getFuturePayment().getUser().getConcernList().size() == 0) {
			if (pd == null) {
				pd = new ProgressDialog(getActivity());
			}
			pd.setMessage("Loading..");
			pd.show();
			Task task = new Task(Task.TASK_GET_ENTERPRISES, null);
			MainService.newTask(task);
		} else {
			bindAdapter();
		}
		// TODO Auto-generated method stub
		return view;
	}

	private void bindAdapter() {
		el = MainService.getFuturePayment().getUser().getConcernList();
		adapter = new MySurroundAdapter(getActivity(), el);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// 选中某个商家
				// Intent intent = new Intent();
				// intent.setClass(SurroundActivity.this, );
			}
		});
	}

	public void refresh(Object... param) {
		switch (((Integer) param[0]).intValue()) {
		case RelationActivity.GET_ENTERPRISE_FAILURE:
			int state = ((PaymentException) param[1]).getResultCode();
			String hint = null;
			switch (state) {
			case ResultCode.EMPTY:
				hint = "No interesting enterprise";
				pd.cancel();
				Toast.makeText(getActivity(), hint, Toast.LENGTH_SHORT).show();
				break;
			default:
				pd.cancel();
				Toast.makeText(getActivity(), "error:" + state,
						Toast.LENGTH_SHORT).show();
			}
		case RelationActivity.GET_ENTERPRISE_SUCCESS:
			bindAdapter();
			pd.cancel();
			break;
		}
	}
}
