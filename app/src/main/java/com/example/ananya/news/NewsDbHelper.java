package com.example.ananya.news;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NewsDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "news";
    private static final int DATABASE_VERSION = 1;

    public NewsDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_RESULT_TABLE = "CREATE TABLE " +
                NewsContract.NewsEntry.TABLE_NAME + " (" +
                NewsContract.NewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NewsContract.NewsEntry.COLUMN_AUTHOR + " TEXT NOT NULL," +
                NewsContract.NewsEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                NewsContract.NewsEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                NewsContract.NewsEntry.COLUMN_URL + " TEXT NOT NULL," +
                NewsContract.NewsEntry.COLUMN_URLTOIMAGE + " TEXT NOT NULL," +
                NewsContract.NewsEntry.COLUMN_PUBLISHEDAT + " TEXT NOT NULL" + ")";

        db.execSQL(SQL_CREATE_RESULT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + NewsContract.NewsEntry.TABLE_NAME);
        onCreate(db);
    }
}
