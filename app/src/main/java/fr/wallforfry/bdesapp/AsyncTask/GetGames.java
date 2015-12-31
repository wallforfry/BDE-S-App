package fr.wallforfry.bdesapp.AsyncTask;

import android.database.Cursor;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import fr.wallforfry.bdesapp.BDD.DBHelper;
import fr.wallforfry.bdesapp.Fragments.JeuxFragment;
import fr.wallforfry.bdesapp.InputStreamOperations;
import fr.wallforfry.bdesapp.MainActivity;

/**
 * Created by wallerand on 30/12/2015.
 */
public class GetGames extends AsyncTask<Void, Integer, Void> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        JeuxFragment.swipeLayout.setRefreshing(true);
        work = null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        getGames();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if(work == null) {
            MainActivity.makeSnack(JeuxFragment.rootView, "Mise à jour réussie");
        }
        else{
            JeuxFragment.notConnected();
        }
        JeuxFragment.getLocalGame();
        //NewsFragment.gameList = NewsFragment.maj;
        JeuxFragment.adapter.notifyDataSetChanged();
        JeuxFragment.swipeLayout.setRefreshing(false);
    }

    private static int arrayLength = 0;
    private static Exception work;

    public static void getGames() {

        List maj = new ArrayList<>();

        try {

            DBHelper mydb = JeuxFragment.dbShare; //mydb = à la base de donnée locale

            String myurl = "http://api.wallforfry.fr/getGames";

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
            JSONArray array = new JSONArray(jsonObject.getString("games"));

            arrayLength = array.length();
            mydb.dropGames();

            // Pour tous les objets on récupère les infos
            for (int i = 0; i < array.length(); i++) {
                // On récupère un objet JSON du tableau
                JSONObject obj = new JSONObject(array.getString(i));
                int id = i;
                //ajout en local
                //  if(JeuxFragment.dbShare.numberOfRowsGames() <= i) {
                String subtitle = obj.getString("subtitle");
                int lastId = 0;
                Cursor cursor = mydb.getDataGames(id);
                if(cursor.getCount() != 0){
                    lastId = mydb.getGameId(cursor, "iddb");
                }
                else{
                    lastId = 666;
                }
                mydb.insertGames(i, obj.getInt("id"), obj.getInt("type"), obj.getString("title"), obj.getString("subtitle"), obj.getString("picture"), obj.getString("date"));

                /*
                CardGameObject game = new CardGameObject(obj.getString("title"), obj.getString("picture"), obj.getString("subtitle"));
                maj.add(game);
                */
                // On ajoute la personne à la liste
            }
        } catch (Exception e) {
            work = e;
            e.printStackTrace();
        }
    }
}
