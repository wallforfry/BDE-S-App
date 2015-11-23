package fr.wallforfry.bdesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_help);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"wallerand.delevacq@edu.esiee.fr"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Contact Application BDE");
            //  emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, « Corps du message »);
                startActivity(Intent.createChooser(emailIntent, " Envoi email… "));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
