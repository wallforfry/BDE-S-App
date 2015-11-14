package fr.wallforfry.bdesapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.wallforfry.bdesapp.Object.CardGameObject;
import fr.wallforfry.bdesapp.ViewHolder.CardGameViewHolder;
import fr.wallforfry.bdesapp.R;

/**
* Created by wallerand on 14/11/2015.
*/
public class CardGameAdapter extends RecyclerView.Adapter<CardGameViewHolder> {

List<CardGameObject> list;

//ajouter un constructeur prenant en entrée une liste
public CardGameAdapter(List<CardGameObject> list) {
    this.list = list;
}

//cette fonction permet de créer les viewHolder
//et par la même indiquer la vue à inflater (à partir des layout xml)
@Override
public CardGameViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_cards,viewGroup,false);
    return new CardGameViewHolder(view);
}

//c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
@Override
public void onBindViewHolder(CardGameViewHolder myViewHolder, int position) {
    CardGameObject myObject = list.get(position);
    myViewHolder.bind(myObject);
}

@Override
public int getItemCount() {
    return list.size();
}

}

