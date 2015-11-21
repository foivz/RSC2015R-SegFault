package com.example.tinoba.liveball.db;

/**
 * Created by antonio on 21/11/15.
 */
public class Database {
    public static final String DATABASE_NAME = "liveball.db";
    public static final int DATABASE_VERSION = 1;

    public static class usersTable{
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_ADMIN = "isAdmin";
        public static final String COLUMN_AUTH_KEY = "key";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("+
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_EMAIL + " TEXT NOT NULL, " +
                COLUMN_ADMIN + " INTEGER NOT NULL DEFAULT 0" +
                COLUMN_AUTH_KEY + " TEXT NOT NULL " + ")";

    }
}
