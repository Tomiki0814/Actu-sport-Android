package com.example.projetandroidmaster1.controller;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projetandroidmaster1.R;
import com.example.projetandroidmaster1.model.Sport;
import com.example.projetandroidmaster1.vue.detail;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myadapter extends FirebaseRecyclerAdapter<com.example.projetandroidmaster1.model.Sport,myadapter.AccueilViewHolder> {
    public myadapter(@NonNull FirebaseRecyclerOptions<com.example.projetandroidmaster1.model.Sport> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AccueilViewHolder holder, int position, @NonNull Sport model) {
        holder.nom.setText(model.getTitre());
        Glide.with(holder.image.getContext()).load(model.getImage()).into(holder.image);
        holder.img=model.getImage();
        holder.url=model.getUrl();
        holder.description=model.getDescription();
    }


    @NonNull
    @Override
    public AccueilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return new AccueilViewHolder(view);
    }


    public static class AccueilViewHolder extends RecyclerView.ViewHolder {
        TextView nom;
        ImageView image;
        RelativeLayout r;
        String img;
        String url;
        String description;



        public AccueilViewHolder(@NonNull View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.nametext);
            image = itemView.findViewById(R.id.img1);


            r = itemView.findViewById(R.id.sport);
            r.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(itemView.getContext(), detail.class);
                    i.putExtra("sport",nom.getText());
                    i.putExtra("image",img);
                    i.putExtra("url",url);
                    i.putExtra("description",description);
                    itemView.getContext().startActivity(i);
                }

            });

        }
    }
}


