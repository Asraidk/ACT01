package com.example.portatil.act01;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_PRODUCTES = "productes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CODI = "codi";
    public static final String COLUMN_DESCRIPCIO = "descripcio";
    public static final String COLUMN_PVP = "pvp";
    public static final String COLUMN_STOCK = "stock";

    private static final String DATABASE_NAME = "productes.db";
    private static final int DATABASE_VERSION = 1;

    //Creacio de la base de dades
    private static final String DATABASE_CREATE = "create table "
            + TABLE_PRODUCTES + "( " +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_CODI + " text not null, " +
            COLUMN_DESCRIPCIO + " text not null, " +
            COLUMN_PVP + " float not null, " +
            COLUMN_STOCK + " integer not null);";

    public MyOpenHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* aqui ira codigo sobre reinciar la tabla*/
    }

}