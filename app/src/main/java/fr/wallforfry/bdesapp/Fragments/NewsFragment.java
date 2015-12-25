package fr.wallforfry.bdesapp.Fragments;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.wallforfry.bdesapp.Adapter.NewsViewAdapter;
import fr.wallforfry.bdesapp.BDD.DBHelper;
import fr.wallforfry.bdesapp.BDD.BddConnect;
import fr.wallforfry.bdesapp.Object.CardBigPictureObject;
import fr.wallforfry.bdesapp.Object.CardGameObject;
import fr.wallforfry.bdesapp.Object.CardMediumRightObject;
import fr.wallforfry.bdesapp.Object.CardPictureOnlyObject;
import fr.wallforfry.bdesapp.R;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

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
    private static View rootView;

    private RecyclerView recyclerView;
    private List gameList = new ArrayList<>();
    private List maj = new ArrayList<>();
    SwipeRefreshLayout swipeLayout;
    private NewsViewAdapter adapter = null;
    private int id_To_Update = 0;

    public static DBHelper dbShare = null;

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
        rootView = inflater.inflate(R.layout.fragment_news, container, false);

        initNews(); //init de la bdd pour première utilisation

      /*//permet de récupérer les préférences
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String pref = sp.getString("example_text", "YourName");
        TextView text = (TextView) rootView.findViewById(R.id.textView2);
        text.setText(pref);*/


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        // Attach the layout manager to the recycler view
        recyclerView.setLayoutManager(gridLayoutManager);

        //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //puis créer un MyAdapter, lui fournir notre liste de villes.
        //cet adapter servira à remplir notre recyclerview
        adapter = new NewsViewAdapter((gameList));
        recyclerView.setAdapter(adapter);
        //recyclerView.setItemAnimator(new SlideInUpAnimator());
        getLocalNews();

        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        swipeLayout.setColorSchemeColors(
                Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()

        {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                Refresh();
            }
        });
        return rootView;
    }

    public static String getTitle() {
        return title;
    }

    public static void makeSnack(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void initNews() {
        DBHelper mydb = new DBHelper(getActivity());
        dbShare = mydb;
        mydb.insertNews(0,0,2,"Bienvenue","Application du BDE","Pour accéder pour la première fois aux news, glissez vers le bas","http://www.g2j.fr/images/M_images/eiffel-tower-paris-2.jpg","test","test","test");
        for(int i=1; i< 10; i++) {
            mydb.insertNews(i,i, -1, "locale", "test", "test", "test", "test", "test", "test");
        }
    }

    private void ajouterAutre() {
        //adapter.clear();
        /*gameList.add(new CardBigPictureObject("Soirée Gala", "Le 4 décembre", "Réservez dès à présent votre place pour le Gala Disponible au BDE", R.drawable.italie));
        gameList.add(new CardBigPictureObject("Cacahuete", "Chocolat", "Salut je suis calypso", R.drawable.russia));
        gameList.add(new CardBigPictureObject("Bonjour", "Ordinateur", "Le chien court dans le jardin comme un fou", R.drawable.paris));
        gameList.add(new CardBigPictureObject("Guitare", "Trompette", "Je suis une licorne", R.drawable.london));
        gameList.add(new CardBigPictureObject("Theatre", "Sims", "Mon frere joue de la guitare depuis 12 ans", R.drawable.taupe));
        gameList.add(new CardBigPictureObject("Le nom du chien", "28/05/1999", "Je suis nee le 28 mai 199 a 15h15 à paris à l'hopital Robert Debré danns le 19e arrondissement il faisait beau mon frere avait deux ans quand je uis nee. j'aime le chocolat et j'adore faire des gateaux au chocolat", R.drawable.paris));
        */

            BddConnect.makeSnack(rootView, "Mise à jour réussie");
            gameList = maj;

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(new NewsViewAdapter(gameList));
        swipeLayout.setRefreshing(false);
    }

    private void notConnected() {

        BddConnect.makeSnack(rootView, "Mise à jour impossible");
        swipeLayout.setRefreshing(false);
    }

    private void Refresh(){
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            Thread task = null;
            if (task == null) {
                task = new Thread(
                        new Runnable() {
                            public void run() {
                                maj = BddConnect.getPersonnes();
                            }
                        });
                task.start();
            } else {
                task.run();
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ajouterAutre();
                }
            }, 5000);
        } else {
            notConnected();
        }
    }

    private void getLocalNews(){

        for(int Value = 0; Value <10 ; Value++) {
            Cursor rs = dbShare.getData(Value);
            id_To_Update = Value;
            rs.moveToFirst();


            int type = rs.getInt(rs.getColumnIndex(DBHelper.NEWS_COLUMN_TYPE));
            String title = rs.getString(rs.getColumnIndex(DBHelper.NEWS_COLUMN_TITLE));
            String subtitle = rs.getString(rs.getColumnIndex(DBHelper.NEWS_COLUMN_SUBTITLE));
            String content = rs.getString(rs.getColumnIndex(DBHelper.NEWS_COLUMN_CONTENT));
            String picture = rs.getString(rs.getColumnIndex(DBHelper.NEWS_COLUMN_PICTURE));
            String action1 = rs.getString(rs.getColumnIndex(DBHelper.NEWS_COLUMN_ACTION1));
            String action2 = rs.getString(rs.getColumnIndex(DBHelper.NEWS_COLUMN_ACTION2));
            String date = rs.getString(rs.getColumnIndex(DBHelper.NEWS_COLUMN_DATE));

            switch (type) {
                case 0:
                    CardGameObject game = new CardGameObject(title, picture, subtitle);
                    gameList.add(game);
                    break;
                case 1:
                    CardPictureOnlyObject pictureOnly = new CardPictureOnlyObject(title, subtitle, picture);
                    gameList.add(pictureOnly);
                    break;
                case 2:
                    CardBigPictureObject bigPicture = new CardBigPictureObject(title, subtitle, content, picture, action1, action2);
                    gameList.add(bigPicture);
                    break;
                case 3:
                    CardMediumRightObject mediumRight = new CardMediumRightObject(title, subtitle, picture, action1, action2);
                    gameList.add(mediumRight);
                    break;
            }
        }
    }
}