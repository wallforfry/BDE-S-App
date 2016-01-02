package fr.wallforfry.bdesapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import fr.wallforfry.bdesapp.AsyncTask.GetButown;
import fr.wallforfry.bdesapp.AsyncTask.GetGames;
import fr.wallforfry.bdesapp.AsyncTask.GetNews;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), StartScreen.class);
                startActivity(i);
            }
        }, 2000);
    }
}
