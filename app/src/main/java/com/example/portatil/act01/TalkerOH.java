package com.example.portatil.act01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TalkerOH {

    // Database fields
    private SQLiteDatabase baseDadesLlegir,baseDadesEscriure;
    private MyOpenHelper bdAjudant;

    public TalkerOH(Context context) {
        //per poder comunucarnos amb la base de dades
        bdAjudant = new MyOpenHelper(context);
        //creme dos database per lleguir i modifica la bsase de dades
        baseDadesLlegir= bdAjudant.getReadableDatabase();
        baseDadesEscriure=bdAjudant.getWritableDatabase();
    }

    //per destruir


    @Override
    protected void finalize(){
        baseDadesLlegir.close();
        baseDadesEscriure.close();
    }

    //METODES QUE SERVEIXEN PER LLEGUIR CAMPS DE LA BASE DE DADES\\
    //metode que retorna un curso amb tota la informacio de totes les columnes que te la base de dades
    public Cursor carregaTotaLaTaula() {
        // Retorem totes les tasques
        return baseDadesLlegir.query(bdAjudant.TABLE_PRODUCTES, new String[]{bdAjudant.COLUMN_ID,bdAjudant.COLUMN_CODI,bdAjudant.COLUMN_DESCRIPCIO,bdAjudant.COLUMN_PVP,bdAjudant.COLUMN_STOCK},
                null, null,
                null, null, bdAjudant.COLUMN_ID);
    }
    //metode que retorna el valor de una sola row
    public Cursor task(long id) {
        // Retorna un cursor només amb el id indicat
        // Retornem les tasques que el camp DONE = 1
        return baseDadesLlegir.query(bdAjudant.TABLE_PRODUCTES, new String[]{bdAjudant.COLUMN_ID,bdAjudant.COLUMN_CODI,bdAjudant.COLUMN_DESCRIPCIO,bdAjudant.COLUMN_PVP,bdAjudant.COLUMN_STOCK},
                bdAjudant.COLUMN_ID+ "=?", new String[]{String.valueOf(id)},
                null, null, null);

    }



    //METODES QUE SERVEIXEN PER MODIFICAR LA BASE DE DADES \\
//metode que fa insert a la base de dades en la taula productes
    public long AfegirProducte(String codi, String descripcio, double pvp , int stock){

        ContentValues valors =new ContentValues();

        valors.put(bdAjudant.COLUMN_CODI, codi);
        valors.put(bdAjudant.COLUMN_DESCRIPCIO, descripcio);
        valors.put(bdAjudant.COLUMN_PVP, pvp);
        valors.put(bdAjudant.COLUMN_STOCK, stock);

        return baseDadesEscriure.insert(bdAjudant.TABLE_PRODUCTES,null,valors);
    }
}