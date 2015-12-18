package com.futurePayment.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Friend implements Parcelable {
	private String name = null;
	private String path = null;
	private int size;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{name:" + name + ",path:" + path + ",size:" + size + "}";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(name);
		dest.writeString(path);
		dest.writeInt(size);
	}

	public static final Parcelable.Creator<Friend> CREATOR = new Creator<Friend>() {
		public Friend createFromParcel(Parcel source) {
			Friend friend = new Friend();
			friend.name = source.readString();
			friend.path = source.readString();
			friend.size = source.readInt();
			return friend;
		}

		public Friend[] newArray(int size) {
			return new Friend[size];
		}
	};

}
