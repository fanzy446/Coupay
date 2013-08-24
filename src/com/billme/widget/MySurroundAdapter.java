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
import com.futurePayment.model.CommentInfo;
import com.futurePayment.model.EnterpriseBasicInfo;
import com.futurePayment.model.ImageHelper;

public class MySurroundAdapter extends BaseAdapter {
	private Context context = null;
	private ArrayList<EnterpriseBasicInfo> list;
	private LayoutInflater inflater = null;

	/**
	 * 
	 * @param context
	 *            the context of current activity
	 * @param list list of EnterpriseBasicInfo
	 */
	public MySurroundAdapter(Context context,
			ArrayList<EnterpriseBasicInfo> list) {
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
		EnterpriseBasicInfo ebi = list.get(position);
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.my_society_list_item, null);
		}
		viewHolder.head = (ImageView) convertView
				.findViewById(R.id.iv_surroundlistitem_head);
		viewHolder.name = (TextView) convertView
				.findViewById(R.id.tv_surroundlistitem_name);
		viewHolder.introduction = (TextView) convertView
				.findViewById(R.id.tv_surroundlistitem_introduction);
		viewHolder.distance = (TextView) convertView
				.findViewById(R.id.tv_surroundlistitem_distance);
		if (ebi != null) {			
			viewHolder.name.setText(ebi.getName());
			viewHolder.introduction.setText(ebi.getIntroduction());
			viewHolder.distance.setText(ebi.getDiatance() + "米内");
			
			String str = (String) ebi.getHead();
				if (str.startsWith("http://")) {
					// 从网络读取
					ImageHelper imageHelper = MainService.getImageHelper();
					Drawable temp = imageHelper.loadDrawable(str,
							viewHolder.head);
					if (temp != null)
						viewHolder.head.setImageDrawable(temp);
				} 
				else {
					// 从sd卡读取
					FileUtil fu = new FileUtil(MainService.getUser().getName());
					viewHolder.head.setImageDrawable(fu
							.readImageFromSD(ebi.getHead()));
				}
//				viewHolder.text.setText((String) map.get("text"));
//				viewHolder.end.setImageDrawable(context.getResources()
//						.getDrawable((Integer) (map.get("end"))));				
//				String str1 = (String) ci.getPhoto();
//				if (str.startsWith("http://")) {
//					// 从网络读取
//					ImageHelper imageHelper = MainService.getImageHelper();
//					Drawable temp = imageHelper.loadDrawable(str1,
//							viewHolder.photo);
//					if (temp != null)
//						viewHolder.photo.setImageDrawable(temp);
//				} 
		}
		return convertView;
	}

	private class ViewHolder {
		public ImageView head = null;
		public TextView name = null;
		public TextView introduction = null;
		public TextView distance = null;
	}

}
