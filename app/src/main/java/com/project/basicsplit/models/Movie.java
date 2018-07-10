package com.project.basicsplit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Optional;
import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

    private static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500";

    public Optional<String> getId() {
        return id != null ? Optional.of(id) : Optional.<String>absent();
    }

    public Optional<String> getPosterUrl() {
        return posterUrl != null ? Optional.of(POSTER_BASE_URL + posterUrl) : Optional.<String>absent();
    }

    public Optional<String> getTitle() {
        return title != null ? Optional.of(title) : Optional.<String>absent();
    }

    public Optional<String> getOverview() {
        return overview != null ? Optional.of(overview) : Optional.<String>absent();
    }

    public static Creator<Movie> getCREATOR() {
        return CREATOR;
    }

    private String id;
    @SerializedName("poster_path")
    private String posterUrl;
    private String title;
    private String overview;

    protected Movie(Parcel in) {
        id = in.readString();
        posterUrl = in.readString();
        title = in.readString();
        overview = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(posterUrl);
        parcel.writeString(title);
        parcel.writeString(overview);
    }
}
