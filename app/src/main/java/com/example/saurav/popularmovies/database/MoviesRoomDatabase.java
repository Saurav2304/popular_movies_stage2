package com.example.saurav.popularmovies.database;

import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;

import com.example.saurav.popularmovies.model.Movies;

/**
 * Created by Saurav on 7/17/2018.
 **/
@Database(entities = {Movies.class},version = 1,exportSchema = false)
public abstract class MoviesRoomDatabase extends RoomDatabase {

    public abstract MoviesDAO moviesDAO();

    private static MoviesRoomDatabase INSTANCE;

    public static MoviesRoomDatabase getDatabaseInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (MoviesRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MoviesRoomDatabase.class, "movies_database")
                            .allowMainThreadQueries()
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
