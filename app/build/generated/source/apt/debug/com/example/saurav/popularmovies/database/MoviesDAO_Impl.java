package com.example.saurav.popularmovies.database;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import android.support.annotation.NonNull;
import com.example.saurav.popularmovies.model.Movies;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class MoviesDAO_Impl implements MoviesDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfMovies;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDeleteMovieWithId;

  public MoviesDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMovies = new EntityInsertionAdapter<Movies>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `movies_table`(`voteCount`,`id`,`video`,`voteAverage`,`title`,`popularity`,`posterPath`,`originalLanguage`,`originalTitle`,`backdropPath`,`adult`,`overview`,`releaseDate`,`isSaved`) VALUES (?,nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Movies value) {
        stmt.bindLong(1, value.getVoteCount());
        stmt.bindLong(2, value.getId());
        final int _tmp;
        _tmp = value.isVideo() ? 1 : 0;
        stmt.bindLong(3, _tmp);
        stmt.bindDouble(4, value.getVoteAverage());
        if (value.getTitle() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getTitle());
        }
        stmt.bindDouble(6, value.getPopularity());
        if (value.getPosterPath() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getPosterPath());
        }
        if (value.getOriginalLanguage() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getOriginalLanguage());
        }
        if (value.getOriginalTitle() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getOriginalTitle());
        }
        if (value.getBackdropPath() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getBackdropPath());
        }
        final int _tmp_1;
        _tmp_1 = value.isAdult() ? 1 : 0;
        stmt.bindLong(11, _tmp_1);
        if (value.getOverview() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getOverview());
        }
        if (value.getReleaseDate() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getReleaseDate());
        }
        final int _tmp_2;
        _tmp_2 = value.isSaved() ? 1 : 0;
        stmt.bindLong(14, _tmp_2);
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM movies_table";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteMovieWithId = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM movies_table WHERE ID = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(Movies movie) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfMovies.insert(movie);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public void deleteMovieWithId(int id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteMovieWithId.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      _stmt.bindLong(_argIndex, id);
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteMovieWithId.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Movies>> getAllMovies() {
    final String _sql = "SELECT * FROM movies_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Movies>>() {
      private Observer _observer;

      @Override
      protected List<Movies> compute() {
        if (_observer == null) {
          _observer = new Observer("movies_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfVoteCount = _cursor.getColumnIndexOrThrow("voteCount");
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfVideo = _cursor.getColumnIndexOrThrow("video");
          final int _cursorIndexOfVoteAverage = _cursor.getColumnIndexOrThrow("voteAverage");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfPopularity = _cursor.getColumnIndexOrThrow("popularity");
          final int _cursorIndexOfPosterPath = _cursor.getColumnIndexOrThrow("posterPath");
          final int _cursorIndexOfOriginalLanguage = _cursor.getColumnIndexOrThrow("originalLanguage");
          final int _cursorIndexOfOriginalTitle = _cursor.getColumnIndexOrThrow("originalTitle");
          final int _cursorIndexOfBackdropPath = _cursor.getColumnIndexOrThrow("backdropPath");
          final int _cursorIndexOfAdult = _cursor.getColumnIndexOrThrow("adult");
          final int _cursorIndexOfOverview = _cursor.getColumnIndexOrThrow("overview");
          final int _cursorIndexOfReleaseDate = _cursor.getColumnIndexOrThrow("releaseDate");
          final int _cursorIndexOfIsSaved = _cursor.getColumnIndexOrThrow("isSaved");
          final List<Movies> _result = new ArrayList<Movies>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Movies _item;
            final int _tmpVoteCount;
            _tmpVoteCount = _cursor.getInt(_cursorIndexOfVoteCount);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final float _tmpVoteAverage;
            _tmpVoteAverage = _cursor.getFloat(_cursorIndexOfVoteAverage);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpPosterPath;
            _tmpPosterPath = _cursor.getString(_cursorIndexOfPosterPath);
            final String _tmpBackdropPath;
            _tmpBackdropPath = _cursor.getString(_cursorIndexOfBackdropPath);
            final String _tmpOverview;
            _tmpOverview = _cursor.getString(_cursorIndexOfOverview);
            final String _tmpReleaseDate;
            _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            final boolean _tmpIsSaved;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSaved);
            _tmpIsSaved = _tmp != 0;
            _item = new Movies(_tmpId,_tmpVoteCount,_tmpVoteAverage,_tmpTitle,_tmpPosterPath,_tmpReleaseDate,_tmpOverview,_tmpIsSaved,_tmpBackdropPath);
            final boolean _tmpVideo;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfVideo);
            _tmpVideo = _tmp_1 != 0;
            _item.setVideo(_tmpVideo);
            final float _tmpPopularity;
            _tmpPopularity = _cursor.getFloat(_cursorIndexOfPopularity);
            _item.setPopularity(_tmpPopularity);
            final String _tmpOriginalLanguage;
            _tmpOriginalLanguage = _cursor.getString(_cursorIndexOfOriginalLanguage);
            _item.setOriginalLanguage(_tmpOriginalLanguage);
            final String _tmpOriginalTitle;
            _tmpOriginalTitle = _cursor.getString(_cursorIndexOfOriginalTitle);
            _item.setOriginalTitle(_tmpOriginalTitle);
            final boolean _tmpAdult;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfAdult);
            _tmpAdult = _tmp_2 != 0;
            _item.setAdult(_tmpAdult);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}
