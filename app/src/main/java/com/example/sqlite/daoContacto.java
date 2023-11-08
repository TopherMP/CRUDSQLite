package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class daoContacto {
    SQLiteDatabase bd;
    ArrayList<Contacto> lista = new ArrayList<Contacto>();
    Contacto c;
    Context ct;
    String nombreBD = "BDContactos";
    String tabla = "create table if not exists cont(id INTEGER PRIMARY KEY autoincrement, nombre TEXT, apellido TEXT, email TEXT, telefono TEXT, ciudad TEXT)";

    public daoContacto(Context ct){
        this.ct = ct;
        bd = ct.openOrCreateDatabase(nombreBD, Context.MODE_PRIVATE,null);
        bd.execSQL(tabla);
    }

    public boolean insertar(Contacto c){
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", c.getNombre());
        contenedor.put("apellido", c.getApellido());
        contenedor.put("email", c.getEmail());
        contenedor.put("telefono", c.getTelefono());
        contenedor.put("ciudad", c.getCiudad());
        return bd.insert("cont",null,contenedor)>0;
    }
    public boolean eliminar(int id){
        return bd.delete("cont","id="+id,null)>0;
    }
    public boolean editar(Contacto c){
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", c.getNombre());
        contenedor.put("apellido", c.getApellido());
        contenedor.put("email", c.getEmail());
        contenedor.put("telefono", c.getTelefono());
        contenedor.put("ciudad", c.getCiudad());
        return bd.update("cont",contenedor,"id="+c.getId(),null)>0;
    }

    public ArrayList<Contacto> vertodo(){
        lista.clear();
        Cursor cursor = bd.rawQuery("select * from cont",null);
        if (cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                lista.add(new Contacto(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                        )
                );
            }while(cursor.moveToNext());
        }
        return lista;
    }
    public Contacto veruno(int pos){
        Cursor cursor = bd.rawQuery("select * from cont;",null);
        cursor.moveToPosition(pos);
        c = new Contacto(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
        );
        return c;
    }
}
