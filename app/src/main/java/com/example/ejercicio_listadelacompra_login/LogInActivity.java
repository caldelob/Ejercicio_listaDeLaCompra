package com.example.ejercicio_listadelacompra_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ejercicio_listadelacompra_login.databinding.ActivityLogInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private ActivityLogInBinding binding;
    private FirebaseAuth auth; //métodos para dar soporte a cualquier identificación que me aporte Firebase



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        binding.btnDologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String email = binding.txtEmailLogin.getText().toString();
               String password = binding.txtPasswordLogin.getText().toString();

               if(!email.isEmpty() && password.length()>5){
                   doLogIn(email, password);
               }
            }
        });

        binding.btnDoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.txtEmailLogin.getText().toString();
                String password = binding.txtPasswordLogin.getText().toString();

                if(!email.isEmpty() && password.length()>5){
                    doRegister(email, password);
                }
            }
        });
    }

    private void doRegister(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {//con esto puedo comprobar que ha pasado con el usuario y la contraseña
                        if(task.isSuccessful()){

                            FirebaseUser user = auth.getCurrentUser();
                            upDateUI(user);

                        }else{
                            Toast.makeText(LogInActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LogInActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void doLogIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {//con esto puedo comprobar que ha pasado con el usuario y la contraseña
                        if(task.isSuccessful()){

                            FirebaseUser user = auth.getCurrentUser();
                            upDateUI(user);

                        }else{
                            Toast.makeText(LogInActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LogInActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void upDateUI(FirebaseUser user) {
        //si el user existe, tenemos que abrir el main y cerrar la ventana de Login

        if(user!= null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {  //Login Persistente
        super.onStart();
        upDateUI(auth.getCurrentUser());
    }
}