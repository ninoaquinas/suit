package com.idiota.suit.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Suit  implements Parcelable {
	public static enum Play {GAJAH, ORANG, SEMUT};
	public static enum Result {LOSE, DRAW, WIN};
	
	private Play mPlay;
	
	public Suit(Play play) {
		mPlay = play;
	}
	
	public void setPlay(Play play) {
		mPlay = play;
	}
	
	public Play getPlay() {
		return mPlay;
	}
	
	/* Suit Rules */
	public static Result getMatchupResult(Play myPlay, Play oppPlay) {
		if (myPlay.equals(oppPlay)) return Result.DRAW;
		if ((myPlay == Play.GAJAH && oppPlay == Play.ORANG) ||
			(myPlay == Play.ORANG && oppPlay == Play.SEMUT) ||
			(myPlay == Play.SEMUT && oppPlay == Play.GAJAH)) return Result.WIN;
		return Result.LOSE;
	}

	/* Implements Parcelable */
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mPlay.name());
	}
	public Suit(Parcel parcel) {
		mPlay = Play.valueOf(parcel.readString());
	}
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { 
		public Suit createFromParcel(Parcel in) { return new Suit(in); }   
		public Suit[] newArray(int size) { return new Suit[size]; } 
	};

}
