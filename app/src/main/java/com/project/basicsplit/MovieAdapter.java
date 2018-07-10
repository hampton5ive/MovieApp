package com.project.basicsplit;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.project.basicsplit.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ClickPlayListener listener;
    private Picasso picasso;
    private List<Movie> movies;

    class MovieViewHolder extends RecyclerView.ViewHolder {
        MovieViewHolder(View movieView) {
            super(movieView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        MovieView movieView = new MovieView(context);
        return new MovieViewHolder(movieView);
    }

    // todo: PRACTICE WITHOUT A CUSTOM VIEW TOO
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovieView currentView = (MovieView) holder.itemView;
        currentView.bindTo(movies.get(position), picasso, listener);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public MovieAdapter(ClickPlayListener listener, Picasso picasso,
            List<Movie> movies) {
        this.listener = listener;
        this.picasso = picasso;
        this.movies = movies;
    }

    public interface ClickPlayListener {
        void onPlayClick(String movieId);
    }

}
