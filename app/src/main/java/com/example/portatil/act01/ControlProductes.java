package com.example.portatil.act01;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ControlProductes extends AppCompatActivity  implements View.OnClickListener{

    private long task,id;
    private TalkerOH bd;
    Toast toast ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_productes);

        bd=new TalkerOH(this);

        task = this.getIntent().getExtras().getLong("id");
        id = this.getIntent().getExtras().getLong("id");

        Button ok=(Button) findViewById(R.id.btnOk);
        ok.setOnClickListener(this);
        Button cancelar=(Button) findViewById(R.id.btnCancelar);
        cancelar.setOnClickListener(this);
        Button deletear=(Button) findViewById(R.id.btnDelete);
        deletear.setOnClickListener(this);
        if(task==-1){
            deletear.setVisibility(View.GONE);

        }
        if(id!=-1){
            deletear.setVisibility(View.VISIBLE);
            cargarDatos();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnOk:
                afegir();
                break;
            case R.id.btnCancelar:
                cancelar();
                break;
            case R.id.btnDelete:
                borrar(id);
                break;
        }


    }

    private void cargarDatos() {

        // Demanem un cursor que retorna un sol registre amb les dades de la tasca
        // Això es podria fer amb un classe pero...
        Cursor datos = bd.task(id);
        datos.moveToFirst();

        // Carreguem les dades en la interfície
        TextView tv;

        tv = (TextView) findViewById(R.id.edtCodi);
        tv.setText(datos.getString(datos.getColumnIndex(MyOpenHelper.COLUMN_CODI)));

        tv = (TextView) findViewById(R.id.edtDescripcion);
        tv.setText(datos.getString(datos.getColumnIndex(MyOpenHelper.COLUMN_DESCRIPCIO)));

        tv = (TextView) findViewById(R.id.editboxPVP);
        tv.setText(datos.getString(datos.getColumnIndex(MyOpenHelper.COLUMN_PVP)));

        tv = (TextView) findViewById(R.id.editboxStock);
        tv.setText(datos.getString(datos.getColumnIndex(MyOpenHelper.COLUMN_STOCK)));


    }




    private void afegir() {
        // Validem les dades
        TextView tv;

        Toast toast ;


        // Títol ha d'estar informat
        tv = (TextView) findViewById(R.id.edtCodi);
        String codi = tv.getText().toString();
        if (codi.trim().equals("")) {

            toast =Toast.makeText(ControlProductes.this,"El camp codi te que ser obligatori", Toast.LENGTH_SHORT);
            toast.show();

            return;
        }

        tv = (TextView) findViewById(R.id.edtDescripcion);
        String descripcion = tv.getText().toString();
        tv = (TextView) findViewById(R.id.editboxPVP);
        double PVP;


        try {
            PVP = Double.valueOf(tv.getText().toString());
        }
        catch (Exception e) {
            toast =Toast.makeText(ControlProductes.this,"El camp codi te que ser obligatori", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        tv = (TextView) findViewById(R.id.editboxStock);
        Integer stock;


        try {
            stock = Integer.valueOf(tv.getText().toString());
        }
        catch (Exception e) {
            toast =Toast.makeText(ControlProductes.this,"El camp codi te que ser obligatori", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }


        // Mirem si estem creant o estem guardant
        if (task == -1) {
            task = bd.AfegirProducte(codi, descripcion,PVP,stock);
        }
        else {
            bd.taskUpdate(id,descripcion,PVP,stock);
        }

        Intent mIntent = new Intent();
        mIntent.putExtra("id", task);
        setResult(RESULT_OK, mIntent);

        finish();
    }

    private void cancelar() {
        Intent mIntent = new Intent();
        mIntent.putExtra("id", id);
        setResult(RESULT_CANCELED, mIntent);

        finish();
    }

    private  void borrar(long clauprimaria){

        // Pedimos confirmación
        bd.taskDelete(clauprimaria);

        Intent mIntent = new Intent();
        mIntent.putExtra("id", -1);  // Devolvemos -1 indicant que s'ha eliminat
        setResult(RESULT_OK, mIntent);

        finish();
    }
}
