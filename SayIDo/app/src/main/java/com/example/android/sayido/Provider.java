package com.example.android.sayido;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.Nullable;

public class Provider extends ContentProvider {
    private DBHelper db;
    private final static String TABLE_NAME = "users";
    private final static int STUDENT_CODE= 1;
    private final static String AUTHORITY = "com.example.android.sayido";
    private final static Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    private final static Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(TABLE_NAME).build();
    private final UriMatcher mUriMatcher = buildUriMatcher();
    private UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, STUDENT_CODE);
        return uriMatcher;
    }
    public static Uri getContentUri() {
        return CONTENT_URI;
    }
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        matcher.addURI("com.example.android.sayido", "users", 1);
    }

    public boolean onCreate() {
        db = DBHelper.getDB(getContext());
        return true;
    }

    public String getType(Uri uri) {
        if (matcher.match(uri) == 1) {
            return "vnd.android.cursor.dir/vnd.com.example.android.sayido.Provider." + TABLE_NAME;
        } else
            throw new IllegalArgumentException("Unsupported URI: " + uri);

    }

    @Nullable
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase mdb = db.getWritableDatabase();
        int match = mUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case STUDENT_CODE:
                long id = mdb.insert(TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = new ContentUris().withAppendedId(BASE_CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert" + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Operation not Supported");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] args) {
        SQLiteDatabase mdb = db.getWritableDatabase();
        int match = mUriMatcher.match(uri);
        int status;
        Uri returnUri;
        switch (match) {
            case STUDENT_CODE:
                status = mdb.update(TABLE_NAME, values, selection, args);
                if (status > 0) {

                } else {
                    status = -1;
                }
                break;
            default:
                throw new UnsupportedOperationException("Operation not Supported");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return status;

    }

    public int delete(Uri uri, String selection, String[] args) {
        SQLiteDatabase mdb = db.getWritableDatabase();
        int match = mUriMatcher.match(uri);
        long id;

        Uri returnUri;
        switch (match) {
            case STUDENT_CODE:
                id = mdb.delete(TABLE_NAME, selection, args);
                if (id < 0) {
                    id = -1;
                }
                break;
            default:
                throw new UnsupportedOperationException("Operation not Supported");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return (int) id;

    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase mdb = db.getReadableDatabase();
        int match = mUriMatcher.match(uri);
        Cursor cursor;
        switch (match) {
            case STUDENT_CODE:
                cursor = mdb.query(false, TABLE_NAME, projection, selection, selectionArgs,
                        null, null, null, null);
                break;
            default:
                throw new UnsupportedOperationException("Operation Not Supported Yet");
        }
        if (cursor != null) {
            return cursor;
        } else {
            throw new SQLException("Unknown URI " + uri);
        }


    }
}
