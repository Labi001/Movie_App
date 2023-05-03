package com.labinot.bajrami.movie_app.responses;

import com.google.gson.annotations.SerializedName;
import com.labinot.bajrami.movie_app.models.TVShow;

import java.util.List;

public class TVShow_Responses {

    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private int totalPages;

    @SerializedName("tv_shows")
    private List<TVShow> tvShows;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<TVShow> getTvShows() {
        return tvShows;
    }
}
