package fr.wallforfry.bdesapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import fr.wallforfry.bdesapp.Object.AnnalesObject;
import fr.wallforfry.bdesapp.Object.CardBigPictureObject;
import fr.wallforfry.bdesapp.Object.CardGameObject;
import fr.wallforfry.bdesapp.Object.CardPictureOnlyObject;
import fr.wallforfry.bdesapp.R;
import fr.wallforfry.bdesapp.ViewHolder.AnnalesViewHolder;
import fr.wallforfry.bdesapp.ViewHolder.CardBigPictureViewHolder;
import fr.wallforfry.bdesapp.ViewHolder.CardGameViewHolder;
import fr.wallforfry.bdesapp.ViewHolder.CardPictureOnlyViewHolder;

/**
 * Created by wallerand on 15/11/2015.
 */
public class NewsViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // The items to display in your RecyclerView
    private List<Object> items;

    private final int USER = 0, IMAGE = 1, BIG_PICTURE = 2;


    // Provide a suitable constructor (depends on the kind of dataset)
    public NewsViewAdapter(List<Object> items) {
        this.items = items;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    //Returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof CardGameObject) {
            return USER;
        } else if (items.get(position) instanceof CardPictureOnlyObject) {
            return IMAGE;
        } else if (items.get(position) instanceof CardBigPictureObject){
            return BIG_PICTURE;
        }
        return -1;
    }

    /**
     * This method creates different RecyclerView.ViewHolder objects based on the item view type.\
     *
     * @param viewGroup ViewGroup container for the item
     * @param viewType type of view to be inflated
     * @return viewHolder to be inflated
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case USER:
                View v1 = inflater.inflate(R.layout.cell_cards, viewGroup, false);
                viewHolder = new CardGameViewHolder(v1);
                break;
            case IMAGE:
                View v2 = inflater.inflate(R.layout.cell_picture_only, viewGroup, false);
                viewHolder = new CardPictureOnlyViewHolder(v2);
                break;
            case BIG_PICTURE:
                View v3 = inflater.inflate(R.layout.cell_big_picture, viewGroup, false);
                viewHolder = new CardBigPictureViewHolder(v3);
                break;
                /*
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                viewHolder = new RecyclerViewSimpleTextViewHolder(v);
                break;*/
        }
        return viewHolder;
    }

    /**
     * This method internally calls onBindViewHolder(ViewHolder, int) to update the
     * RecyclerView.ViewHolder contents with the item at the given position
     * and also sets up some private fields to be used by RecyclerView.
     *
     * @param viewHolder The type of RecyclerView.ViewHolder to populate
     * @param position Item position in the viewgroup.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case USER:
                CardGameViewHolder vh1 = (CardGameViewHolder) viewHolder;
                configureCardGameViewHolder(vh1, position);
                break;
            case IMAGE:
                CardPictureOnlyViewHolder vh2 = (CardPictureOnlyViewHolder) viewHolder;
                configurePictureOnlyViewHolder(vh2, position);
                break;
            case BIG_PICTURE:
                CardBigPictureViewHolder vh3 = (CardBigPictureViewHolder) viewHolder;
                configureCardBigPictureViewHolder(vh3, position);
                break;/*
            default:
                RecyclerViewSimpleTextViewHolder vh = (RecyclerViewSimpleTextViewHolder) viewHolder;
                configureDefaultViewHolder(vh, position);
                break;*/
        }
    }

   /* private void configureDefaultViewHolder(RecyclerViewSimpleTextViewHolder vh, int position) {
        vh.getLabel().setText((CharSequence) items.get(position));
    }*/

    private void configureCardGameViewHolder(CardGameViewHolder vh1, int position) {
        CardGameObject myObject = (CardGameObject) items.get(position);
        /*if (user != null) {
            vh1.getLabel1().setText("Name: " + user.name);
            vh1.getLabel2().setText("Hometown: " + user.hometown);
        }*/
        vh1.bind(myObject);
    }

    private void configurePictureOnlyViewHolder(CardPictureOnlyViewHolder vh2, int position) {
        CardPictureOnlyObject myObject = (CardPictureOnlyObject) items.get(position);
        vh2.bind(myObject);
    }

    private void configureCardBigPictureViewHolder(CardBigPictureViewHolder vh3, int position) {
        CardBigPictureObject myObject = (CardBigPictureObject) items.get(position);
        vh3.bind(myObject);
    }



    /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

}