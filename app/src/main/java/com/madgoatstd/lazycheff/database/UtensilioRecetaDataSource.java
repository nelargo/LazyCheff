package com.madgoatstd.lazycheff.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UtensilioRecetaDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = { SQLiteHelper.UTENSILIO_RECETA_COLUMN_ID,
            SQLiteHelper.UTENSILIO_RECETA_COLUMN_ID_UTENSILIO,
            SQLiteHelper.UTENSILIO_RECETA_COLUMN_ID_RECETA};

    public UtensilioRecetaDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Utensilio_Receta createUtensilio(int id, int utensilio, int receta){
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.UTENSILIO_RECETA_COLUMN_ID, id);
        values.put(SQLiteHelper.UTENSILIO_RECETA_COLUMN_ID_UTENSILIO, utensilio);
        values.put(SQLiteHelper.UTENSILIO_RECETA_COLUMN_ID_RECETA, receta);
        long insertId = database.insert(SQLiteHelper.TABLE_UTENSILIO_RECETA, null,
                values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_UTENSILIO_RECETA,
                allColumns, SQLiteHelper.UTENSILIO_RECETA_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Utensilio_Receta newUtensilio_Receta = cursorToUtensilio_Receta(cursor);
        cursor.close();
        return newUtensilio_Receta;
    }

    public List<Utensilio_Receta> getAllUtensilio_Recetas() {
        List<Utensilio_Receta> utensilio_Recetas = new ArrayList<Utensilio_Receta>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_UTENSILIO_RECETA,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Utensilio_Receta utensilio_Receta = cursorToUtensilio_Receta(cursor);
            utensilio_Recetas.add(utensilio_Receta);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return utensilio_Recetas;
    }

    private Utensilio_Receta cursorToUtensilio_Receta(Cursor cursor) {
        Utensilio_Receta utensilio_Receta = new Utensilio_Receta(cursor.getInt(0),cursor.getInt(2), cursor.getInt(1));
        return utensilio_Receta;
    }
} 


