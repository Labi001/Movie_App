package com.labinot.bajrami.movie_app.responses;

import com.google.gson.annotations.SerializedName;
import com.labinot.bajrami.movie_app.models.TVShowDetails;

public class TVShowDetailsResponse {

    @SerializedName("tvShow")
    private TVShowDetails tvShowDetails;

    public TVShowDetails getTvShowDetails() {
        return tvShowDetails;
    }
}
