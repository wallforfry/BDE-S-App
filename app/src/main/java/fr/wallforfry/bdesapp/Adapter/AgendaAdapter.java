package fr.wallforfry.bdesapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.wallforfry.bdesapp.Object.AgendaObject;
import fr.wallforfry.bdesapp.R;
import fr.wallforfry.bdesapp.ViewHolder.AgendaViewHolder;

/**
 * Created by wallerand on 29/12/2015.
 */

public class AgendaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // The items to display in your RecyclerView
    private List<Object> items;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AgendaAdapter(List<Object> items) {
        this.items = items;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View v1 = inflater.inflate(R.layout.cell_agenda_object, viewGroup, false);
        viewHolder = new AgendaViewHolder(v1);

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

        AgendaViewHolder vh4 = (AgendaViewHolder) viewHolder;
        configureAgendaViewHolder(vh4, position);

    }

    private void configureAgendaViewHolder(AgendaViewHolder vh4, int position) {
        AgendaObject myObject = (AgendaObject) items.get(position);
        vh4.bind(myObject);
    }

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
