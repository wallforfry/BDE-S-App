package fr.wallforfry.bdesapp.ViewHolder;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.wallforfry.bdesapp.Object.CardGameObject;
import fr.wallforfry.bdesapp.R;

/**
 * Created by wallerand on 14/11/2015.
 */
public class CardGameViewHolder extends RecyclerView.ViewHolder{

    private TextView textViewView;
    private ImageView imageView;
    private String gameName = null;

    //itemView est la vue correspondante à 1 cellule
    public CardGameViewHolder(final View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView

        textViewView = (TextView) itemView.findViewById(R.id.text);
        imageView = (ImageView) itemView.findViewById(R.id.image);
        FloatingActionButton fab = (FloatingActionButton) itemView.findViewById(R.id.game_button);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i;
                try {
                    i = v.getContext().getPackageManager().getLaunchIntentForPackage(gameName);
                    if (i == null)
                        throw new PackageManager.NameNotFoundException();
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    v.getContext().startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    // affiche que l'appli n'es pas présente sur le téléphone et ouvre le play store sur le package
                    playStore(gameName, itemView);
                }

            }
        });

    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(CardGameObject myObject){
        textViewView.setText(myObject.getText());
        gameName = myObject.getPkgName();
        Picasso.with(imageView.getContext()).load(myObject.getImageUrl()).centerCrop().fit().into(imageView);
        //imageView.setImageResource(myObject.getImageUrl());
    }

    public void playStore(String appPackageName, View v) {      //ouvre le market avec le package cible

        try {
            v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}