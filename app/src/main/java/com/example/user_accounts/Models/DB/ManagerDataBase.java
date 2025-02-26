package com.example.user_accounts.Models.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ManagerDataBase extends SQLiteOpenHelper {

    private static final String DATA_BASE = "dbUser";
    private static final int VERSION = 1;
    private static final String TABLE_USER = "users";
    private static final String QUERY_TABLE_USER = "CREATE TABLE " + TABLE_USER +
            "(user_document VARCHAR(50) PRIMARY KEY," +
            "user_nick_name VARCHAR(150) NOT NULL, " +
            "user_names VARCHAR(150) NOT NULL," +
            "user_lastnames VARCHAR(150), " +
            "user_password VARCHAR(150) NOT NULL " +
            ");";

    public ManagerDataBase(@Nullable Context context) {
        super(context, DATA_BASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(QUERY_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        final String DB_DOWN_USER = "DROP TABLE IF EXISTS " + TABLE_USER;
        database.execSQL(DB_DOWN_USER);
        onCreate(database);
    }
}