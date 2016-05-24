package com.mayaswell.trailers;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by dak on 5/22/2016.
 */
public class Upcoming implements Parcelable {
	ArrayList<TrailerSet> sets = new ArrayList<TrailerSet>();

	public static final Parcelable.Creator<Upcoming> CREATOR = new Parcelable.Creator<Upcoming>() {

		@Override
		public Upcoming createFromParcel(Parcel source) {
			return new Upcoming(source);
		}

		@Override
		public Upcoming[] newArray(int size) {
			return new Upcoming[size];
		}
	};

	public Upcoming(Parcel source) {
	}

	public Upcoming() {

	}

	public void add(TrailerSet ts) {
		sets.add(ts);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}
}
