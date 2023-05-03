package com.labinot.bajrami.movie_app.activities;

import static com.labinot.bajrami.movie_app.utils.Constants.COUNTRY;
import static com.labinot.bajrami.movie_app.utils.Constants.ID;
import static com.labinot.bajrami.movie_app.utils.Constants.NAME;
import static com.labinot.bajrami.movie_app.utils.Constants.NETWORK;
import static com.labinot.bajrami.movie_app.utils.Constants.START_DATE;
import static com.labinot.bajrami.movie_app.utils.Constants.STATUS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.labinot.bajrami.movie_app.R;
import com.labinot.bajrami.movie_app.adapters.TVShowAdapter;
import com.labinot.bajrami.movie_app.databinding.ActivityMainBinding;
import com.labinot.bajrami.movie_app.listeners.TVShowListeners;
import com.labinot.bajrami.movie_app.models.TVShow;
import com.labinot.bajrami.movie_app.utils.Constants;
import com.labinot.bajrami.movie_app.viewmodels.MostPopularTVShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TVShowListeners {

    private MostPopularTVShowsViewModel viewModel;
    private ActivityMainBinding activityMainBinding;
    private List<TVShow> tvShows = new ArrayList<>();
    private TVShowAdapter tvShowAdapter;
    private int currentPage = 1;
    private int totalAvailablePage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        doInitialization();
    }

    private void doInitialization() {

        activityMainBinding.tvShowRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        tvShowAdapter = new TVShowAdapter(tvShows,this);
        activityMainBinding.tvShowRecyclerView.setAdapter(tvShowAdapter);
        activityMainBinding.tvShowRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(!activityMainBinding.tvShowRecyclerView.canScrollVertically(1)){

                    if(currentPage <= totalAvailablePage){

                        currentPage += 1;
                        getMostPopularTvShow();
                    }

                }
            }
        });
        activityMainBinding.imageWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), WatchListActivity.class));
            }
        });
        activityMainBinding.imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });
        getMostPopularTvShow();
    }

    private void getMostPopularTvShow() {
       toggleLoading();
        viewModel.getMostPopularTvShow(currentPage).observe(this,mostPopularTvShowsResponse -> {

              toggleLoading();
              if (mostPopularTvShowsResponse != null){
                 totalAvailablePage = mostPopularTvShowsResponse.getTotalPages();
                  if(mostPopularTvShowsResponse.getTvShows() != null){
                      int oldCount = tvShows.size();
                      tvShows.addAll(mostPopularTvShowsResponse.getTvShows());
                      tvShowAdapter.notifyItemRangeInserted(oldCount,tvShows.size());
                  }

              }

        });
    }

    private void toggleLoading(){

        if (currentPage == 1) {

            if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading())
                activityMainBinding.setIsLoading(false);
            else
                activityMainBinding.setIsLoading(true);


        } else {

            if (activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore())
                activityMainBinding.setIsLoadingMore(false);
            else
                activityMainBinding.setIsLoadingMore(true);

        }

    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {

        Intent intent = new Intent(getApplicationContext(), TVShowDetailActivity.class);
        intent.putExtra("tvShow",tvShow);
        startActivity(intent);

    }
}