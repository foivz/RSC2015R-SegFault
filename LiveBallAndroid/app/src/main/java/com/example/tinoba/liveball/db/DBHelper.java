package com.example.tinoba.liveball.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tinoba.liveball.models.UserModel;

import java.util.zip.DataFormatException;

/**
 * Created by antonio on 21/11/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context, Database.DATABASE_NAME, null, Database.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Database.usersTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addUser(UserModel user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Database.usersTable.COLUMN_NAME, user.getUsername());
        values.put(Database.usersTable.COLUMN_EMAIL, user.getEmail());
        values.put(Database.usersTable.COLUMN_ADMIN, user.getIsAdmin());
        values.put(Database.usersTable.COLUMN_AUTH_KEY, user.getKey());

        long id = db.insert(Database.usersTable.TABLE_NAME, null, values);
        db.close();
    }

    public UserModel getUser(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String where = Database.usersTable.COLUMN_ID + " = ?";
        Cursor cursor = db.query(
                Database.usersTable.TABLE_NAME,
                new String[] {Database.usersTable.COLUMN_ID, Database.usersTable.COLUMN_NAME, Database.usersTable.COLUMN_EMAIL, Database.usersTable.COLUMN_ADMIN, Database.usersTable.COLUMN_AUTH_KEY},
                where,
                new String[] {Integer.toString(id)},
                null, null, null
        );

        cursor.moveToFirst();
        return new UserModel(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getString(4)
        );
    }
}
