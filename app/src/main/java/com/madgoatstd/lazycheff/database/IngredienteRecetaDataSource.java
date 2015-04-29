package com.madgoatstd.lazycheff.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.madgoatstd.lazycheff.adapters.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredienteRecetaDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = { SQLiteHelper.INGREDIENTE_RECETA_COLUMN_ID,
            SQLiteHelper.INGREDIENTE_RECETA_COLUMN_ID_INGREDIENTE,
            SQLiteHelper.INGREDIENTE_RECETA_COLUMN_ID_RECETA,
            SQLiteHelper.INGREDIENTE_RECETA_COLUMN_CANTIDAD};

    public IngredienteRecetaDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Ingrediente_Receta createIngredienteReceta(int id, int utensilio, int receta, String cantidad){
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.INGREDIENTE_RECETA_COLUMN_ID, id);
        values.put(SQLiteHelper.INGREDIENTE_RECETA_COLUMN_ID_INGREDIENTE, utensilio);
        values.put(SQLiteHelper.INGREDIENTE_RECETA_COLUMN_ID_RECETA, receta);
        values.put(SQLiteHelper.INGREDIENTE_RECETA_COLUMN_CANTIDAD, cantidad);
        long insertId = database.insert(SQLiteHelper.TABLE_INGREDIENTE_RECETA, null,
                values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_INGREDIENTE_RECETA,
                allColumns, SQLiteHelper.INGREDIENTE_RECETA_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Ingrediente_Receta newIngrediente_Receta = cursorToIngrediente_Receta(cursor);
        cursor.close();
        return newIngrediente_Receta;
    }

    public List<Ingrediente_Receta> getAllIngrediente_Recetas() {
        List<Ingrediente_Receta> ingrediente_Recetas = new ArrayList<Ingrediente_Receta>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_INGREDIENTE_RECETA,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Ingrediente_Receta utensilio_Receta = cursorToIngrediente_Receta(cursor);
            ingrediente_Recetas.add(utensilio_Receta);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return ingrediente_Recetas;
    }

    private Ingrediente_Receta cursorToIngrediente_Receta(Cursor cursor) {
        Ingrediente_Receta utensilio_Receta = new Ingrediente_Receta(cursor.getInt(0),cursor.getInt(1), cursor.getInt(2), cursor.getString(3));
        return utensilio_Receta;
    }
} 


