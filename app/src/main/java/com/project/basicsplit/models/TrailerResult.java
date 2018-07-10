package com.project.basicsplit.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TrailerResult implements Parcelable {
    private List<Trailer> results;

    public List<Trailer> getResults() {
        return results;
    }

    protected TrailerResult(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TrailerResult> CREATOR = new Creator<TrailerResult>() {
        @Override
        public TrailerResult createFromParcel(Parcel in) {
            return new TrailerResult(in);
        }

        @Override
        public TrailerResult[] newArray(int size) {
            return new TrailerResult[size];
        }
    };
}
