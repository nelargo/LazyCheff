package com.madgoatstd.lazycheff.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

    // TODO: TABLAS
    public static final String TABLE_RECETA = "receta";
    public static final String TABLE_INGREDIENTE = "ingrediente";
    public static final String TABLE_UTENSILIO = "utensilio";
    public static final String TABLE_UTENSILIO_RECETA = "utensilio_receta";
    public static final String TABLE_INGREDIENTE_RECETA = "ingrediente_receta";

    /** TODO: COLUMNAS **/

    /** TODO:INGREDIENTE **/
    public static final String INGREDIENTE_COLUMN_ID = "_id";
    public static final String INGREDIENTE_COLUMN_NOMBRE = "nombre";

    /** TODO:UTENSILIO **/
    public static final String UTENSILIO_COLUMN_ID = "_id";
    public static final String UTENSILIO_COLUMN_NOMBRE = "nombre";

    /** TODO: RECETA**/
    public static final String RECETA_COLUMN_ID = "_id";
    public static final String RECETA_COLUMN_NOMBRE = "nombre";
    public static final String RECETA_COLUMN_DIFICULTAD = "dificultad";
    public static final String RECETA_COLUMN_TIEMPO = "tiempo";
    public static final String RECETA_COLUMN_INDICACIONES = "indicaciones";
    public static final String RECETA_COLUMN_IMAGEN = "imagen";
    public static final String RECETA_COLUMN_TIPO = "tipo";

    /** TODO: INGREDIENTE RECETA**/
    public static final String INGREDIENTE_RECETA_COLUMN_ID = "_id";
    public static final String INGREDIENTE_RECETA_COLUMN_ID_RECETA = "id_receta";
    public static final String INGREDIENTE_RECETA_COLUMN_ID_INGREDIENTE = "id_ingrediente";
    public static final String INGREDIENTE_RECETA_COLUMN_CANTIDAD = "_id_cantidad";

    /** TODO: INGREDIENTE RECETA**/
    public static final String UTENSILIO_RECETA_COLUMN_ID = "_id";
    public static final String UTENSILIO_RECETA_COLUMN_ID_RECETA = "id_receta";
    public static final String UTENSILIO_RECETA_COLUMN_ID_UTENSILIO = "id_utensilio";


    private static final String DATABASE_NAME = "lazychef.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE_INGREDIENTE = "create table "
            + TABLE_INGREDIENTE
            + "("
            + INGREDIENTE_COLUMN_ID + " integer primary key, "
            + INGREDIENTE_COLUMN_NOMBRE + " text not null"
            + ");";

    private static final String DATABASE_CREATE_UTENSILIO = "create table "
            + TABLE_UTENSILIO
            + "("
            + UTENSILIO_COLUMN_ID + " integer primary key, "
            + UTENSILIO_COLUMN_NOMBRE + " text not null"
            + ");";

    private static final String DATABASE_CREATE_RECETA = "create table "
            + TABLE_RECETA
            + "("
            + RECETA_COLUMN_ID + " integer primary key, "
            + RECETA_COLUMN_NOMBRE + " text not null,"
            + RECETA_COLUMN_DIFICULTAD + " text not null,"
            + RECETA_COLUMN_TIEMPO + " text not null,"
            + RECETA_COLUMN_INDICACIONES + " text not null,"
            + RECETA_COLUMN_TIPO + " text not null,"
            + RECETA_COLUMN_IMAGEN + " text not null"
            + ");";

    private static final String DATABASE_CREATE_INGREDIENTE_RECETA = "create table "
            + TABLE_INGREDIENTE_RECETA
            + "("
            + INGREDIENTE_RECETA_COLUMN_ID + " integer primary key autoincrement, "
            + INGREDIENTE_RECETA_COLUMN_ID_INGREDIENTE + " integer not null,"
            + INGREDIENTE_RECETA_COLUMN_ID_RECETA + " integer not null,"
            + INGREDIENTE_RECETA_COLUMN_CANTIDAD + " integer not null"
            + ");";

    private static final String DATABASE_CREATE_UTENSILIO_RECETA = "create table "
            + TABLE_UTENSILIO_RECETA
            + "("
            + UTENSILIO_RECETA_COLUMN_ID + " integer primary key autoincrement, "
            + UTENSILIO_RECETA_COLUMN_ID_RECETA + " text not null,"
            + UTENSILIO_RECETA_COLUMN_ID_UTENSILIO + " text not null"
            + ");";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_INGREDIENTE);
        database.execSQL(DATABASE_CREATE_UTENSILIO);
        database.execSQL(DATABASE_CREATE_RECETA);
        database.execSQL(DATABASE_CREATE_INGREDIENTE_RECETA);
        database.execSQL(DATABASE_CREATE_UTENSILIO_RECETA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECETA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTENSILIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTE_RECETA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTENSILIO_RECETA
        );
        onCreate(db);
    }

}
