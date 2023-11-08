package com.example.sqlite;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    ArrayList<Contacto> lista;
    daoContacto dao;
    Contacto c;
    Activity a;
    int id = 0;

    public Adaptador(Activity a,ArrayList<Contacto> lista, daoContacto dao ) {
        this.lista = lista;
        this.dao = dao;
        this.a = a;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public int getCount(){
        return lista.size();
    }
    @Override
    public Object getItem(int i){
        c=lista.get(i);
        return null;
    }
    @Override
    public long getItemId(int i){
        c=lista.get(i);
        return c.getId();
    }
    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup){
        View v = view;
        if (v == null){
            LayoutInflater li = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.item, null);
        }

        c = lista.get(posicion);

        TextView nombre = v.findViewById(R.id.tvNombre);
        TextView apellido = v.findViewById(R.id.tvApellido);
        TextView telefono = v.findViewById(R.id.tvTelefono);
        TextView email = v.findViewById(R.id.tvEmail);
        TextView ciudad = v.findViewById(R.id.tvCiudad);
        Button edit = v.findViewById(R.id.btnEdit);
        Button eliminar = v.findViewById(R.id.btnDelete);

        nombre.setText(c.getNombre());
        apellido.setText(c.getApellido());
        telefono.setText(c.getTelefono());
        email.setText(c.getEmail());
        ciudad.setText(c.getCiudad());

        edit.setTag(posicion);
        eliminar.setTag(posicion);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = Integer.parseInt(v.getTag().toString());

                final Dialog dialog = new Dialog(a);
                dialog.setTitle("Editar registro");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogo);
                dialog.show();

                final EditText nombre = dialog.findViewById(R.id.etNombre);
                final EditText apellido = dialog.findViewById(R.id.etApellido);
                final EditText email = dialog.findViewById(R.id.etEmail);
                final EditText telefono = dialog.findViewById(R.id.etTelefono);
                final EditText ciudad = dialog.findViewById(R.id.etCiudad);
                Button guardar = dialog.findViewById(R.id.btnAdd);
                Button cancelar = dialog.findViewById(R.id.btnCancel);

                c = lista.get(pos);
                setId(c.getId());

                nombre.setText(c.getNombre());
                apellido.setText(c.getApellido());
                telefono.setText(c.getTelefono());
                email.setText(c.getEmail());
                ciudad.setText(c.getCiudad());

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            c = new Contacto(getId(),
                                    nombre.getText().toString(),
                                    apellido.getText().toString(),
                                    email.getText().toString(),
                                    telefono.getText().toString(),
                                    ciudad.getText().toString());
                            dao.editar(c);
                            lista = dao.vertodo();
                            notifyDataSetChanged();
                            dialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(a, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = Integer.parseInt(v.getTag().toString());
                c = lista.get(pos);
                setId(c.getId());
                AlertDialog.Builder del = new AlertDialog.Builder(a);
                del.setMessage("Seguro que deseas eliminar?");
                del.setCancelable(false);
                del.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dao.eliminar(getId());
                        lista = dao.vertodo();
                        notifyDataSetChanged();
                    }
                });
                del.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });
                del.show();
            }
        });
        return v;
    }
}
