package com.billme.widget;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.billme.logic.MainService;
import com.billme.ui.R;
import com.billme.util.FileUtil;
import com.futurePayment.model.Coupon;
import com.futurePayment.model.ImageHelper;

public class MyCouponAdapter extends BaseAdapter {
	private Context context = null;
	private ArrayList<Coupon> list;
	private LayoutInflater inflater = null;

	/**
	 * 
	 * @param context
	 *            the context of current activity
	 * @param list
	 *            list of CommentInfo
	 * 
	 */
	public MyCouponAdapter(Context context, ArrayList<Coupon> list) {
		super();
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = new ViewHolder();
		Coupon c = list.get(position);
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.my_society_list_item, null);
		}
		viewHolder.picture = (ImageView) convertView
				.findViewById(R.id.iv_couponlistitem_picture);
		viewHolder.name = (TextView) convertView
				.findViewById(R.id.tv_couponlistitem_name);
		viewHolder.time = (TextView) convertView
				.findViewById(R.id.tv_couponlistitem_time);
		viewHolder.value = (TextView) convertView
				.findViewById(R.id.tv_couponlistitem_value);
		viewHolder.least = (TextView) convertView
				.findViewById(R.id.tv_couponlistitem_least);
		viewHolder.amount = (TextView) convertView
				.findViewById(R.id.tv_couponlistitem_amount);
		if (c != null) {
			viewHolder.name.setText(c.getEnterpriseName());
			viewHolder.time.setText(c.getStartTime() + " ----- "
					+ c.getEndTime());
			if ("discount".equals(c.getType())) {
				viewHolder.value.setText("打" + c.getValue() + "折");
			} else if ("substitute".equals(c.getType())) {
				viewHolder.value.setText("抵" + c.getValue() + "元");
			}
			viewHolder.least.setText("最低消费" + c.getLeast() + "元");
			viewHolder.amount.setText("最低消费" + c.getLeast() + "元");
			String str = (String) c.getPicture();
			if (str.startsWith("http://")) {
				// 从网络读取
				ImageHelper imageHelper = MainService.getImageHelper();
				Drawable temp = imageHelper.loadDrawable(str,
						viewHolder.picture);
				if (temp != null)
					viewHolder.picture.setImageDrawable(temp);
			} else {
				// 从sd卡读取
				FileUtil fu = new FileUtil(MainService.getUser().getName());
				viewHolder.picture.setImageDrawable(fu.readImageFromSD(str));
			}
		}
		return convertView;
	}

	private class ViewHolder {
		public ImageView picture = null;
		public TextView name = null;
		public TextView time = null;
		public TextView value = null;
		public TextView least = null;
		public TextView amount = null;
	}
}
