package com.example.splashlogin.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class CartDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "CartItems";
    public static final String TABLE_NAME = "CartItems";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "PRODUCT_NAME";
    public static final String COLUMN_PRICE = "PRODUCT_PRICE";
    public static final String COLUMN_IMG = "PRODUCT_IMG";
    public static final String COLUMN_QUANTITY = "PRODUCT_QUANTITY";
    public static final String COLUMN_TOTAL_PRICE = "PRODUCT_TOTAL_PRICE";

    public CartDB(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " TEXT primary key,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PRICE + " INTEGER,"
                + COLUMN_IMG + " TEXT,"
                + COLUMN_QUANTITY + " INTEGER,"
                + COLUMN_TOTAL_PRICE + " INTEGER)";

        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addCartItem(CartModel cartModel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(COLUMN_ID, cartModel.getID());
        values.put(COLUMN_NAME, cartModel.getProductName());
        values.put(COLUMN_PRICE, cartModel.getProductPrice());
        values.put(COLUMN_IMG, cartModel.getImg());
        values.put(COLUMN_QUANTITY, cartModel.getQuantity());
        values.put(COLUMN_TOTAL_PRICE, cartModel.getTotalPrice());

        long insert = db.insert(TABLE_NAME, null, values);

        return insert != -1;
    }

    public List<CartModel> getAllCartItems() {
        List<CartModel> cartItemsList = new ArrayList<CartModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CartModel product = new CartModel(cursor.getString(0),cursor.getString(1), cursor.getInt(2), cursor.getString(3),cursor.getInt(4), cursor.getInt(5));

                cartItemsList.add(product);
            } while (cursor.moveToNext());
        }

        return cartItemsList;
    }

    public int updateCartItem(CartModel cartModel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(COLUMN_ID, cartModel.getID());
        values.put(COLUMN_NAME, cartModel.getProductName());
        values.put(COLUMN_PRICE, cartModel.getProductPrice());
        values.put(COLUMN_IMG, cartModel.getImg());
        values.put(COLUMN_QUANTITY, cartModel.getQuantity());
        values.put(COLUMN_TOTAL_PRICE, cartModel.getTotalPrice());


        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[] { String.valueOf(cartModel.getID()) });
    }

    public void removeCartItem(String ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[] { String.valueOf(ID) });
        db.close();
    }

    public void removeAllCartItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from "+ TABLE_NAME);
        db.close();
    }
}
