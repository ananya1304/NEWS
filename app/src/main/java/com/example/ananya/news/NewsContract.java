package com.example.ananya.news;

import android.net.Uri;
import android.provider.BaseColumns;

public class NewsContract {
    private NewsContract() {
    }

    ;

    public static final String CONTENT_AUTHORITY = "com.example.ananya.news";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class NewsEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "articles";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_URLTOIMAGE = "urltoimage";
        public static final String COLUMN_PUBLISHEDAT = "publishedat";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static Uri buildStudentUriWithId(long id){
            return CONTENT_URI.buildUpon().appendPath(Long.toString(id)).build();
        }
    }

}
