package com.example.splashlogin.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class FavoritesDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "Favorites";
    public static final String TABLE_NAME = "Favorites";
    public static final String COLUMN_ID = "ID";

    public FavoritesDB(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " TEXT primary key)";

        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addToFavorite(String ID) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, ID);

        long insert = db.insert(TABLE_NAME, null, values);

        return insert != -1;
    }

    public boolean isFavorites(String ID) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM Favorites WHERE ID='" + ID + "'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }



    public boolean removeFromFavorites(String ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        long status = db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[] { String.valueOf(ID) });
        db.close();
        return status != -1;
    }

    public void clearFavorites() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from "+ TABLE_NAME);
        db.close();
    }
}

