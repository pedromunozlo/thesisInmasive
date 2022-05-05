package com.trabajodegrado.thesisinmasive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mButtonIAmClient;
    Button mButtonIAmDriver;
    private Object MainActivity;

    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializo la preferencias de los botones Conductor / Cliente y establezco el identificador
        String s;
        mPref = getApplicationContext().getSharedPreferences( "typeUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPref.edit();

        mButtonIAmClient=findViewById(R.id.btnIAmClient);
        mButtonIAmDriver=findViewById(R.id.btnIAmDriver);

        mButtonIAmClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Implemento Identificador en el Boton Cliente y guarde el valor en el metodo apply
                String s;
                editor.putString("User", " Client");
                editor.apply();
                goToSelectAuth();
            }
        });

        mButtonIAmDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Implemento Identificador en el Boton Conductor y guarde el valor
                String s;
                editor.putString("User", " Conductor");
                editor.apply();
                goToSelectAuth();
            }
        });

    }

    private void goToSelectAuth() {
        Intent intent = new Intent(MainActivity.this, SelectOptionAuthActivity.class);
        startActivity(intent);

    }
}