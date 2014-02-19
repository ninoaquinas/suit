package com.idiota.suit.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FriendPreview implements Parcelable {
	private String mUid;
	private String mName;
	private String mPic;
	public String getUid() {
		return mUid;
	}
	public void setUid(String mUid) {
		this.mUid = mUid;
	}
	public String getName() {
		return mName;
	}
	public void setName(String mName) {
		this.mName = mName;
	}
	public String getPic_square() {
		return mPic;
	}
	public void setPic_square(String mPic) {
		this.mPic = mPic;
	}
	
	/* Implements Parcelable */
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mUid);
		dest.writeString(mName);
		dest.writeString(mPic);
	}
	public FriendPreview(Parcel parcel) {
		mUid = parcel.readString();
		mName = parcel.readString();
		mPic = parcel.readString();
	}
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { 
		public FriendPreview createFromParcel(Parcel in) { return new FriendPreview(in); }   
		public FriendPreview[] newArray(int size) { return new FriendPreview[size]; } 
	};
	
	/* Dummy constructor for jackson */
	public FriendPreview() {}
}
