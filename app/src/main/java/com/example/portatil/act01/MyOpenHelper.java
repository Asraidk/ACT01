package com.example.portatil.act01;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyOpenHelper extends SQLiteOpenHelper {
    //camps de la taula y les columnes
    public static final String TABLE_PRODUCTES = "productes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CODI = "codi";
    public static final String COLUMN_DESCRIPCIO = "descripcio";
    public static final String COLUMN_PVP = "pvp";
    public static final String COLUMN_STOCK = "stock";
    //nom de la bs i la seva versio
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
    //metode  constructor del openhelper per quan necesitem acedir a la BD
    public MyOpenHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //en el on create genera la nova base de dades si es el primer cop, sino tindriem que mirar version actuals
    //y fer un upgrade de la base de dades
    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(DATABASE_CREATE);
    }
    //de moment no serveix per res ja que la versio sempre es la 1
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* aqui ira codigo sobre reinciar la tabla*/
    }

}