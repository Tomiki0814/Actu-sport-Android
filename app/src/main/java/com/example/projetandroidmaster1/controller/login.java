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

import com.example.projetandroidmaster1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    Button b1,b2;
    EditText ed1,ed2;
    ProgressBar pr;
    TextView error;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("");
        error = findViewById(R.id.error);
        pr = findViewById(R.id.progressbar);
        pr.setVisibility(View.GONE);
        b1 = (Button)findViewById(R.id.connexion);
        b2 = (Button)findViewById(R.id.register);
        ed1 = findViewById(R.id.mail);
        ed2 = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(v);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, Inscription.class);
                //this is used as activity class is subclass of Context
                startActivity(intent);
                finish();
            }
        });
    }
    private void loginUser(View v){
        pr.setVisibility(View.VISIBLE);
        String email = ed1.getText().toString();
        String password = ed2.getText().toString();

        if (TextUtils.isEmpty(email)){
           error.setText(" Email ne peut pas être vide");
            pr.setVisibility(View.GONE);
        }else if (TextUtils.isEmpty(password)){
            error.setText(" Mot de passe ne peut pas être vide");
            pr.setVisibility(View.GONE);
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Intent i = new Intent(v.getContext(), afterlogin.class);
                        i.putExtra("user",email);
                        v.getContext().startActivity(i);
                        finish();
                    }else{
                        error.setText("Vérifier vos identifiants");
                    }
                    pr.setVisibility(View.GONE);
                }
            });
        }
    }
}