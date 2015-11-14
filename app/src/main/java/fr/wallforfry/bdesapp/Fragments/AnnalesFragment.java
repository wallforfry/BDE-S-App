package fr.wallforfry.bdesapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.wallforfry.bdesapp.Adapter.AnnalesAdapter;
import fr.wallforfry.bdesapp.Object.AnnalesObject;
import fr.wallforfry.bdesapp.R;

/**
 * Created by wallerand on 11/11/2015.
 */

public class AnnalesFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static String title = "Annales";

    private RecyclerView recyclerView;
    private List<AnnalesObject> annalesList = new ArrayList<>();
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AnnalesFragment newInstance(int sectionNumber) {
        AnnalesFragment fragment = new AnnalesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public AnnalesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_annales, container, false);

        ajouterAnnales();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.annalesRecyclerView);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
       // recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        recyclerView.setAdapter(new AnnalesAdapter(annalesList));

        return rootView;
    }

    public static String getTitle(){
        return title;
    }

    private void ajouterAnnales() {
        annalesList.add(new AnnalesObject("E1"));
        annalesList.add(new AnnalesObject("E2"));
        annalesList.add(new AnnalesObject("E3"));
        annalesList.add(new AnnalesObject("E4"));
        annalesList.add(new AnnalesObject("E5"));


    }
}