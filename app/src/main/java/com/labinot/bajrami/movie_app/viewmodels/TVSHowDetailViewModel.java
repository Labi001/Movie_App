package com.labinot.bajrami.movie_app.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.labinot.bajrami.movie_app.database.TvShowsDatabase;
import com.labinot.bajrami.movie_app.models.TVShow;
import com.labinot.bajrami.movie_app.repository.TVShowDetailRepository;
import com.labinot.bajrami.movie_app.responses.TVShowDetailsResponse;



import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TVSHowDetailViewModel extends AndroidViewModel {

    private TVShowDetailRepository tvShowDetailRepository;
    private TvShowsDatabase tvShowsDatabase;

    public TVSHowDetailViewModel (@NonNull Application application){
         super(application);
        tvShowDetailRepository = new TVShowDetailRepository();
        tvShowsDatabase = TvShowsDatabase.getTvShowsDatabase(application);
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(String tvShowId){

        return tvShowDetailRepository.getTVShowDetails(tvShowId);
    }

    public Completable addToWatchList(TVShow tvShow){

           return tvShowsDatabase.tvShowDao().addToWatchList(tvShow);
    }

    public Completable removeTvShowFromWatchList(TVShow tvShow){

        return tvShowsDatabase.tvShowDao().removeFromWatchList(tvShow);
    }

    public Flowable<TVShow> getTVShowFromWatchList(String tvShowId){

       return tvShowsDatabase.tvShowDao().getTVShowFromWatchList(tvShowId);
    }

}
