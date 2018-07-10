package com.project.basicsplit;

import android.os.Bundle;
import android.util.Log;

import com.project.basicsplit.models.Movie;
import com.project.basicsplit.models.SearchResult;
import com.project.basicsplit.models.Trailer;
import com.project.basicsplit.models.TrailerResult;
import com.project.basicsplit.networking.ApiClient;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainPresenter implements MovieAdapter.ClickPlayListener {

    private static final String MOVIES_KEY = "MOVIES_KEY";
    private static final String YOUTUBE = "YouTube";
    private static final String TRAILER = "Trailer";
    private MainPresentation presentation;
    private ApiClient apiClient;
    private List<Subscription> subscriptionList = new ArrayList<>();
    private ArrayList<Movie> nowPlaying;

    @Inject
    public MainPresenter(MainPresentation presentation,
                         ApiClient apiClient) {
        this.presentation = presentation;
        this.apiClient = apiClient;
    }

    void onResume() {
        if (nowPlaying != null) {
            return;
        }

        subscriptionList.add(apiClient.searchMovies()
                .flatMap(new Func1<SearchResult, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> call(SearchResult searchResult) {
                        return Observable.from(searchResult.getResults());
                    }
                })
                // errors about cannot be applied to with "anonymous <Object Boolean>
                // Check the generic type args in upstream operators!
                // don't work with incomplete movie objects
                .filter(new Func1<Movie, Boolean>() {
                    @Override
                    public Boolean call(Movie movie) {
                        return movie.getId().isPresent();
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Movie>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TRACE", "error retrieving movies, " + e.getMessage());
                    }

                    @Override
                    public void onNext(List<Movie> movies) {
                        onMoviesResultSucess(new ArrayList(movies));
                    }
                }));
    }

    void onPause() {
        for (Subscription subscription: subscriptionList) {
            if (subscription != null) {
                subscription.unsubscribe();
            }
        }
    }

    void onDestroy() {
        subscriptionList = null;
    }

    @Override
    public void onPlayClick(String movieId) {
        subscriptionList.add(apiClient.getTrailers(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<TrailerResult, Observable<Trailer>>() {
                    @Override
                    public Observable<Trailer> call(TrailerResult trailerResult) {
                        return Observable.from(trailerResult.getResults());
                    }
                })
                .filter(new Func1<Trailer, Boolean>() {
                    @Override
                    public Boolean call(Trailer trailer) {
                        return trailer.getKey().isPresent() && trailer.getSite().isPresent() &&
                                trailer.getSite().get().equals(YOUTUBE) &&
                                trailer.getType().isPresent() &&
                                trailer.getType().get().equals(TRAILER);
                    }
                })
                .toList()
                .subscribe(new Observer<List<Trailer>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TRACE","Error retrieving trailer"+e.getMessage());
                    }

                    @Override
                    public void onNext(List<Trailer> trailers) {
                        onTrailerResultSuccess(trailers);
                    }
                }));
    }

    void onMoviesResultSucess(ArrayList<Movie> movies) {
        nowPlaying = movies;
        presentation.showList(movies);
    }

    void onTrailerResultSuccess(List<Trailer> trailers) {
        if (trailers.isEmpty()) {
            Log.d("TRACE", "No trailers");
            return;
        }
        presentation.showTrailer(trailers.get(0).getKey().get());
    }
}
