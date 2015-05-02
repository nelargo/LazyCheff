package com.madgoatstd.lazycheff.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RecipeDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = { SQLiteHelper.RECETA_COLUMN_ID,
            SQLiteHelper.RECETA_COLUMN_NOMBRE,
            SQLiteHelper.RECETA_COLUMN_DIFICULTAD,
            SQLiteHelper.RECETA_COLUMN_TIEMPO,
            SQLiteHelper.RECETA_COLUMN_INDICACIONES,
            SQLiteHelper.RECETA_COLUMN_TIPO,
            SQLiteHelper.RECETA_COLUMN_IMAGEN };

    public RecipeDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Receta createReceta(int id, String nombre, String dificultad, String tiempo, String indicaciones, String imagen, String tipo) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.RECETA_COLUMN_ID, id);
        values.put(SQLiteHelper.RECETA_COLUMN_NOMBRE, nombre);
        values.put(SQLiteHelper.RECETA_COLUMN_DIFICULTAD, dificultad);
        values.put(SQLiteHelper.RECETA_COLUMN_TIEMPO, tiempo);
        values.put(SQLiteHelper.RECETA_COLUMN_INDICACIONES, indicaciones);
        values.put(SQLiteHelper.RECETA_COLUMN_TIPO, tipo);
        values.put(SQLiteHelper.RECETA_COLUMN_IMAGEN, imagen);
        long insertId = database.insert(SQLiteHelper.TABLE_RECETA, null,
                values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_RECETA,
                allColumns, SQLiteHelper.RECETA_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Receta newReceta = cursorToReceta(cursor);
        cursor.close();
        return newReceta;
    }

    public List<Receta> getAllRecetas() {
        List<Receta> recetas = new ArrayList<Receta>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_RECETA,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Receta receta = cursorToReceta(cursor);
            recetas.add(receta);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return recetas;
    }

    public int getLastId(){
        Cursor cursor = database.rawQuery("SELECT "+SQLiteHelper.RECETA_COLUMN_ID+" FROM "+SQLiteHelper.TABLE_RECETA +" ORDER BY "+SQLiteHelper.RECETA_COLUMN_ID+" DESC LIMIT 1",null);
        cursor.moveToFirst();
        int p=0;
        if(cursor.getCount() > 0)
            p = cursor.getInt(0);
        return p;
    }

    private Receta cursorToReceta(Cursor cursor) {
        Receta receta = new Receta((int)cursor.getLong(0),cursor.getString(1),cursor.getString(2),
                cursor.getString(3), cursor.getString(4),cursor.getString(5),cursor.getString(6));
        return receta;
    }
} 


