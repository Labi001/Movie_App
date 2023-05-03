package com.labinot.bajrami.movie_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.labinot.bajrami.movie_app.R;
import com.labinot.bajrami.movie_app.adapters.WatchlistAdapter;
import com.labinot.bajrami.movie_app.databinding.ActivityWatchListBinding;
import com.labinot.bajrami.movie_app.listeners.WatchlistListener;
import com.labinot.bajrami.movie_app.models.TVShow;
import com.labinot.bajrami.movie_app.utils.Constants;
import com.labinot.bajrami.movie_app.viewmodels.WatchListViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity implements WatchlistListener {

    private ActivityWatchListBinding activityWatchListBinding;
    private WatchListViewModel viewModel;
    private WatchlistAdapter watchlistAdapter;
    private List<TVShow> watchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchListBinding = DataBindingUtil.setContentView(this,R.layout.activity_watch_list);
        doInitialization();

    }

    private void doInitialization() {

        viewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
        activityWatchListBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
        watchList = new ArrayList<>();
        loadWatchlist();

    }

    private void loadWatchlist(){
     activityWatchListBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModel.loadWatchlist().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShows -> {
                    activityWatchListBinding.setIsLoading(false);
                    if(watchList.size() > 0)
                        watchList.clear();

                    watchList.addAll(tvShows);
                    watchlistAdapter = new WatchlistAdapter(watchList,this);
                    activityWatchListBinding.watchlistRecyclerView.setAdapter(watchlistAdapter);
                    activityWatchListBinding.watchlistRecyclerView.setVisibility(View.VISIBLE);
                    compositeDisposable.dispose();


                }));

    }



    @Override
    protected void onResume() {
        super.onResume();
        if(Constants.IS_WATCHLIST_UPDATE){
            loadWatchlist();
            Constants.IS_WATCHLIST_UPDATE = false;
        }

    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {

        Intent intent = new Intent(getApplicationContext(),TVShowDetailActivity.class);
        intent.putExtra("tvShow",tvShow);
        startActivity(intent);

    }

    @Override
    public void removeTVShowFromWatchlist(TVShow tvShow, int position) {

        CompositeDisposable compositeDisposableForDelete = new CompositeDisposable();
        compositeDisposableForDelete.add(viewModel.removeTVShowFromWatchList(tvShow)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    watchList.remove(position);
                    watchlistAdapter.notifyItemRemoved(position);
                    watchlistAdapter.notifyItemRangeChanged(position,watchlistAdapter.getItemCount());
                    compositeDisposableForDelete.dispose();
                }));


    }
}