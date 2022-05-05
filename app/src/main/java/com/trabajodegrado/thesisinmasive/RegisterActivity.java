package com.trabajodegrado.thesisinmasive;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trabajodegrado.thesisinmasive.models.User;

public class RegisterActivity extends AppCompatActivity {


    SharedPreferences mPref;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    //Instanciar Vistas de registro
    Button mButtonRegister;
    TextInputEditText mtextInputEmail;
    TextInputEditText mtextInputName;
    TextInputEditText mtextInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Instanciar
        mAuth = FirebaseAuth.getInstance();

        //Referencia nodo principal de la DB realtime
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
        //      String selectedUser = mPref.getString("user", "");

        mButtonRegister =findViewById(R.id.btnRegister);
        mtextInputEmail = findViewById(R.id.textInputEmail);
        mtextInputName = findViewById(R.id.textInputName);
        mtextInputPassword = findViewById(R.id.textInputPassword);


        //Confirmamos el campo de Usuario
        //String selectedUser;
        Toast.makeText(this, "El Valor que selecciono fue", Toast.LENGTH_SHORT).show();

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }
//Creamos metodo de registro paraobtener los datos de los campos de texto Usuario, email y contraseña
    void registerUser() {
        String name = mtextInputName.getText().toString();
        String email = mtextInputEmail.getText().toString();
        String password = mtextInputPassword.getText().toString();

        //Validacion para los campos vacios
        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            //Validacion de contraseña
            if (password.length() >=6){
                //Recibe el email y la contraseña
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Validamos si fue exitoso el registro en Firebase
                        if (task.isSuccessful()){
                            //Ejecuto Metodo para alojar los datos del usuario Registrado en Realtime de la DB
                            saveUser(name, email);
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "No se Pudo Registrar el Usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else{
                Toast.makeText(this, "La Contraseña debe Tener almenos 6 Caracteres", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Ingrese Todos los Campos", Toast.LENGTH_SHORT).show();
        }
    }
    void saveUser(String name, String email){
        //Obtener Valores de las Preferencias y validar tipo de usuario
        String selectedUser = mPref.getString("user", "");

        //Importamos modelo del paquete "User.java"
        User user = new User();
        user.setEmail(email);
        user.setName(name);

        if (selectedUser.equals("driver")){

            //Creamos dentro del nodo "Conductor" un identificador unico para cada usuario con push
            mDatabase.child("Users").child("Drivers").push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                     if (task.isSuccessful()){
                         Toast.makeText(RegisterActivity.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                     }
                     else {
                         Toast.makeText(RegisterActivity.this, "Fallo el Registro", Toast.LENGTH_SHORT).show();
                     }
                }
            });

        }
        else if (selectedUser.equals("client")){

            //Creamos dentro del nodo "Cliente" un identificador unico para cada cliente con push
            mDatabase.child("Users").child("Clients").push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Fallo el Registro", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }



    }

}