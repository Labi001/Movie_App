package com.labinot.bajrami.movie_app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.labinot.bajrami.movie_app.dao.TVShowDao;
import com.labinot.bajrami.movie_app.models.TVShow;

@Database(entities = {TVShow.class}, version = 1, exportSchema = false)
public abstract class TvShowsDatabase extends RoomDatabase {

    private static TvShowsDatabase tvShowsDatabase;

    public static synchronized TvShowsDatabase getTvShowsDatabase(Context context){

        if(tvShowsDatabase == null){

            tvShowsDatabase = Room.databaseBuilder(
                context,
                    TvShowsDatabase.class,
                    "tv_shows_db"

            ).build();
        }
      return tvShowsDatabase;
    }

    public abstract TVShowDao tvShowDao();

}
