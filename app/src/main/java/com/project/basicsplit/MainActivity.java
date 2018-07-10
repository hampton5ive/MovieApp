package com.project.basicsplit;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.project.basicsplit.di.AppComponent;
import com.project.basicsplit.di.MainModule;
import com.project.basicsplit.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainPresentation {

    private static final String KEY_RECYCLER_STATE = "KEY_RECYCLER_STATE";
    RecyclerView recyclerView;
    TextView counterTv;
    MovieAdapter adapter;

    @Inject
    MainPresenter presenter;

    @Inject
    Picasso picasso;

    private static final String ACTION_TYPE = "video/mp4";
    private static final String YOUTUBE_PREFIX = "https://www.youtube.com/watch?v=";
    private static final String APP_PREFIX = "vnd.youtube:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MovieAdapter(presenter, picasso, new ArrayList<Movie>());
        recyclerView.setAdapter(adapter);
        // NEED to case the view or it won't find it!!!
        /*counterBtn = (Button)findViewById(R.id.counterBtn);
        counterTv = (TextView)findViewById(R.id.counterTv);
        counterBtn.setOnClickListener(this);*/

        AppComponent component = BasicApplication.get(this).getAppComponent();
        component.mainComponent(new MainModule(this)).inject(this);

        if (savedInstanceState != null) {
            Parcelable listState = savedInstanceState.getParcelable(KEY_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(KEY_RECYCLER_STATE, listState);
    }

    @Override
    public void showList(List<Movie> list) {
        adapter = new MovieAdapter(presenter, picasso, list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateCounterView(int count) {
        counterTv.setText("" + count);
    }

    @Override
    public void showTrailer(String key) {
        Uri youtubeUri = Uri.parse(APP_PREFIX + key);
        Intent appIntent = new Intent(Intent.ACTION_VIEW, youtubeUri);

        Uri webUri = Uri.parse(YOUTUBE_PREFIX + key);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webUri);

        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}
