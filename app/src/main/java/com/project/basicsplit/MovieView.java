package com.project.basicsplit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.basicsplit.models.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class MovieView extends RelativeLayout {

    @BindView(R.id.poster)
    ImageView poster;

    @BindView(R.id.movieTitle)
    TextView title;

    @BindView(R.id.movieDescription)
    TextView description;

    private Movie movie;
    private MovieAdapter.ClickPlayListener listener;

    public MovieView(Context context) {
        super(context);
        init();
    }

    // this ctor is needed to prevent an inflation exception if you embed in xml and inflate layout
    // attributeSet like layout_width
    // https://developer.android.com/training/custom-views/create-view.html
    public MovieView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public MovieView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        init();
    }

    public void init() {
        View view = inflate(getContext(), R.layout.view_movie, this);
        ButterKnife.bind(this, view);
    }

    public void bindTo(@NonNull Movie movie, @NonNull Picasso picasso,
            @NonNull MovieAdapter.ClickPlayListener listener) {
        this.movie = movie;
        this.listener = listener;
        if (movie.getPosterUrl().isPresent()) {
            picasso.load(movie.getPosterUrl().get()).into(poster);
        }

        if (movie.getTitle().isPresent()) {
            title.setText(movie.getTitle().get());
        }

        if (movie.getOverview().isPresent()) {
            description.setText(movie.getOverview().get());
        }
    }

    @Optional
    @OnClick(R.id.playBtn)
    public void onPlay() {
        if (movie.getId().isPresent()) {
            listener.onPlayClick(movie.getId().get());
        }
        // launch movie intent
        /*if (movie.getTr.isPresent()) {
            Uri uri = Uri.parse(movie.get.get());
            listener.onPlayClick(uri);
        }*/
    }
}
