package fr.wallforfry.bdesapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.wallforfry.bdesapp.Object.AnnalesObject;
import fr.wallforfry.bdesapp.ViewHolder.AnnalesViewHolder;
import fr.wallforfry.bdesapp.R;

/**
 * Created by wallerand on 14/11/2015.
 */
public class AnnalesAdapter extends RecyclerView.Adapter<AnnalesViewHolder> {

    List<AnnalesObject> list;

    //ajouter un constructeur prenant en entrée une liste
    public AnnalesAdapter(List<AnnalesObject> list) {
        this.list = list;
    }

    //cette fonction permet de créer les viewHolder
//et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public AnnalesViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_annales,viewGroup,false);
        return new AnnalesViewHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(AnnalesViewHolder myViewHolder, int position) {
        AnnalesObject myObject = list.get(position);
        myViewHolder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

