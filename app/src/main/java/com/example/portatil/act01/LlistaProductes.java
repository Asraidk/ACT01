package com.example.portatil.act01;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

//implementem el view per fer els botons escolitn els click dels botons
public class LlistaProductes extends AppCompatActivity implements View.OnClickListener {
    //varieables per fer actes de fe per que es reinici al tornar al activiti sobre el listview y els roductes
    private static int ACTIVITY_TASK_ADD = 1;
    private static int ACTIVITY_TASK_UPDATE = 2;
    //variables utilitzades
    //private MyOpenHelper bd;
    private TalkerOH comunicador;
    private long idActual;
    private int positionActual;
    /*private SimpleCursorAdapter scTasks;*///sino tuviesemos cambios de color usamos esta en plan solo mostrar info
    private adapterTodoListFilter scTasks;
    //camps que mostrem del nostre array en el list view(creo)
    private static String[] from = new String[]{MyOpenHelper.COLUMN_CODI,MyOpenHelper.COLUMN_DESCRIPCIO,/*MyOpenHelper.COLUMN_PVP,*/MyOpenHelper.COLUMN_STOCK};
    private static int[] to = new int[]{R.id.codi,R.id.descripcio,/*R.id.PVP,*/R.id.estoc};
    /*on create posem les intancies dels botons que tenim per fer les funciones que busquem*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistra_productes);
        Button afegir=(Button) findViewById(R.id.btnAfegir);
        afegir.setOnClickListener(this);
        Button filtar=(Button) findViewById(R.id.btnFiltrar);
        filtar.setOnClickListener(this);
        Button senseFiltre=(Button) findViewById(R.id.senseFiltre);
        senseFiltre.setOnClickListener(this);

        //part sobre els click en la list view per llançar un metode
        comunicador = new TalkerOH(this);
        carregaCamps();
        ListView llista=(ListView)findViewById(R.id.llista);
        llista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View vista, int posicion,
                                    long id) {
                updateTask(id);
            }

        });
    }

    //determinar quin item es fa click del activyti
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
            case R.id.btnFiltrar:

                filtrarPerDescripcio();
                /*buscar maneras per poder fer un curso que retorne "" o un null no torbat*/
                break;
            case R.id.senseFiltre:
                carregaCamps();
                /*buscar maneras per poder fer un curso que retorne "" o un null no torbat*/
                break;
        }


    }

    private void filtrarPerDescripcio() {
        // Demanem totes les tasques pendents
        Cursor cursorTasks = comunicador.filtre();

        // Notifiquem al adapter que les dades han canviat i que refresqui
        scTasks.changeCursor(cursorTasks);
        scTasks.notifyDataSetChanged();

        // Ens situem en el primer registre

    }

    //metode cridat on create per que carregui tota la informacio de la bd en la listview de la nostre activity
    private void carregaCamps() {

        // Demanem els row que ens interesan de la taula
        Cursor cursorTasks = comunicador.carregaTotaLaTaula();
        scTasks = new adapterTodoListFilter(this, R.layout.simpel_cursor, cursorTasks, from, to, 1);
        //setListAdapter(scTasks);
        ListView llista=(ListView)findViewById(R.id.llista);
        llista.setAdapter(scTasks);
    }
    //metode fe, per quan torna de una activity que no sigui la main on tenim la list view fasi un reload y mostri
    //tota la nova informacio de la bs
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
    //metode que retrna un altre cop els valor de la bd de la taula indicada
    private void refreshTasks() {

        // Demanem totes les tasques
        Cursor cursorTasks = comunicador.carregaTotaLaTaula();

        // Now create a simple cursor adapter and set it to display
        scTasks.changeCursor(cursorTasks);
        scTasks.notifyDataSetChanged();
    }
    //metode cridat al fer click en  una row del list view per poder fer upadetes al camps que ens permeti
    //l'alicacio
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
//extendero el cursor per tal que les row es pintin o mostrin mes coses de les que nosaltres volem
class adapterTodoListFilter extends android.widget.SimpleCursorAdapter {
    private static final String colorTaskPending = "#d78290";
    private static final String colorTaskCompleted = "#d7d7d7";

    public adapterTodoListFilter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        // Agafem l'objecte de la view que es una LINEA DEL CURSOR
        Cursor linia = (Cursor) getItem(position);
        Integer done = linia.getInt(linia.getColumnIndexOrThrow(MyOpenHelper.COLUMN_STOCK));

        // Pintem el fons de la view segons està completada o no
        if (done>0) {
            view.setBackgroundColor(Color.parseColor(colorTaskCompleted));
        }
        else {
            view.setBackgroundColor(Color.parseColor(colorTaskPending));
        }

        return view;
    }
}