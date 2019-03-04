package mx.edu.ittepic.myapplication;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    ArrayList<PacientesVo> listaUsuario;
    RecyclerView recyclerViewUsuarios;

    BaseDatos conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        conn=new BaseDatos(this,"hospital",null,1);

        listaUsuario=new ArrayList<>();

        recyclerViewUsuarios= findViewById(R.id.idRecycler);
        recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(this));

        SQLiteDatabase tabla=conn.getReadableDatabase();
        String SQL ="SELECT * FROM PACIENTE";

        Cursor resultado=tabla.rawQuery(SQL, null);

        PacientesVo usuario=null;


        while (resultado.moveToNext()){
        usuario=new PacientesVo();
            usuario.setId(resultado.getString(0));
            usuario.setNombre(resultado.getString(1));
            usuario.setRfc(resultado.getString(2));
            usuario.setCelular(resultado.getString(3));
            usuario.setEmail(resultado.getString(4));
            usuario.setFecha(resultado.getString(5));

            listaUsuario.add(usuario);
        }
        Adapter adapter=new Adapter(listaUsuario);
        recyclerViewUsuarios.setAdapter(adapter);

    }

}
