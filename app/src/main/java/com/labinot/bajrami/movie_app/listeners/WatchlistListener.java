package com.labinot.bajrami.movie_app.listeners;

import com.labinot.bajrami.movie_app.models.TVShow;

public interface WatchlistListener {

    void onTVShowClicked(TVShow tvShow);

    void removeTVShowFromWatchlist(TVShow tvShow,int position);
}
