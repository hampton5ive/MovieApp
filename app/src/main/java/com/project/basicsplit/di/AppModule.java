package com.project.basicsplit.di;

import android.app.Application;
import android.content.Context;

import com.project.basicsplit.networking.ApiClient;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Module
public class AppModule {
    private static final String API_KEY_KEY = "api_key";
    private static final String API_KEY_VALUE = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return app.getApplicationContext();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttp() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url()
                                .newBuilder()
                                .addQueryParameter(API_KEY_KEY, API_KEY_VALUE)
                                .build();
                        request = request.newBuilder()
                                .url(url)
                                .build();
                        return chain.proceed(request);

                    }
                }).build();
    }

    @Provides
    @Singleton
    ApiClient provideApiClient(Context context, OkHttpClient client) {
        return new ApiClient(context, client);
    }

    @Provides
    @Singleton
    Picasso providePicasso() {
        return new Picasso.Builder(app).build();
    }
}
