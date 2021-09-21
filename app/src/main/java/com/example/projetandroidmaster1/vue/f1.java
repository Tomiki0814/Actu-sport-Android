package com.example.projetandroidmaster1.vue;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.projetandroidmaster1.R;
import com.example.projetandroidmaster1.model.Sport;
import com.example.projetandroidmaster1.controller.myadapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class f1 extends Fragment {

    private View view;
    private RecyclerView liste;
    private DatabaseReference ref;
    private myadapter adapter;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_f1,container,false);
        liste = view.findViewById(R.id.recview4);
        liste.setLayoutManager(new LinearLayoutManager(getContext()));

        ref = FirebaseDatabase.getInstance().getReference().child("F1");


        return view;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem item=menu.findItem(R.id.search);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

    }
    private void processsearch(String s) {
        FirebaseRecyclerOptions<Sport> options =
                new FirebaseRecyclerOptions.Builder<Sport>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("F1").orderByChild("titre").startAt(s).endAt(s + "\uf8ff"), Sport.class)
                        .build();

        adapter=new myadapter(options);
        liste.setAdapter(adapter);
        adapter.startListening();
    }



    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Sport> options =
                new FirebaseRecyclerOptions.Builder<Sport>()
                        .setQuery(ref, Sport.class)
                        .build();


        adapter=new myadapter(options);
        liste.setAdapter(adapter);
        adapter.startListening();



    }

    @Override
    public void onStop() {
        super.onStop();


    }

}