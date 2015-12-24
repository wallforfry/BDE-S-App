package fr.wallforfry.bdesapp.ViewHolder;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.wallforfry.bdesapp.Fragments.NewsFragment;
import fr.wallforfry.bdesapp.MainActivity;
import fr.wallforfry.bdesapp.Object.CardMediumRightObject;
import fr.wallforfry.bdesapp.R;

/**
 * Created by wallerand on 16/11/2015.
 */
public class CardMediumRightViewHolder extends RecyclerView.ViewHolder{

    private TextView title;
    private TextView subtitle;
    private ImageView imageView;
    private TextView ouvrir;
    private TextView partager;

    private String valueTitle;
    private String valueSubtitle;
    private String actionOuvrir;
    private String actionPartager;

    private View view;


    //itemView est la vue correspondante à 1 cellule
    public CardMediumRightViewHolder(final View itemView) {
        super(itemView);

        view = itemView;
        //c'est ici que l'on fait nos findView

        title = (TextView) itemView.findViewById(R.id.title);
        subtitle = (TextView) itemView.findViewById(R.id.subtitle);
        imageView = (ImageView) itemView.findViewById(R.id.image);
        ouvrir = (TextView) itemView.findViewById(R.id.ouvrir);
        partager = (TextView) itemView.findViewById(R.id.partager);


        ouvrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = actionOuvrir;
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    v.getContext().startActivity(i);
                }
                catch (android.content.ActivityNotFoundException anfe) {
                    NewsFragment.makeSnack("Oups, pas de lien disponible..");
                }

            }
        });

        partager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareTextUrl(valueTitle, valueSubtitle, actionOuvrir);

            }
        });



    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(CardMediumRightObject myObject){
        title.setText(myObject.getTitle());
        valueTitle = myObject.getTitle();
        subtitle.setText(myObject.getSubtitle());
        valueSubtitle = myObject.getSubtitle();

       // if(myObject.getActionOuvrir() != "") {
            actionOuvrir = myObject.getActionOuvrir();
        /*}
        else{
            ouvrir.setVisibility(View.INVISIBLE);
        }*/
        actionPartager = myObject.getActionPartager();
        Picasso.with(imageView.getContext()).load(myObject.getImageUrl()).centerCrop().fit().into(imageView);
        //imageView.setImageResource(myObject.getImageUrl());
    }

    private void shareTextUrl(String subject, String text, String lien) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, subject);
        share.putExtra(Intent.EXTRA_TEXT, "Regardes ce que j'ai trouvé, un nouveau post de Benjamin Butown : \n\n" + subject + "\n" + text + "\n\nRetrouve le post complet sur : " + lien);

        view.getContext().startActivity(Intent.createChooser(share, "Partager le post !"));
    }

}