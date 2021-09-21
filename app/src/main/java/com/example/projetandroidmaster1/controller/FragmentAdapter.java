package com.example.projetandroidmaster1.controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projetandroidmaster1.vue.football;
import com.example.projetandroidmaster1.vue.f1;
import com.example.projetandroidmaster1.vue.basketball;
import com.example.projetandroidmaster1.vue.tennis;

public class FragmentAdapter extends FragmentStateAdapter{
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 1 :
                return new tennis();
            case 2 :
                return new basketball();
            case 3 :
                return new f1();

        }

        return new football();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
