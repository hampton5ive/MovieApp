package com.project.basicsplit.networking;

import android.content.Context;

import com.project.basicsplit.R;
import com.project.basicsplit.models.SearchResult;
import com.project.basicsplit.models.TrailerResult;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class ApiClient {
    // retrofit interface type
    MoviesApi moviesApi;

    public ApiClient(Context context, OkHttpClient okHttpClient) {
        Retrofit moviesRetrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.movie_base_url))
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        moviesApi = moviesRetrofit.create(MoviesApi.class);
    }

    public Observable<SearchResult> searchMovies() {
        return moviesApi.searchMovies();
    }

    public Observable<TrailerResult> getTrailers(String movieId) {
        return moviesApi.getTrailers(movieId);
    }

}
