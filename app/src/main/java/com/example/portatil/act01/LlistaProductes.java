package com.example.portatil.act01;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class LlistaProductes extends AppCompatActivity implements View.OnClickListener {




    private static int ACTIVITY_TASK_ADD = 1;
    private static int ACTIVITY_TASK_UPDATE = 2;

    //private MyOpenHelper bd;
    private TalkerOH comunicador;
    private long idActual;
    private int positionActual;
    private SimpleCursorAdapter scTasks;


    private static String[] from = new String[]{MyOpenHelper.COLUMN_CODI,MyOpenHelper.COLUMN_DESCRIPCIO,MyOpenHelper.COLUMN_PVP,MyOpenHelper.COLUMN_STOCK};
    private static int[] to = new int[]{R.id.codi,R.id.descripcio,/*R.id.PVP,*/R.id.estoc};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistra_productes);
        Button afegir=(Button) findViewById(R.id.btnAfegir);
        afegir.setOnClickListener(this);


        comunicador = new TalkerOH(this);
        loadTasks();
        ListView llista=(ListView)findViewById(R.id.llista);
        llista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View vista, int posicion,
                                    long id) {
                updateTask(id);
            }

        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnAfegir:

                Bundle bundle = new Bundle();
                bundle.putLong("id",-1);


                Intent controlDelsProductes = new Intent(LlistaProductes.this, ControlProductes.class);
                controlDelsProductes.putExtras(bundle);
                startActivityForResult(controlDelsProductes,ACTIVITY_TASK_ADD);

                break;
        }


    }

    private void loadTasks() {

        // Demanem totes les tasques
        Cursor cursorTasks = comunicador.carregaTotaLaTaula();

        // Now create a simple cursor adapter and set it to display
        scTasks = new SimpleCursorAdapter(this, R.layout.simpel_cursor, cursorTasks, from, to, 1);
        //setListAdapter(scTasks);
        ListView llista=(ListView)findViewById(R.id.llista);
        llista.setAdapter(scTasks);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_TASK_ADD) {
            if (resultCode == RESULT_OK) {
                // Carreguem totes les tasques a lo bestia
                refreshTasks();
            }
        }

        if (requestCode == ACTIVITY_TASK_UPDATE) {
            if (resultCode == RESULT_OK) {
                refreshTasks();
            }
        }

    }
    private void refreshTasks() {

        // Demanem totes les tasques
        Cursor cursorTasks = comunicador.carregaTotaLaTaula();

        // Now create a simple cursor adapter and set it to display
        scTasks.changeCursor(cursorTasks);
        scTasks.notifyDataSetChanged();
    }

    private void updateTask(long id) {
        // Cridem a l'activity del detall de la tasca enviant com a id -1
        Bundle bundle = new Bundle();
        bundle.putLong("id",id);

        idActual = id;


        Intent i = new Intent(LlistaProductes.this, ControlProductes.class );
        i.putExtras(bundle);
        startActivityForResult(i,ACTIVITY_TASK_UPDATE);
    }


}
