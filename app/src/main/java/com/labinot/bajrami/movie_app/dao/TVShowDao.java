package com.labinot.bajrami.movie_app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.labinot.bajrami.movie_app.models.TVShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface TVShowDao {

    @Query("SELECT * FROM tvshows")
    Flowable<List<TVShow>> getWatchList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToWatchList(TVShow tvShow);

    @Delete
    Completable removeFromWatchList(TVShow tvShow);

    @Query("SELECT * FROM tvshows WHERE id = :tvShowId")
    Flowable<TVShow> getTVShowFromWatchList(String tvShowId);
}
