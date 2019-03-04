package mx.edu.ittepic.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper {
    public SQLiteDatabase myDB;
    public BaseDatos(Context context, String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PACIENTE (ID INTEGER PRIMARY KEY NOT NULL, NOMBRE VARCHAR(200), RFC VARCHAR (50), CELULAR VARCHAR(20), EMAIL VARCHAR (50), FECHA VARCHAR(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getAll(){

        String query="SELECT * FROM  PACIENTES ";

        return myDB.rawQuery(query,null);

    }
}
