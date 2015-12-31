package fr.wallforfry.bdesapp.AsyncTask;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import fr.wallforfry.bdesapp.BDD.DBHelper;
import fr.wallforfry.bdesapp.Fragments.NewsFragment;
import fr.wallforfry.bdesapp.InputStreamOperations;
import fr.wallforfry.bdesapp.MainActivity;

/**
 * Created by wallerand on 30/12/2015.
 */
public class GetNews extends AsyncTask<Void, Integer, Void> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        NewsFragment.swipeLayout.setRefreshing(true);
        work = null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        getPersonnes();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if(work == null) {
            MainActivity.makeSnack(NewsFragment.rootView, "Mise à jour réussie");
        }
        else{
            NewsFragment.notConnected();
        }
        NewsFragment.getLocalNews();
        //NewsFragment.gameList = maj;
        NewsFragment.adapter.notifyDataSetChanged();
        NewsFragment.swipeLayout.setRefreshing(false);
    }

    private static int arrayLength = 0;
    private static Exception work;

    public static void getPersonnes() {
        List maj = new ArrayList<>();

        try {

            DBHelper mydb = NewsFragment.dbShare; //mydb = à la base de donnée locale

            String myurl = "http://api.wallforfry.fr/getNews";

            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();

            /*
             * InputStreamOperations est une classe complémentaire:
             * Elle contient une méthode InputStreamToString.
             */
            String result = InputStreamOperations.InputStreamToString(inputStream);

            // On récupère le JSON complet
            JSONObject jsonObject = new JSONObject(result);
            // On récupère le tableau d'objets qui nous concernent
            JSONArray array = new JSONArray(jsonObject.getString("news"));
            // Pour tous les objets on récupère les infos

            mydb.dropNews();

            for (int i = 0; i < array.length(); i++) {
                // On récupère un objet JSON du tableau
                JSONObject obj = new JSONObject(array.getString(i));


                //ajout en local
                mydb.insertNews(i, obj.getInt("id"), obj.getInt("type"), obj.getString("title"), obj.getString("subtitle"), obj.getString("content"), obj.getString("picture"), obj.getString("action1"), obj.getString("action2"), obj.getString("date"));

/*
                switch (obj.getInt("type")) {
                    case 0:
                        CardGameObject game = new CardGameObject(obj.getString("title"), obj.getString("picture"), obj.getString("subtitle"));
                        maj.add(game);
                        break;
                    case 1:
                        CardPictureOnlyObject pictureOnly = new CardPictureOnlyObject(obj.getString("title"), obj.getString("subtitle"), obj.getString("picture"));
                        maj.add(pictureOnly);
                        break;
                    case 2:
                        CardBigPictureObject bigPicture = new CardBigPictureObject(obj.getString("title"), obj.getString("subtitle"), obj.getString("content"), obj.getString("picture"), obj.getString("action1"), obj.getString("action2"));
                        maj.add(bigPicture);
                        break;
                    case 3:
                        CardMediumRightObject mediumRight = new CardMediumRightObject(obj.getString("title"), obj.getString("subtitle"), obj.getString("picture"), obj.getString("action1"), obj.getString("action2"));
                        maj.add(mediumRight);
                        break;
                }*/
                // On ajoute la personne à la liste
            }
            //JSONObject obj = new JSONObject(array.getString(1));
        } catch (Exception e) {
            work = e;
            e.printStackTrace();
        }

        // On retourne la liste des personnes
    }

}
