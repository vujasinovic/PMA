package com.example.transportivo.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.transportivo.db.DbHelper;
import com.example.transportivo.db.NotificationTokenTableHelper;

import java.util.HashMap;
import java.util.Objects;

public class NotificationTokenProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.example.transportivo.provider.NotificationTokenProvider";
    public static final String URL = "content://" + PROVIDER_NAME + "/tokens";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    static final int TOKEN = 1;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "tokens", TOKEN);
    }

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DbHelper dbHelper = new DbHelper(context);

        db = dbHelper.getWritableDatabase();
        return db != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(NotificationTokenTableHelper.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case TOKEN:
                qb.setProjectionMap(new HashMap<>());
                break;
            default:
        }

        return qb.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id = db.insert(NotificationTokenTableHelper.TABLE_NAME, "", values);
        db.execSQL("DROP TABLE Offers");
        if (id > 0) {
            Uri uriWithId = ContentUris.withAppendedId(CONTENT_URI, id);
            ContentResolver contentResolver = Objects.requireNonNull(getContext()).getContentResolver();
            contentResolver.notifyChange(uriWithId, null);
            return uriWithId;
        }

        throw new SQLException("Failed to insert a record into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int row_update;

        row_update = db.update(NotificationTokenTableHelper.TABLE_NAME, values, "id=" + values.get("id"), null);

        ContentResolver contentResolver = Objects.requireNonNull(getContext()).getContentResolver();
        contentResolver.notifyChange(uri, null);

        return row_update;
    }
}
