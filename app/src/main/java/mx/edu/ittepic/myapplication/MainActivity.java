package mx.edu.ittepic.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText identificar, nombre, rfc, celular, email, fecha;
    Button insertar, consultar, modificar, eliminar, todo;

    BaseDatos base;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        identificar=findViewById(R.id.identificador);
        nombre=findViewById(R.id.nombre);
        rfc=findViewById(R.id.rfc);
        celular=findViewById(R.id.celular);
        email=findViewById(R.id.email);
        fecha=findViewById(R.id.fecha);


        insertar=findViewById(R.id.insertar);
        consultar=findViewById(R.id.consultar);
        modificar=findViewById(R.id.modificar);
        eliminar=findViewById(R.id.eliminar);
        todo=findViewById(R.id.verdatos);

        base=new BaseDatos(this, "hospital", null, 1);
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codigoInsertar();
            }
        });
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedirID(1);
            }
        });
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(modificar.getText().toString().startsWith("CONFIRMAR"))
                {
                    invocarConfirmacionActualizar();
                }
                else
                {
                    pedirID(2);
                }
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedirID(3);

            }
        });
        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent miIntent =new Intent(MainActivity.this,Main2Activity.class);
                startActivity(miIntent);
            }
        });
    }
    private void codigoInsertar() {
        try
        {
            SQLiteDatabase tabla= base.getWritableDatabase();
            String SQL ="INSERT INTO PACIENTE VALUES("+identificar.getText().toString()+",'"
                    +nombre.getText().toString()+"','"
                    +rfc.getText().toString()+"','"
                    +celular.getText().toString()+"','"
                    +email.getText().toString()+"','"
                    +fecha.getText().toString()+"')";

            tabla.execSQL(SQL);
            Toast.makeText(this, "USUARIO NUEVO CREADO", Toast.LENGTH_LONG).show();
            tabla.close();
        }
        catch (SQLiteException e)
        {
            Toast.makeText(this, "NO SE CREO NINGUN USUARIO", Toast.LENGTH_LONG).show();
        }
        identificar.setText("");
        nombre.setText("");
        rfc.setText("");
        celular.setText("");
        email.setText("");
        fecha.setText("");
    }
    private void pedirID(final int origen ) {

        final EditText pedirID=new EditText(this);
        pedirID.setInputType(InputType.TYPE_CLASS_NUMBER);
        pedirID.setHint("VALOR ENTERO MAYOR DE 0");
        String mensaje="Escriba el ID a buscar";
        String mensaje2="Buscar";
        AlertDialog.Builder alerta =new AlertDialog.Builder(this);
        if(origen==2)
        {
            mensaje="Escriba el ID a modificar";
            mensaje2="Modificar";
        }
        if(origen==3)
        {
            mensaje="Escriba el ID a eliminar";
            mensaje2="Eliminar";
        }
        alerta.setTitle("ATENCION!!")
                .setMessage(mensaje)
                .setView(pedirID)
                .setPositiveButton(mensaje2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (pedirID.getText().toString().isEmpty())
                        {
                            Toast.makeText(MainActivity.this, "DEBES INGRESAR UN NUMERO", Toast.LENGTH_LONG).show();
                            return;
                        }
                        buscarDato(pedirID.getText().toString(), origen);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("CANCELAR", null).show();
    }
    private void buscarDato(String idABuscar, int origen)
    {
        try
        {
            SQLiteDatabase tabla=base.getReadableDatabase();
            String SQL ="SELECT * FROM PACIENTE  WHERE ID="+idABuscar;

            Cursor resultado=tabla.rawQuery(SQL, null);

            if (resultado.moveToFirst())
            {
                //si hay

                if(origen==3)
                {
                    eliminardato(idABuscar);
                    return;
                }
                identificar.setText(resultado.getString(0));
                nombre.setText(resultado.getString(1));
                rfc.setText(resultado.getString(2));
                celular.setText(resultado.getString(3));
                email.setText(resultado.getString(4));
                fecha.setText(resultado.getString(5));

                if(origen==2)
                {
                    insertar.setEnabled(false);
                    consultar.setEnabled(false);
                    eliminar.setEnabled(false);
                    modificar.setText("CONFIRMAR ACTUALIZACIÓN");
                    identificar.setEnabled(false);
                }
            }
            else
            {
                //no hay
                Toast.makeText(this, "NO HAY DATOS", Toast.LENGTH_LONG).show();
            }
            tabla.close();
        }
        catch (SQLiteException e)
        {
            Toast.makeText(this, "NO SE PUDO BUSCAR", Toast.LENGTH_LONG).show();
        }
    }

    private void eliminardato(String idABuscar) {
        try {
            SQLiteDatabase tabla = base.getWritableDatabase();

            String SQL = "DELETE FROM PACIENTE WHERE ID=" + idABuscar;
            tabla.execSQL(SQL);
            Toast.makeText(this, "ELIMINADO", Toast.LENGTH_LONG).show();
            tabla.close();
            identificar.setText("");
            nombre.setText("");
            rfc.setText("");
            celular.setText("");
            email.setText("");
            fecha.setText("");
        }
        catch (SQLiteException e)
        {
            Toast.makeText(this, "NO SE PUDO ELIMINAR", Toast.LENGTH_LONG).show();
        }
    }
    private void invocarConfirmacionActualizar() {
        AlertDialog.Builder confirmar=new AlertDialog.Builder(this);
        confirmar.setTitle("IMPORTANTE!!")
                .setMessage("¿Estas seguro que deseas actualizar?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        actualizarDatos();
                        dialog.dismiss();
                    }
                }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                habilitarBotonesyLimpiarCampos();
                dialog.cancel();
            }
        }).show();

    }
    private void actualizarDatos() {
        try
        {
            SQLiteDatabase tabla =base.getWritableDatabase();
            String SQL = "UPDATE PACIENTE SET NOMBRE='"+nombre.getText().toString()+"'," +
                    " RFC='"+rfc.getText().toString()+"'," +
                    " CELULAR='"+celular.getText().toString()+"'," +
                    " EMAIL='"+email.getText().toString()+"',"+
                    " FECHA='"+fecha.getText().toString()+"' WHERE ID="+identificar.getText().toString();
            tabla.execSQL(SQL);
            tabla.close();
            Toast.makeText(this, "SE ACTUALIZO", Toast.LENGTH_LONG).show();
        }
        catch (SQLiteException e)
        {
            Toast.makeText(this, "NO SE PUDO ACTUALIZAR", Toast.LENGTH_LONG).show();
        }
        habilitarBotonesyLimpiarCampos();
    }

    private void habilitarBotonesyLimpiarCampos() {
        identificar.setText("");
        nombre.setText("");
        rfc.setText("");
        celular.setText("");
        email.setText("");
        fecha.setText("");

        insertar.setEnabled(true);
        consultar.setEnabled(true);
        eliminar.setEnabled(true);

        identificar.setEnabled(true);
    }
}
