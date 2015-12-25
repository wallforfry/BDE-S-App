package fr.wallforfry.bdesapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.wallforfry.bdesapp.Adapter.CardGameAdapter;
import fr.wallforfry.bdesapp.Adapter.NewsViewAdapter;
import fr.wallforfry.bdesapp.BDD.BddConnect;
import fr.wallforfry.bdesapp.BDD.DBHelper;
import fr.wallforfry.bdesapp.Object.CardBigPictureObject;
import fr.wallforfry.bdesapp.Object.CardGameObject;
import fr.wallforfry.bdesapp.Object.CardMediumRightObject;
import fr.wallforfry.bdesapp.Object.CardPictureOnlyObject;
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
    private View rootView;

    private RecyclerView recyclerView;
    private List<CardGameObject> gameList = new ArrayList<>();
    private List<CardGameObject> maj = new ArrayList<>();
    private SwipeRefreshLayout swipeLayout;
    private CardGameAdapter adapter = null;
    private int id_To_Update = 0;
    public static DBHelper dbShare = null;

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
        rootView = inflater.inflate(R.layout.fragment_jeux, container, false);

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

        DBHelper mydb = new DBHelper(getActivity());
        dbShare = mydb;
        /*mydb.insertGames(0, 0, "Bienvenue", "com.tulipe.android.taupe", "http://www.g2j.fr/images/M_images/eiffel-tower-paris-2.jpg", "21/01/2015");
        for(int i=1; i< 10; i++) {
                mydb.insertGames(i, 10, "locale", "test", "test", "test");
        }
       /* if(mydb.gameExist(0) == false ) {
            mydb.insertGames(0, 0, "Tape Taupe", "com.tulipe.android.taupe", "http://www.g2j.fr/images/M_images/eiffel-tower-paris-2.jpg", "29/11/2015");
        }
        else{
            mydb.updateGames(0, 0, "Tape Taupe", "com.tulipe.android.taupe", "http://www.g2j.fr/images/M_images/eiffel-tower-paris-2.jpg", "29/11/2015");
        }*/

        //recycler view
      //  ajouterJeux();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewGame);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
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
        //recyclerView.setAdapter(new CardGameAdapter(gameList));

        adapter = new CardGameAdapter(gameList);
        recyclerView.setAdapter(adapter);

        getLocalGame();

        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainerGame);
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

    public static String getTitle(){
        return title;
    }


    private void ajouterJeux() {
        gameList.add(new CardGameObject("Tape Taupe", "http://psp-img.gamergen.com/taupe-chrono-007_0190000000327445.png", "com.tulipe.android.taupe"));
        gameList.add(new CardGameObject("Doodle jump", "http://psp-img.gamergen.com/taupe-chrono-007_0190000000327445.png", "com.tulipe.android.doodleJump"));
       /* cities.add(new MyObject("France","http://www.telegraph.co.uk/travel/destination/article130148.ece/ALTERNATES/w620/parisguidetower.jpg", "com.umonistudio.tile"));
        cities.add(new MyObject("Angleterre","http://www.traditours.com/images/Photos%20Angleterre/ForumLondonBridge.jpg", ""));
        cities.add(new MyObject("Allemagne","http://tanned-allemagne.com/wp-content/uploads/2012/10/pano_rathaus_1280.jpg", ""));
        cities.add(new MyObject("Espagne","http://www.sejour-linguistique-lec.fr/wp-content/uploads/espagne-02.jpg", ""));
        cities.add(new MyObject("Italie","http://retouralinnocence.com/wp-content/uploads/2013/05/Hotel-en-Italie-pour-les-Vacances2.jpg", ""));
        cities.add(new MyObject("Russie","http://www.choisir-ma-destination.com/uploads/_large_russie-moscou2.jpg", ""));*/
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
                                maj = BddConnect.getGames();
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

    private void ajouterAutre() {

        BddConnect.makeSnack(rootView, "Mise à jour réussie");
        gameList = maj;

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(new CardGameAdapter(gameList));
        swipeLayout.setRefreshing(false);
    }

    private void notConnected() {

        BddConnect.makeSnack(rootView, "Mise à jour impossible");
        swipeLayout.setRefreshing(false);
    }

    private void getLocalGame(){

      /*  DBHelper mydb ;

        mydb = new DBHelper(getActivity());
*/
        //int Value = 0; //id de la recherche
        int vMax = dbShare.numberOfRowsGames();
        //vMax = vMax;
       for(int Value = 0; Value < vMax; Value++) {
       //     int Value = 0;

            Cursor rs = dbShare.getDataGames(Value);
            id_To_Update = Value;
            rs.moveToFirst();

       // while(!rs.isLast()){
       //     rs.moveToNext();

            int type = rs.getInt(rs.getColumnIndex(DBHelper.GAMES_COLUMN_TYPE));
            String title = rs.getString(rs.getColumnIndex(DBHelper.GAMES_COLUMN_TITLE));
            //String title =  dbShare.getGameSubtitle(Value);
            //String title =  "title";
            String subtitle = rs.getString(rs.getColumnIndex(DBHelper.GAMES_COLUMN_SUBTITLE));
            String picture = rs.getString(rs.getColumnIndex(DBHelper.GAMES_COLUMN_PICTURE));
            String date = rs.getString(rs.getColumnIndex(DBHelper.GAMES_COLUMN_DATE));

            CardGameObject game = new CardGameObject(title, picture, subtitle);
            gameList.add(game);
        }
    }
}