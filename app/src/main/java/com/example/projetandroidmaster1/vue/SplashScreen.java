package com.example.projetandroidmaster1.vue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.projetandroidmaster1.R;
import com.example.projetandroidmaster1.controller.NotificationApp;
import com.example.projetandroidmaster1.controller.login;

public class SplashScreen extends AppCompatActivity {
    private NotificationManagerCompat notificationManagerCompat;
    private final int timeout = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setTitle("");
        this.notificationManagerCompat = NotificationManagerCompat.from(this);
        //redirection vers main apres 3 seconde
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //AFFICHER NOTIF
               addNotification();
                //demarrer une page
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        }, timeout);

    }

    private void addNotification() {
        String title = "ACTU SPORT !!!";
        String message = "Content de vous revoir.";

        Notification notification = new NotificationCompat.Builder(this, NotificationApp.CHANNEL_1_ID)
                .setSmallIcon(R.mipmap.logo)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        int notificationId = 1;
        this.notificationManagerCompat.notify(notificationId, notification);
    }


}