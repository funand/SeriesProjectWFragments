package com.example.prince.myfirstapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteConnection extends SQLiteOpenHelper {

    final String create_episodes = "CREATE TABLE episodes (title TEXT, date TEXT," +
            " imdbRating TEXT, num INTEGER, season TEXT, episode TEXT,imdbID TEXT,image BLOB," +
            "plot TEXT,writers TEXT, actors TEXT, RUNTIME text, rating TEXT, votes TEXT)";
    final String drop_episodes = "DROP TABLE IF EXISTS episodes";

    public SQLiteConnection(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_episodes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(drop_episodes);
        onCreate(db);
    }
}
