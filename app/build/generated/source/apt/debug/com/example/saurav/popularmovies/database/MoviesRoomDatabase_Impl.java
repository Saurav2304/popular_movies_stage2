package com.example.saurav.popularmovies.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class MoviesRoomDatabase_Impl extends MoviesRoomDatabase {
  private volatile MoviesDAO _moviesDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `movies_table` (`voteCount` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `video` INTEGER NOT NULL, `voteAverage` REAL NOT NULL, `title` TEXT, `popularity` REAL NOT NULL, `posterPath` TEXT, `originalLanguage` TEXT, `originalTitle` TEXT, `backdropPath` TEXT, `adult` INTEGER NOT NULL, `overview` TEXT, `releaseDate` TEXT, `isSaved` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"78be6d0c44813f97a804e70ae86a220a\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `movies_table`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsMoviesTable = new HashMap<String, TableInfo.Column>(14);
        _columnsMoviesTable.put("voteCount", new TableInfo.Column("voteCount", "INTEGER", true, 0));
        _columnsMoviesTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsMoviesTable.put("video", new TableInfo.Column("video", "INTEGER", true, 0));
        _columnsMoviesTable.put("voteAverage", new TableInfo.Column("voteAverage", "REAL", true, 0));
        _columnsMoviesTable.put("title", new TableInfo.Column("title", "TEXT", false, 0));
        _columnsMoviesTable.put("popularity", new TableInfo.Column("popularity", "REAL", true, 0));
        _columnsMoviesTable.put("posterPath", new TableInfo.Column("posterPath", "TEXT", false, 0));
        _columnsMoviesTable.put("originalLanguage", new TableInfo.Column("originalLanguage", "TEXT", false, 0));
        _columnsMoviesTable.put("originalTitle", new TableInfo.Column("originalTitle", "TEXT", false, 0));
        _columnsMoviesTable.put("backdropPath", new TableInfo.Column("backdropPath", "TEXT", false, 0));
        _columnsMoviesTable.put("adult", new TableInfo.Column("adult", "INTEGER", true, 0));
        _columnsMoviesTable.put("overview", new TableInfo.Column("overview", "TEXT", false, 0));
        _columnsMoviesTable.put("releaseDate", new TableInfo.Column("releaseDate", "TEXT", false, 0));
        _columnsMoviesTable.put("isSaved", new TableInfo.Column("isSaved", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMoviesTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMoviesTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMoviesTable = new TableInfo("movies_table", _columnsMoviesTable, _foreignKeysMoviesTable, _indicesMoviesTable);
        final TableInfo _existingMoviesTable = TableInfo.read(_db, "movies_table");
        if (! _infoMoviesTable.equals(_existingMoviesTable)) {
          throw new IllegalStateException("Migration didn't properly handle movies_table(com.example.saurav.popularmovies.model.Movies).\n"
                  + " Expected:\n" + _infoMoviesTable + "\n"
                  + " Found:\n" + _existingMoviesTable);
        }
      }
    }, "78be6d0c44813f97a804e70ae86a220a", "37bf82f004d1daa1cffda2006a39f48a");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "movies_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `movies_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public MoviesDAO moviesDAO() {
    if (_moviesDAO != null) {
      return _moviesDAO;
    } else {
      synchronized(this) {
        if(_moviesDAO == null) {
          _moviesDAO = new MoviesDAO_Impl(this);
        }
        return _moviesDAO;
      }
    }
  }
}
