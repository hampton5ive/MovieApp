package com.project.basicsplit.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


// DEFENSIVE CODING, take care of nulls!
public class SearchResult implements Parcelable {
    private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }

    protected SearchResult(Parcel in) {
        results = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Creator<SearchResult> CREATOR = new Creator<SearchResult>() {
        @Override
        public SearchResult createFromParcel(Parcel in) {
            return new SearchResult(in);
        }

        @Override
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(results);
    }
}
