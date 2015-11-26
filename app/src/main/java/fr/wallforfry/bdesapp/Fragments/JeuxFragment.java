package fr.wallforfry.bdesapp.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.wallforfry.bdesapp.Adapter.CardGameAdapter;
import fr.wallforfry.bdesapp.Object.CardGameObject;
import fr.wallforfry.bdesapp.R;

/**
 * Created by wallerand on 11/11/2015.
 */

public class JeuxFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static String title = "Jeux";

    private RecyclerView recyclerView;
    private List<CardGameObject> gameList = new ArrayList<>();

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static JeuxFragment newInstance(int sectionNumber) {
        JeuxFragment fragment = new JeuxFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public JeuxFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_jeux, container, false);

       /* FloatingActionButton fab_game_1 = (FloatingActionButton) rootView.findViewById(R.id.game_button);
        fab_game_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                try {
                    i = getActivity().getPackageManager().getLaunchIntentForPackage(getString(R.string.game_1_package));
                    if (i == null)
                        throw new PackageManager.NameNotFoundException();
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    // affiche que l'appli n'es pas présente sur le téléphone et ouvre le play store sur le package
                    playStore(getString(R.string.game_1_package));
                }
            }
        });*/

        //recycler view
        //remplir la ville
        ajouterJeux();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //puis créer un MyAdapter, lui fournir notre liste de villes.
        //cet adapter servira à remplir notre recyclerview
        recyclerView.setAdapter(new CardGameAdapter(gameList));

        return rootView;
    }

    public static String getTitle(){
        return title;
    }

    public void playStore(String appPackageName) {      //ouvre le market avec le package cible

        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private void ajouterJeux() {
        gameList.add(new CardGameObject("Tape Taupe", "http://psp-img.gamergen.com/taupe-chrono-007_0190000000327445.png", "com.tulipe.android.taupe"));
       /* cities.add(new MyObject("France","http://www.telegraph.co.uk/travel/destination/article130148.ece/ALTERNATES/w620/parisguidetower.jpg", "com.umonistudio.tile"));
        cities.add(new MyObject("Angleterre","http://www.traditours.com/images/Photos%20Angleterre/ForumLondonBridge.jpg", ""));
        cities.add(new MyObject("Allemagne","http://tanned-allemagne.com/wp-content/uploads/2012/10/pano_rathaus_1280.jpg", ""));
        cities.add(new MyObject("Espagne","http://www.sejour-linguistique-lec.fr/wp-content/uploads/espagne-02.jpg", ""));
        cities.add(new MyObject("Italie","http://retouralinnocence.com/wp-content/uploads/2013/05/Hotel-en-Italie-pour-les-Vacances2.jpg", ""));
        cities.add(new MyObject("Russie","http://www.choisir-ma-destination.com/uploads/_large_russie-moscou2.jpg", ""));*/
    }


}