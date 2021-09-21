package com.example.projetandroidmaster1.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.projetandroidmaster1.R;
import com.example.projetandroidmaster1.model.Preference;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class afterlogin extends AppCompatActivity {

    FirebaseDatabase database;
    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter adapter;
    String user;
    String choix="";

    ProgressBar pr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterlogin);

        pr = findViewById(R.id.progressbar);
        pr.setVisibility(View.GONE);
        tabLayout = findViewById(R.id.tab_layout);
        pager2 = findViewById(R.id.view_pager2);

        setTitle("");
        database = FirebaseDatabase.getInstance();
        Bundle extra = getIntent().getExtras();
        user = extra.getString("user");

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Foot"));
        tabLayout.addTab(tabLayout.newTab().setText("Tennis"));
        tabLayout.addTab(tabLayout.newTab().setText("Basket"));
        tabLayout.addTab(tabLayout.newTab().setText("F1"));

        tabLayout.getTabAt(0).setIcon(R.drawable.baseline_sports_soccer_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.baseline_sports_tennis_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.baseline_sports_basketball_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.baseline_sports_black_24dp);

        sport_prefere(user);



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    //menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.preference,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.search){
            Toast.makeText(this, "Recherche", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id==R.id.pref){
            Toast.makeText(this, "Préférence", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(afterlogin.this, preference.class);
            intent.putExtra("user",user);
            intent.putExtra("choix",choix);
            startActivity(intent);
            return true;
        }
        if(id==R.id.deconnexion){
            Intent intent=new Intent(afterlogin.this, login.class);
            Toast.makeText(this, "Déconnexion", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
    public void sport_prefere(String user){
        pr.setVisibility(View.VISIBLE);
        Query city = database.getReference().child("preference").orderByChild("user").startAt(user).endAt(user+ "\uf8ff");

        city.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Preference> preference = new ArrayList<Preference>();
                int i=0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    preference.add(postSnapshot.getValue(Preference.class));
                    i=i+1;
                }
                if(i!=0){
                        choix = preference.get(0).getChoix();

                        int position=-1;
                        if( choix.equals("Basketball")){
                            position=2;
                        }
                        if( choix.equals("Football")){
                            position=0;
                        }
                        if( choix.equals("Tennis")){
                            position=1;
                        }
                        if( choix.equals("Formule 1")){
                            position=3;
                        }
                        pager2.setCurrentItem(position);
                        tabLayout.selectTab(tabLayout.getTabAt(position));


                }
                pr.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }
}