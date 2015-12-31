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

import fr.wallforfry.bdesapp.Adapter.NewsViewAdapter;
import fr.wallforfry.bdesapp.AsyncTask.GetNews;
import fr.wallforfry.bdesapp.BDD.DBHelper;
import fr.wallforfry.bdesapp.MainActivity;
import fr.wallforfry.bdesapp.Object.CardBigPictureObject;
import fr.wallforfry.bdesapp.Object.CardGameObject;
import fr.wallforfry.bdesapp.Object.CardMediumRightObject;
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
    public static View rootView;
    private static Context context;

    private RecyclerView recyclerView;
    public static List gameList = new ArrayList<>();
    public static List maj = new ArrayList<>();
    public static SwipeRefreshLayout swipeLayout;
    public static NewsViewAdapter adapter = null;
    private static int id_To_Update = 0;

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
        context = getActivity();

        initNews(); //init de la bdd pour première utilisation
        getLocalNews();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        // Attach the layout manager to the recycler view
        recyclerView.setLayoutManager(layoutManager);


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
                newsRefresh();
            }
        });
        return rootView;
    }

    public static String getTitle() {
        return title;
    }

    private void initNews() {
        DBHelper mydb = new DBHelper(getActivity());
        dbShare = mydb;
        /*mydb.insertNews(0, 0, 2, "Bienvenue", "Application du BDE", "Pour accéder pour la première fois aux news, glissez vers le bas", "http://www.g2j.fr/images/M_images/eiffel-tower-paris-2.jpg", "test", "test", "test");
        for(int i=1; i< 10; i++) {
            mydb.insertNews(i,i, -1, "locale", "test", "test", "test", "test", "test", "test");
        }*/
    }

    public static void notConnected() {
        Snackbar.make(rootView, "Mise à jour impossible", Snackbar.LENGTH_LONG)
                .setAction("Réessayer", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newsRefresh();
                    }
                }).show();
        swipeLayout.setRefreshing(false);
    }


    public static void getLocalNews(){
        gameList.clear();
        int vMax = dbShare.numberOfRowsNews();
        if(vMax >10){
            vMax=10;
        }
        for(int Value = 0; Value <vMax ; Value++) {
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

    private static void newsRefresh(){
        if (MainActivity.isConnected(context)) {
            GetNews news = new GetNews();
            news.execute();
        } else {
            notConnected();
        }
    }
}