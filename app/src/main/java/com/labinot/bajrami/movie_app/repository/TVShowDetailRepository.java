package com.labinot.bajrami.movie_app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.labinot.bajrami.movie_app.network.ApiClient;
import com.labinot.bajrami.movie_app.network.ApiService;
import com.labinot.bajrami.movie_app.responses.TVShowDetailsResponse;

import io.reactivex.annotations.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowDetailRepository {

    private ApiService apiService;

    public TVShowDetailRepository(){

        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(String tvShowId){

        MutableLiveData<TVShowDetailsResponse> data = new MutableLiveData<>();
        apiService.getTVShowDetails(tvShowId).enqueue(new Callback<TVShowDetailsResponse>() {
            @Override
            public void onResponse(@NonNull  Call<TVShowDetailsResponse> call, @NonNull Response<TVShowDetailsResponse> response) {

                  data.setValue(response.body());
            }

            @Override
            public void onFailure( @NonNull Call<TVShowDetailsResponse> call, @NonNull Throwable t) {

                data.setValue(null);

            }
        });
        return data;
    }

}
