package com.example.transportivo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.transportivo.db.offers.OffersTableHelper;
import com.example.transportivo.db.reservation.ReservationTableHelper;

public class DbHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "Transportivo";
    static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(OffersTableHelper.CREATE_TABLE);
        db.execSQL(ReservationTableHelper.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + OffersTableHelper.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ReservationTableHelper.TABLE_NAME);

        onCreate(db);
    }
}
