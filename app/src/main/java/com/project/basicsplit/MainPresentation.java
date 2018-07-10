package com.project.basicsplit;

import android.net.Uri;

import com.project.basicsplit.models.Movie;

import java.util.List;

public interface MainPresentation {
    void showList(List<Movie> list);
    void showTrailer(String key);
    void updateCounterView(int count);
}
