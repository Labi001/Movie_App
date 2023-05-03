package com.labinot.bajrami.movie_app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.labinot.bajrami.movie_app.repository.SearchTVShowRepository;
import com.labinot.bajrami.movie_app.responses.TVShow_Responses;

public class SearchViewModel extends ViewModel {

    private SearchTVShowRepository searchTVShowRepository;

    public SearchViewModel(){

        searchTVShowRepository = new SearchTVShowRepository();
    }

    public LiveData<TVShow_Responses> searchTVShow(String query,int page){

        return searchTVShowRepository.searchTVShow(query, page);
    }

}
