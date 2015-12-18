package com.billme.logic;

import java.sql.Date;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MyMessage  implements Parcelable{
	private int pushType;
	private String data;
	private String date;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String toString() {
		String s = "{pushType:" + pushType + "data:" + data + ",date:" + date + "}";
		return s;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getPushType() {
		return pushType;
	}

	public void setPushType(int pushType) {
		this.pushType = pushType;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(pushType);
		dest.writeString(data);
		dest.writeString(date);
	}

	public static final Parcelable.Creator<MyMessage> CREATOR = new Creator<MyMessage>() {
		public MyMessage createFromParcel(Parcel source) {
			MyMessage myMessage = new MyMessage();
			myMessage.pushType = source.readInt();
			myMessage.data = source.readString();
			myMessage.date = source.readString();			
			return myMessage;
		}

		public MyMessage[] newArray(int size) {
			return new MyMessage[size];
		}
	};
	
}
