package fr.wallforfry.bdesapp.ViewHolder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.wallforfry.bdesapp.Object.CardPictureOnlyObject;
import fr.wallforfry.bdesapp.R;

/**
 * Created by wallerand on 15/11/2015.
 */
public class CardPictureOnlyViewHolder extends RecyclerView.ViewHolder{

    private TextView title;
    private TextView subtitle;
    private ImageView imageView;

    //itemView est la vue correspondante à 1 cellule
    public CardPictureOnlyViewHolder(final View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView

        title = (TextView) itemView.findViewById(R.id.title);
        subtitle = (TextView) itemView.findViewById(R.id.subtitle);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(CardPictureOnlyObject myObject){
        title.setText(myObject.getTitle());
        subtitle.setText(myObject.getSubtitle());
        Picasso.with(imageView.getContext()).load(myObject.getImageUrl()).centerCrop().fit().into(imageView);
        //imageView.setImageResource(myObject.getImageUrl());
    }

}