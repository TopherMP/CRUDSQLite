package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    daoContacto dao;
    Adaptador adapter;
    ArrayList<Contacto> lista;
    Contacto c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new daoContacto(MainActivity.this);
        lista = dao.vertodo();
        adapter = new Adaptador(this, lista, dao);
        ListView list = findViewById(R.id.Lista);
        Button insertar = findViewById(R.id.btnInsert);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setTitle("Nuevo registro");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogo);
                dialog.show();

                final EditText nombre = dialog.findViewById(R.id.etNombre);
                final EditText apellido = dialog.findViewById(R.id.etApellido);
                final EditText email = dialog.findViewById(R.id.etEmail);
                final EditText telefono = dialog.findViewById(R.id.etTelefono);
                final EditText ciudad = dialog.findViewById(R.id.etCiudad);

                Button guardar = dialog.findViewById(R.id.btnAdd);
                guardar.setText("Agregar");
                Button cancelar = dialog.findViewById(R.id.btnCancel);
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            c = new Contacto(
                                    nombre.getText().toString(),
                                    apellido.getText().toString(),
                                    email.getText().toString(),
                                    telefono.getText().toString(),
                                    ciudad.getText().toString()
                            );
                            dao.insertar(c);
                            lista = dao.vertodo();
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }catch(Exception e){
                            Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
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

    }
}