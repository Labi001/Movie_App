package com.labinot.bajrami.movie_app.network;

import com.labinot.bajrami.movie_app.responses.TVShowDetailsResponse;
import com.labinot.bajrami.movie_app.responses.TVShow_Responses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<TVShow_Responses> getMostPopularTvShows(@Query("page") int page);

    @GET("show-details")
    Call<TVShowDetailsResponse> getTVShowDetails(@Query("q") String tvShowId);

    @GET("search")
    Call<TVShow_Responses> searchTvShow(@Query("q") String query, @Query("page") int page);

}
