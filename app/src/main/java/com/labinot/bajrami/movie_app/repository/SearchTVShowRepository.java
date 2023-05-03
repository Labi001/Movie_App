package com.labinot.bajrami.movie_app.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.labinot.bajrami.movie_app.network.ApiClient;
import com.labinot.bajrami.movie_app.network.ApiService;
import com.labinot.bajrami.movie_app.responses.TVShow_Responses;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTVShowRepository {

    private ApiService apiService;

    public SearchTVShowRepository(){

        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVShow_Responses> searchTVShow(String query, int page){

        MutableLiveData<TVShow_Responses> data = new MutableLiveData<>();
        apiService.searchTvShow(query, page).enqueue(new Callback<TVShow_Responses>() {
            @Override
            public void onResponse(@NonNull Call<TVShow_Responses> call,@NonNull Response<TVShow_Responses> response) {

                data.setValue(response.body());

            }

            @Override
            public void onFailure(@NonNull Call<TVShow_Responses> call, @NonNull Throwable t) {

                data.setValue(null);
            }
        });
      return data;
    }
}
