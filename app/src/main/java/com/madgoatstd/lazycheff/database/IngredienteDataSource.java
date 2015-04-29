package com.madgoatstd.lazycheff.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.madgoatstd.lazycheff.adapters.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredienteDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = { SQLiteHelper.INGREDIENTE_COLUMN_ID,
            SQLiteHelper.INGREDIENTE_COLUMN_NOMBRE };

    public IngredienteDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Ingrediente createIngrediente(int id, String nombre){
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.INGREDIENTE_COLUMN_ID, id);
        values.put(SQLiteHelper.INGREDIENTE_COLUMN_NOMBRE, nombre);
        long insertId = database.insert(SQLiteHelper.TABLE_INGREDIENTE, null,
                values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_INGREDIENTE,
                allColumns, SQLiteHelper.INGREDIENTE_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Ingrediente newIngrediente = cursorToIngrediente(cursor);
        cursor.close();
        return newIngrediente;
    }

    public List<Ingrediente> getAllIngredientes() {
        List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_INGREDIENTE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Ingrediente ingrediente = cursorToIngrediente(cursor);
            ingredientes.add(ingrediente);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return ingredientes;
    }

    private Ingrediente cursorToIngrediente(Cursor cursor) {
        Ingrediente ingrediente = new Ingrediente(cursor.getInt(0),cursor.getString(1));
        return ingrediente;
    }
} 


