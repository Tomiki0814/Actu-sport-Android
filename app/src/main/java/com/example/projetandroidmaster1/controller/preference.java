package com.example.projetandroidmaster1.controller;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projetandroidmaster1.R;
import com.example.projetandroidmaster1.model.Preference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class preference extends AppCompatActivity {
    private FirebaseDatabase database;
    Spinner sp;
    Button btn;
    String[] list_sport={"FootBall","Tennis","Basketball","Formule 1"};
    String choix="";
    String user;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        InitView();
        choix_sport();
        onClick();
        setTitle("");
        Bundle extra = getIntent().getExtras();
        user =extra.getString("user");
        choix = extra.getString("choix");
        preferenceDefault();
        database = FirebaseDatabase.getInstance();
        progressBar= findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);



    }

    private void InitView(){
        this.sp=(Spinner)findViewById(R.id.spinner);
        this.btn=(Button)findViewById(R.id.btn);
        ArrayAdapter adp=new ArrayAdapter(this,R.layout.spinner_1,this.list_sport);
        this.sp.setAdapter(adp);

        choix= preference.this.sp.getSelectedItem().toString();

    }


    //choix
    private void choix_sport(){
        this.sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choix="";
                choix= preference.this.sp.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //sauvegarder
    private void onClick(){
        this.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertUpdate(user,choix);
            }
        });
    }

    private void InsertPreference(String selected){
        String utilisateur = user;
        String choix = selected;
        Preference pr = new Preference(user, choix);
        progressBar.setVisibility(View.VISIBLE);
        database.getReference().child("preference").push().setValue(pr).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    private void UpdatePreference(String selected, String key) {
        String choix = selected;
        String mere = key;
        HashMap hashMap = new HashMap();
        hashMap.put("choix",choix);
        progressBar.setVisibility(View.VISIBLE);
        database.getReference().child("preference").child(mere).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }


    private void InsertUpdate(String user,String selected){
        Query city = database.getReference().child("preference").orderByChild("user").startAt(user).endAt(user + "\uf8ff");

        city.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> cities = new ArrayList<String>();
                int i=0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    cities.add(postSnapshot.getKey());
                    i=i+1;
                }
                if(i!=0){
                    UpdatePreference(selected,cities.get(0));
                }else{
                    InsertPreference(selected);
                }
                Toast.makeText(preference.this, "Votre choix a été enregistré", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void preferenceDefault(){
        if(choix.compareTo(list_sport[0])!=0 && choix.compareTo("")!=0){
            int i =0;

            while(i<list_sport.length){

                if(choix.compareTo(list_sport[i])==0){
                    String first = list_sport[0];
                    String pref  = list_sport[i];
                    list_sport[0] = pref;
                    list_sport[i] = first;
                    break;

                }
                i=i+1;
            }
        }
    }
}