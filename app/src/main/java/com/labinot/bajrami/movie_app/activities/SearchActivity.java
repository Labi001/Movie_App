package com.labinot.bajrami.movie_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.labinot.bajrami.movie_app.R;
import com.labinot.bajrami.movie_app.adapters.TVShowAdapter;
import com.labinot.bajrami.movie_app.databinding.ActivitySearchBinding;
import com.labinot.bajrami.movie_app.listeners.TVShowListeners;
import com.labinot.bajrami.movie_app.models.TVShow;
import com.labinot.bajrami.movie_app.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements TVShowListeners {

    private ActivitySearchBinding activitySearchBinding;
    private SearchViewModel viewModel;
    private List<TVShow> tvShows = new ArrayList<>();
    private TVShowAdapter tvShowAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;
    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = DataBindingUtil.setContentView(this,R.layout.activity_search);
        doInitialization();

    }

    private void doInitialization() {

        activitySearchBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        activitySearchBinding.tvShowRecyclerViewSearch.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        tvShowAdapter = new TVShowAdapter(tvShows,this);
        activitySearchBinding.tvShowRecyclerViewSearch.setAdapter(tvShowAdapter);
        activitySearchBinding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(timer != null)
                    timer.cancel();

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!s.toString().trim().isEmpty()){

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {

                                    currentPage = 1;
                                    totalAvailablePages = 1;
                                    searchTVShow(s.toString());

                                }
                            });

                        }
                    },800);

                }else{
                    tvShows.clear();
                    tvShowAdapter.notifyDataSetChanged();


                }

            }
        });

        activitySearchBinding.tvShowRecyclerViewSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!activitySearchBinding.tvShowRecyclerViewSearch.canScrollVertically(1)){

                    if(!activitySearchBinding.inputSearch.getText().toString().isEmpty()){

                        if(currentPage < totalAvailablePages){

                            currentPage += 1;
                            searchTVShow(activitySearchBinding.inputSearch.getText().toString());

                        }



                    }

                }
            }
        });

        activitySearchBinding.inputSearch.requestFocus();
    }

    private void searchTVShow(String query){

        toggleLoading();
        viewModel.searchTVShow(query,currentPage).observe(this,tvShow_responses -> {

            toggleLoading();
            if(tvShow_responses != null){

                totalAvailablePages = tvShow_responses.getTotalPages();
                if(tvShow_responses.getTvShows() != null){
                    int oldCount = tvShows.size();
                    tvShows.addAll(tvShow_responses.getTvShows());
                    tvShowAdapter.notifyItemRangeInserted(oldCount,tvShows.size());

                }

            }


        });


    }

    private void toggleLoading(){

        if (currentPage == 1) {

            if (activitySearchBinding.getIsLoading() != null && activitySearchBinding.getIsLoading())
                activitySearchBinding.setIsLoading(false);
            else
                activitySearchBinding.setIsLoading(true);


        } else {

            if (activitySearchBinding.getIsLoadingMore() != null && activitySearchBinding.getIsLoadingMore())
                activitySearchBinding.setIsLoadingMore(false);
            else
                activitySearchBinding.setIsLoadingMore(true);

        }

    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {

        Intent intent = new Intent(getApplicationContext(), TVShowDetailActivity.class);
        intent.putExtra("tvShow",tvShow);
        startActivity(intent);
    }
}