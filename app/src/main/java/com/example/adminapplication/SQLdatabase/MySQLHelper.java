package com.example.adminapplication.SQLdatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySQLHelper extends SQLiteOpenHelper{
    public static  String USERS ="users";
    public static final String NAME ="name";
    public static final String EMAIL ="email";
    public static final String PASSWORD="password";
    public static final String PHONE="phone";
    public static final String IMAGE="image";
    public static final String BALANCE="balance";

    public MySQLHelper(Context context) {
        super(context,"admin.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLUtility.createFavoriteTable(USERS));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQLUtility.createFavoriteTable(USERS));
        onCreate(db);

    }

}
