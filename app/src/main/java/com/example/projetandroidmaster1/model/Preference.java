package com.example.projetandroidmaster1.model;

public class Preference {
    private String user,  choix;


    public Preference() {

    }

    public Preference(String user, String choix) {
        this.user = user;
        this.choix = choix;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getChoix() {
        return choix;
    }

    public void setChoix(String choix) {
        this.choix = choix;
    }
}
