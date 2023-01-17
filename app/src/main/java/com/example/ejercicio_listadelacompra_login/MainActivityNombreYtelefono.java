package com.example.ejercicio_listadelacompra_login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.ejercicio_listadelacompra_login.databinding.ActivityLogInBinding;
import com.example.ejercicio_listadelacompra_login.databinding.ActivityMainNombreYtelefonoBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityNombreYtelefono extends AppCompatActivity {

    private ActivityMainNombreYtelefonoBinding binding;
    private FirebaseAuth auth; //métodos para dar soporte a cualquier identificación que me aporte Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nombre_ytelefono);
        
        binding = ActivityMainNombreYtelefonoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        auth = FirebaseAuth.getInstance();
        
        binding.btnGuardarNombreYtel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = binding.txtNombreNomYtel.getText().toString();
                String telefono = binding.txtTelefonoNomYtel.getText().toString();


                if(!nombre.isEmpty() && telefono.length()>8){
                    doCrearNombreYTel(nombre, telefono);
                }
            }
        });
    }

    private void doCrearNombreYTel(String nombre, String telefono) {
    }
}