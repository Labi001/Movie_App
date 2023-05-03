package com.labinot.bajrami.movie_app.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.labinot.bajrami.movie_app.database.TvShowsDatabase;
import com.labinot.bajrami.movie_app.models.TVShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WatchListViewModel extends AndroidViewModel {

    private TvShowsDatabase tvShowsDatabase;

    public WatchListViewModel(@NonNull Application application) {
       super(application);

       tvShowsDatabase = TvShowsDatabase.getTvShowsDatabase(application);

    }

    public Flowable<List<TVShow>> loadWatchlist(){

        return tvShowsDatabase.tvShowDao().getWatchList();
    }

    public Completable removeTVShowFromWatchList(TVShow tvShow){

          return tvShowsDatabase.tvShowDao().removeFromWatchList(tvShow);
    }

}
