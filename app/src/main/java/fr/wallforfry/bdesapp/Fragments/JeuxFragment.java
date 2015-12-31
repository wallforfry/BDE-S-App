package fr.wallforfry.bdesapp.Fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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

import fr.wallforfry.bdesapp.Adapter.CardGameAdapter;
import fr.wallforfry.bdesapp.AsyncTask.GetGames;
import fr.wallforfry.bdesapp.BDD.DBHelper;
import fr.wallforfry.bdesapp.MainActivity;
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
    public static View rootView;
    private static Context context;

    private RecyclerView recyclerView;
    public static List<CardGameObject> gameList = new ArrayList<>();
    public static SwipeRefreshLayout swipeLayout;
    public static CardGameAdapter adapter = null;
    private static int id_To_Update = 0;
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
        context = getActivity();

        DBHelper mydb = new DBHelper(getActivity());
        dbShare = mydb;

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewGame);

        getLocalGame();

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        // Attach the layout manager to the recycler view
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new CardGameAdapter(gameList);
        recyclerView.setAdapter(adapter);

        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainerGame);
        swipeLayout.setColorSchemeColors(
                Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                gamesRefresh();
            }
        });

        return rootView;
    }

    public static String getTitle(){
        return title;
    }

    public static void notConnected() {
        Snackbar.make(rootView, "Mise à jour impossible", Snackbar.LENGTH_LONG)
                .setAction("Réessayer", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gamesRefresh();
                    }
                }).show();
        swipeLayout.setRefreshing(false);
    }

    public static void getLocalGame(){
        gameList.clear();
        int vMax = dbShare.numberOfRowsGames();
        for(int Value = 0; Value < vMax; Value++) {

            Cursor rs = dbShare.getDataGames(Value);
            id_To_Update = Value;
            rs.moveToFirst();

            int type = rs.getInt(rs.getColumnIndex(DBHelper.GAMES_COLUMN_TYPE));
            String title = rs.getString(rs.getColumnIndex(DBHelper.GAMES_COLUMN_TITLE));
            String subtitle = rs.getString(rs.getColumnIndex(DBHelper.GAMES_COLUMN_SUBTITLE));
            String picture = rs.getString(rs.getColumnIndex(DBHelper.GAMES_COLUMN_PICTURE));
            String date = rs.getString(rs.getColumnIndex(DBHelper.GAMES_COLUMN_DATE));

            CardGameObject game = new CardGameObject(title, picture, subtitle);
            gameList.add(game);
        }
    }

    private static void gamesRefresh(){
        if (MainActivity.isConnected(context)) {
            GetGames games = new GetGames();
            games.execute();
        } else {
            notConnected();
        }
    }
}