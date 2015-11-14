package fr.wallforfry.bdesapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fr.wallforfry.bdesapp.Object.AnnalesObject;
import fr.wallforfry.bdesapp.R;

/**
 * Created by wallerand on 14/11/2015.
 */
public class AnnalesViewHolder extends RecyclerView.ViewHolder{

    private TextView textViewView;

    //itemView est la vue correspondante à 1 cellule
    public AnnalesViewHolder(final View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView

        textViewView = (TextView) itemView.findViewById(R.id.text);

    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(AnnalesObject myObject){
        textViewView.setText(myObject.getText());
    }


}