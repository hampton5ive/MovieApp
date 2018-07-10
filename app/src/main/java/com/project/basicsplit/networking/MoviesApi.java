package com.project.basicsplit.networking;

import com.project.basicsplit.models.SearchResult;
import com.project.basicsplit.models.TrailerResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface MoviesApi {
    @GET("movie/now_playing")
    Observable<SearchResult> searchMovies();

    @GET("movie/{movie_id}/videos")
    Observable<TrailerResult> getTrailers(@Path("movie_id") String movieId);
}
