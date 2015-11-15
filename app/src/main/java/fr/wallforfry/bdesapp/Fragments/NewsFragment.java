package fr.wallforfry.bdesapp.Fragments;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.wallforfry.bdesapp.Adapter.NewsViewAdapter;
import fr.wallforfry.bdesapp.Object.AnnalesObject;
import fr.wallforfry.bdesapp.Object.CardBigPictureObject;
import fr.wallforfry.bdesapp.Object.CardGameObject;
import fr.wallforfry.bdesapp.Object.CardPictureOnlyObject;
import fr.wallforfry.bdesapp.R;

/**
 * Created by wallerand on 11/11/2015.
 */

public class NewsFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static String title = "Les News";

    private RecyclerView recyclerView;
    private List gameList = new ArrayList<>();
    SwipeRefreshLayout swipeLayout;
    private NewsViewAdapter adapter = null;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static NewsFragment newInstance(int sectionNumber) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public NewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

      /*//permet de récupérer les préférences
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String pref = sp.getString("example_text", "YourName");
        TextView text = (TextView) rootView.findViewById(R.id.textView2);
        text.setText(pref);*/


        ajouterNews();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //puis créer un MyAdapter, lui fournir notre liste de villes.
        //cet adapter servira à remplir notre recyclerview
        adapter = new NewsViewAdapter((gameList));
        recyclerView.setAdapter(adapter);

        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        swipeLayout.setColorSchemeColors(
                Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);

    swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()

    {
        @Override
        public void onRefresh() {
            swipeLayout.setRefreshing(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ajouterAutre();
                    recyclerView.setAdapter(new NewsViewAdapter(gameList));
                    swipeLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "List Updated",
                            Toast.LENGTH_LONG).show();
                }
            }, 5000);
        }
    });
        return rootView;
    }

    public static String getTitle(){
        return title;
    }

    private void ajouterNews() {
        gameList.add(new CardGameObject("Dernier jeu", R.drawable.taupe, "com.tulipe.android.taupe"));
        gameList.add(new CardPictureOnlyObject("Picture only","Picture subtitle", R.drawable.russia));
        gameList.add(new CardPictureOnlyObject("Paris","Best world town", R.drawable.paris));
        gameList.add(new CardBigPictureObject("Rafraichir", "Explication ici", "Pour rafraichir, tirez vers le bas", R.drawable.taupe));
       // gameList.add(new AnnalesObject("Gros titre"));
        gameList.add(new CardBigPictureObject("Le titre de ma carte", "Là c'est son sous-titre", "Et ici un contenu qui va être très très très très long parce que j'ai besoin de tester l'adaptation d'un grand text", R.drawable.taupe));
    }

    private void ajouterAutre() {
        adapter.clear();
        gameList.add(new CardBigPictureObject("Soirée Gala", "Le 4 décembre", "Réservez dès à présent votre place pour le Gala Disponible au BDE", R.drawable.italie));
        gameList.add(new CardBigPictureObject("Cacahuete", "Chocolat", "Salut je suis calypso", R.drawable.russia));
        gameList.add(new CardBigPictureObject("Bonjour", "Ordinateur", "Le chien court dans le jardin comme un fou", R.drawable.paris));
        gameList.add(new CardBigPictureObject("Guitare", "Trompette", "Je suis une licorne", R.drawable.london));
        gameList.add(new CardBigPictureObject("Theatre", "Sims", "Mon frere joue de la guitare depuis 12 ans", R.drawable.taupe));
        gameList.add(new CardBigPictureObject("Le nom du chien", "28/05/1999", "Je suis nee le 28 mai 199 a 15h15 à paris à l'hopital Robert Debré danns le 19e arrondissement il faisait beau mon frere avait deux ans quand je uis nee. j'aime le chocolat et j'adore faire des gateaux au chocolat", R.drawable.paris));
        adapter.notifyDataSetChanged();
    }

}