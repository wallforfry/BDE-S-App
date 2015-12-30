package fr.wallforfry.bdesapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fr.wallforfry.bdesapp.Object.AgendaObject;
import fr.wallforfry.bdesapp.R;

/**
 * Created by wallerand on 29/12/2015.
 */
public class AgendaViewHolder extends RecyclerView.ViewHolder{

    private TextView summary;
    private TextView description;

    //itemView est la vue correspondante à 1 cellule
    public AgendaViewHolder(final View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView

        summary = (TextView) itemView.findViewById(R.id.summary);
        description = (TextView) itemView.findViewById(R.id.description);

    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(AgendaObject myObject){
        summary.setText(myObject.getSummary());
        description.setText(myObject.getDescription());
    }


}