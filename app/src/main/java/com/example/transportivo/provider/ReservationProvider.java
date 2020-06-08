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
import com.example.transportivo.db.offers.OffersTableHelper;
import com.example.transportivo.db.reservation.ReservationTableHelper;

import java.util.HashMap;
import java.util.Objects;

public class ReservationProvider extends ContentProvider {


    static final String PROVIDER_NAME = "com.example.transportivo.provider.ReservationProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/reservation";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    static final int RESERVATION = 1;
    static final int RESERVATION_ID = 2;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "reservation", RESERVATION);
        uriMatcher.addURI(PROVIDER_NAME, "reservation/#", RESERVATION_ID);
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
        qb.setTables(OffersTableHelper.TABLE_NAME);
        Cursor c = qb.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id = db.insert(ReservationTableHelper.TABLE_NAME, "", values);

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
        return 0;
    }
}
