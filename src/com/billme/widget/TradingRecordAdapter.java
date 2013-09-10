package com.billme.widget;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.billme.ui.R;
import com.billme.widget.TradingRecordListView.PinnedHeaderAdapter;
import com.futurePayment.model.ImageHelper;
import com.futurePayment.model.TradeRecord;

public class TradingRecordAdapter extends BaseExpandableListAdapter implements
		PinnedHeaderAdapter {

	private ArrayList<String[]> group;
	private ArrayList<ArrayList<String[]>> item;
	private ArrayList<TradeRecord> trList;
	// private Context context;
	private LayoutInflater inflater;
	private TradingRecordListView listView;
	private ImageHelper ih = ImageHelper.getInstance();

	public TradingRecordAdapter(Context context, ArrayList<String[]> group,
			ArrayList<ArrayList<String[]>> item, TradingRecordListView listView) {
		// this.context = context;
		inflater = LayoutInflater.from(context);
		this.group = group;
		this.item = item;
		this.listView = listView;
	}

	public TradingRecordAdapter(Context context, ArrayList<TradeRecord> tr,
			TradingRecordListView listView) {
		inflater = LayoutInflater.from(context);
		this.listView = listView;
		this.trList = tr;

		ArrayList<String[]> tempGroup = new ArrayList<String[]>();
		ArrayList<ArrayList<String[]>> tempItem = new ArrayList<ArrayList<String[]>>();
		ArrayList<String[]> temp = new ArrayList<String[]>();
		ArrayList<TradeRecord> tempTradeRecordList = tr;
		double income = 0;
		double outcome = 0;
		String currMounth = tempTradeRecordList.get(0).getMounth();
		for (int i = 0; i < tempTradeRecordList.size(); ++i) {
			TradeRecord tempTradeRecord = tempTradeRecordList.get(i);

			String[] tempStr;
			if (currMounth != tempTradeRecord.getMounth()) {
				tempItem.add(temp);

				tempStr = new String[] { currMounth, String.valueOf(income),
						String.valueOf(outcome) };
				tempGroup.add(tempStr);

				income = 0;
				outcome = 0;
				currMounth = tempTradeRecord.getMounth();
				temp = new ArrayList<String[]>();
			}

			tempStr = new String[] { tempTradeRecord.getTitle(),
					tempTradeRecord.getDate(),
					String.valueOf(tempTradeRecord.getAmount()),
					tempTradeRecord.getState(),
					tempTradeRecord.getReceiverPic(),
					tempTradeRecord.getReceiver() };
			temp.add(tempStr);

			if (tempTradeRecord.getType() == 1) {
				income += tempTradeRecord.getAmount();
			} else if (tempTradeRecord.getType() == -1) {
				outcome += tempTradeRecord.getAmount();
			}
		}

		this.group = tempGroup;
		this.item = tempItem;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return item.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return groupPosition * 10000 + childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = inflater
					.inflate(R.layout.trading_record_listview_item, null);
		}
		TextView recordName = (TextView) view
				.findViewById(R.id.tv_TRLVitem_record_name);
		TextView tradeDate = (TextView) view
				.findViewById(R.id.tv_TRLVitem_trading_date);
		TextView tradePrice = (TextView) view
				.findViewById(R.id.tv_TRLVitem_trading_price);
		TextView tradeState = (TextView) view
				.findViewById(R.id.tv_TRLVitem_trading_state);
		ImageView pic = (ImageView) view
				.findViewById(R.id.iv_TRLVitem_enterprise_pic);
		TextView reciver = (TextView) view
				.findViewById(R.id.tv_TRLVitem_enterprise_name);
		String[] tempItem = item.get(groupPosition).get(childPosition);
		recordName.setText(tempItem[0]);
		tradeDate.setText(tempItem[1]);
		tradePrice.setText(tempItem[2]);
		tradeState.setText(tempItem[3]);
		ih.loadDrawable(tempItem[4], pic);
		reciver.setText(tempItem[5]);
		// recordName.setOnClickListener(new ChildOnClickListener());
		// tradeDate.setOnClickListener(new ChildOnClickListener());
		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (groupPosition > -1 && groupPosition < item.size()) {
			return item.get(groupPosition).size();
		}
		return -1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return group.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return group.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.trading_record_listview_group,
					null);
		}
		String[] tempItem = group.get(groupPosition);
		((TextView) view.findViewById(R.id.tv_TRLVgroup_mounth_label))
				.setText(tempItem[0]);
		((TextView) view.findViewById(R.id.tv_TRLVgroup_income_amount_label))
				.setText(tempItem[1]);
		((TextView) view.findViewById(R.id.tv_TRLVgroup_outcome_amount_label))
				.setText(tempItem[2]);

		return view;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public int getPinnedHeaderState(int groupPosition, int childPosition) {
		final int childCount = getChildrenCount(groupPosition);

		if (childPosition == (childCount - 1)) {
			return PINNED_HEADER_PUSHED_UP;
		} else if (childPosition == -1
				&& !listView.isGroupExpanded(groupPosition)) {
			return PINNED_HEADER_GONE;
		} else {

			return PINNED_HEADER_VISIBLE;
		}
	}

	@Override
	public void configurePinnedHeader(View header, int groupPosition,
			int childPosition, int alpha) {

		if (groupPosition > -1 && groupPosition < group.size()) {
			String[] str = group.get(groupPosition);
			((TextView) header.findViewById(R.id.tv_TRLVgroup_mounth_label))
					.setText(str[0]);
			((TextView) header
					.findViewById(R.id.tv_TRLVgroup_income_amount_label))
					.setText(str[1]);
			((TextView) header
					.findViewById(R.id.tv_TRLVgroup_outcome_amount_label))
					.setText(str[2]);
		}
	}

	public void refreshRecord(ArrayList<TradeRecord> tr) {
		this.trList.addAll(0, tr);

		ArrayList<String[]> tempGroup = new ArrayList<String[]>();
		ArrayList<ArrayList<String[]>> tempItem = new ArrayList<ArrayList<String[]>>();
		ArrayList<String[]> temp = new ArrayList<String[]>();
		ArrayList<TradeRecord> tempTradeRecordList = tr;
		double income = 0;
		double outcome = 0;
		String currMounth = tempTradeRecordList.get(0).getMounth();
		for (int i = 0; i < tempTradeRecordList.size(); ++i) {
			TradeRecord tempTradeRecord = tempTradeRecordList.get(i);

			String[] tempStr;
			if (currMounth != tempTradeRecord.getMounth()) {
				tempItem.add(temp);

				tempStr = new String[] { currMounth, String.valueOf(income),
						String.valueOf(outcome) };
				tempGroup.add(tempStr);

				income = 0;
				outcome = 0;
				currMounth = tempTradeRecord.getMounth();
				temp = new ArrayList<String[]>();
			}

			tempStr = new String[] { tempTradeRecord.getTitle(),
					tempTradeRecord.getDate(),
					String.valueOf(tempTradeRecord.getAmount()),
					tempTradeRecord.getState(),
					tempTradeRecord.getReceiverPic(),
					tempTradeRecord.getReceiver() };
			temp.add(tempStr);

			if (tempTradeRecord.getType() == 1) {
				income += tempTradeRecord.getAmount();
			} else if (tempTradeRecord.getType() == -1) {
				outcome += tempTradeRecord.getAmount();
			}
		}

		this.group.addAll(0, tempGroup);
		this.item.addAll(0, tempItem);
	}

	public void loadRecord(ArrayList<TradeRecord> tr) {
		this.trList.addAll(tr);

		ArrayList<String[]> tempGroup = new ArrayList<String[]>();
		ArrayList<ArrayList<String[]>> tempItem = new ArrayList<ArrayList<String[]>>();
		ArrayList<String[]> temp = new ArrayList<String[]>();
		ArrayList<TradeRecord> tempTradeRecordList = tr;
		double income = 0;
		double outcome = 0;
		String currMounth = tempTradeRecordList.get(0).getMounth();
		for (int i = 0; i < tempTradeRecordList.size(); ++i) {
			TradeRecord tempTradeRecord = tempTradeRecordList.get(i);

			String[] tempStr;
			if (currMounth != tempTradeRecord.getMounth()) {
				tempItem.add(temp);

				tempStr = new String[] { currMounth, String.valueOf(income),
						String.valueOf(outcome) };
				tempGroup.add(tempStr);

				income = 0;
				outcome = 0;
				currMounth = tempTradeRecord.getMounth();
				temp = new ArrayList<String[]>();
			}

			tempStr = new String[] { tempTradeRecord.getTitle(),
					tempTradeRecord.getDate(),
					String.valueOf(tempTradeRecord.getAmount()),
					tempTradeRecord.getState(),
					tempTradeRecord.getReceiverPic(),
					tempTradeRecord.getReceiver() };
			temp.add(tempStr);

			if (tempTradeRecord.getType() == 1) {
				income += tempTradeRecord.getAmount();
			} else if (tempTradeRecord.getType() == -1) {
				outcome += tempTradeRecord.getAmount();
			}
		}

		this.group.addAll(tempGroup);
		this.item.addAll(tempItem);
	}

	public void sort() {
		TradeRecord tempTR;
		for (int i = 0; i < this.trList.size(); ++i) {
			for (int j = i; j < this.trList.size() - 1; ++j) {
				int year1 = Integer.valueOf(trList.get(j).getYear());
				int year2 = Integer.valueOf(trList.get(j + 1).getYear());
				if (year1 > year2) {
					tempTR = this.trList.get(j);
					this.trList.set(j, trList.get(j + 1));
					this.trList.set(j + 1, tempTR);
				} else {
					int mounth1 = Integer.valueOf(trList.get(j).getMounth());
					int mounth2 = Integer
							.valueOf(trList.get(j + 1).getMounth());
					if (mounth1 > mounth2) {
						tempTR = this.trList.get(j);
						this.trList.set(j, trList.get(j + 1));
						this.trList.set(j + 1, tempTR);
					} else {
						int day1 = Integer.valueOf(trList.get(j).getDay());
						int day2 = Integer.valueOf(trList.get(j + 1).getDay());
						if (day1 > day2) {
							tempTR = this.trList.get(j);
							this.trList.set(j, trList.get(j + 1));
							this.trList.set(j + 1, tempTR);
						}
					}
				}
			}
		}
	}

	public void setTradeRecored(ArrayList<TradeRecord> tr) {
		ArrayList<String[]> tempGroup = new ArrayList<String[]>();
		ArrayList<ArrayList<String[]>> tempItem = new ArrayList<ArrayList<String[]>>();
		ArrayList<String[]> temp = new ArrayList<String[]>();
		ArrayList<TradeRecord> tempTradeRecordList = tr;
		double income = 0;
		double outcome = 0;
		String currMounth = tempTradeRecordList.get(0).getMounth();
		for (int i = 0; i < tempTradeRecordList.size(); ++i) {
			TradeRecord tempTradeRecord = tempTradeRecordList.get(i);

			String[] tempStr;
			if (currMounth != tempTradeRecord.getMounth()) {
				tempItem.add(temp);

				tempStr = new String[] { currMounth, String.valueOf(income),
						String.valueOf(outcome) };
				tempGroup.add(tempStr);

				income = 0;
				outcome = 0;
				currMounth = tempTradeRecord.getMounth();
				temp = new ArrayList<String[]>();
			}

			tempStr = new String[] { tempTradeRecord.getTitle(),
					tempTradeRecord.getDate(),
					String.valueOf(tempTradeRecord.getAmount()),
					tempTradeRecord.getState(),
					tempTradeRecord.getReceiverPic(),
					tempTradeRecord.getReceiver() };
			temp.add(tempStr);

			if (tempTradeRecord.getType() == 1) {
				income += tempTradeRecord.getAmount();
			} else if (tempTradeRecord.getType() == -1) {
				outcome += tempTradeRecord.getAmount();
			}
		}
		this.group = tempGroup;
		this.item = tempItem;
	}

	class ChildOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Log.i("error", "child has been touch");
		}

	}

}
