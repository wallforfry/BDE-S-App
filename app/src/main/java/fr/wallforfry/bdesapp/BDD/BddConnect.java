package fr.wallforfry.bdesapp.BDD;


import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import fr.wallforfry.bdesapp.BDD.DBHelper;
import fr.wallforfry.bdesapp.Fragments.JeuxFragment;
import fr.wallforfry.bdesapp.Fragments.NewsFragment;
import fr.wallforfry.bdesapp.InputStreamOperations;
import fr.wallforfry.bdesapp.Object.CardBigPictureObject;
import fr.wallforfry.bdesapp.Object.CardGameObject;
import fr.wallforfry.bdesapp.Object.CardMediumRightObject;
import fr.wallforfry.bdesapp.Object.CardPictureOnlyObject;

/**
 * Created by wallerand on 19/11/2015.
 */
public class BddConnect {

    private static int arrayLength = 0;

    public static List getPersonnes() {

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
            for (int i = 0; i < array.length(); i++) {
                // On récupère un objet JSON du tableau
                JSONObject obj = new JSONObject(array.getString(i));


                //ajout en local
                mydb.updateNews(i, obj.getInt("id"), obj.getInt("type"),obj.getString("title"),obj.getString("subtitle"),obj.getString("content"),obj.getString("picture"),obj.getString("action1"),obj.getString("action2"),obj.getString("date"));


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
                }
                // On ajoute la personne à la liste
            }
            //JSONObject obj = new JSONObject(array.getString(1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // On retourne la liste des personnes
        return maj;
    }

    public static List getGames() {

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
                /*if (lastId == obj.getInt("id")) {
                    mydb.updateGames(i, obj.getInt("id"), obj.getInt("type"), obj.getString("title"), obj.getString("subtitle"), obj.getString("picture"), obj.getString("date"));
                } else {*/
                    mydb.insertGames(i, obj.getInt("id"), obj.getInt("type"), obj.getString("title"), obj.getString("subtitle"), obj.getString("picture"), obj.getString("date"));
                //}

                CardGameObject game = new CardGameObject(obj.getString("title"), obj.getString("picture"), obj.getString("subtitle"));
                maj.add(game);

                // On ajoute la personne à la liste
            }
            //JSONObject obj = new JSONObject(array.getString(1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // On retourne la liste des personnes
        return maj;
    }


    public static void makeSnack(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    public static int getArrayLength() {
        return arrayLength;
    }
}