package com.labinot.bajrami.movie_app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.labinot.bajrami.movie_app.repository.MostPopularTvShowsRepository;
import com.labinot.bajrami.movie_app.responses.TVShow_Responses;

public class MostPopularTVShowsViewModel extends ViewModel {

    private MostPopularTvShowsRepository mostPopularTvShowsRepository;

    public MostPopularTVShowsViewModel(){

        mostPopularTvShowsRepository = new MostPopularTvShowsRepository();
    }

    public LiveData<TVShow_Responses> getMostPopularTvShow(int page){

          return mostPopularTvShowsRepository.getMostPopularTvShow(page);
    }

}
