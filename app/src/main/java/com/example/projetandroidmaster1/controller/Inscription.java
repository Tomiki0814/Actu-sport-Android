package com.example.projetandroidmaster1.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetandroidmaster1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Inscription extends AppCompatActivity {
    Button inscription,login;
    EditText pseudo,mdp1,mdp2;
    FirebaseAuth mAuth;
    ProgressBar pr;
    TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        initView();
        setTitle("");
        pr = findViewById(R.id.progressbar);
        pr.setVisibility(View.GONE);
        error = findViewById(R.id.error);
    }

    private void initView() {
        inscription = (Button) findViewById(R.id.register_button);
        login =(Button) findViewById(R.id.login_button);
        pseudo = (EditText) findViewById(R.id.pseudo);
        mdp1 = (EditText) findViewById(R.id.mdp1);
        mdp2 = (EditText) findViewById(R.id.mdp2);
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inscription.this,login.class);
                startActivity(intent);
            }
        });
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inscription();

            }
        });
    }

    private void inscription(){
        pr.setVisibility(View.VISIBLE);
        pseudo = (EditText) findViewById(R.id.pseudo);
        mdp1 = (EditText) findViewById(R.id.mdp1);
        mdp2 = (EditText) findViewById(R.id.mdp2);
        String email = pseudo.getText().toString();
        String password = mdp1.getText().toString();
        String password2 = mdp2.getText().toString();

        if (TextUtils.isEmpty(email)){
            error.setText("Email ne doit pas être vide");
            pr.setVisibility(View.GONE);
        }else if (TextUtils.isEmpty(password) || password.length()<8){
            error.setText("Mot de passe: 8 caractères au moins");
            pr.setVisibility(View.GONE);
        }else if (password.compareTo(password2)!=0){
            error.setText("Les 2 mots de passe doivent correspondre ");
            pr.setVisibility(View.GONE);
        }
        else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Inscription.this, "Compte créé avec succès", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Inscription.this, com.example.projetandroidmaster1.controller.login.class));
                        finish();
                    }else{
                        //Toast.makeText(Inscription.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        error.setText(task.getException().getMessage());
                    }
                    pr.setVisibility(View.GONE);
                }
            });
        }
    }
}