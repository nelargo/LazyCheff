package com.madgoatstd.lazycheff.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UtensilioDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = { SQLiteHelper.UTENSILIO_COLUMN_ID,
            SQLiteHelper.UTENSILIO_COLUMN_NOMBRE };

    public UtensilioDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Utensilio createUtensilio(int id, String nombre){
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.UTENSILIO_COLUMN_ID, id);
        values.put(SQLiteHelper.UTENSILIO_COLUMN_NOMBRE, nombre);
        long insertId = database.insert(SQLiteHelper.TABLE_UTENSILIO, null,
                values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_UTENSILIO,
                allColumns, SQLiteHelper.UTENSILIO_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Utensilio newUtensilio = cursorToUtensilio(cursor);
        cursor.close();
        return newUtensilio;
    }

    public List<Utensilio> getAllUtensilios() {
        List<Utensilio> utensilios = new ArrayList<Utensilio>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_UTENSILIO,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Utensilio utensilio = cursorToUtensilio(cursor);
            utensilios.add(utensilio);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return utensilios;
    }

    private Utensilio cursorToUtensilio(Cursor cursor) {
        Utensilio utensilio = new Utensilio(cursor.getInt(0),cursor.getString(1));
        return utensilio;
    }
} 


