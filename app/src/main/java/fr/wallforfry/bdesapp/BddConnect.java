package fr.wallforfry.bdesapp;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.wallforfry.bdesapp.Object.CardBigPictureObject;
import fr.wallforfry.bdesapp.Object.CardGameObject;
import fr.wallforfry.bdesapp.Object.CardMediumRightObject;
import fr.wallforfry.bdesapp.Object.CardPictureOnlyObject;

/**
 * Created by wallerand on 19/11/2015.
 */
public class BddConnect {

    public static List getPersonnes() {

        List maj = new ArrayList<>();

       /* ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute(stringUrl);
        } else {
            textView.setText("No network connection available.");
        }*/

        try {
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
                // On fait le lien Personne - Objet JSON
                switch (obj.getInt("type")) {
                    case 0:
                        CardGameObject game = new CardGameObject(obj.getString("title"), R.drawable.taupe, obj.getString("subtitle"));
                        maj.add(game);
                        break;
                    case 1:
                        CardPictureOnlyObject picture = new CardPictureOnlyObject(obj.getString("title"), obj.getString("subtitle"), R.drawable.taupe);
                        maj.add(picture);
                        break;
                    case 2:
                        CardBigPictureObject bigPicture = new CardBigPictureObject(obj.getString("title"), obj.getString("subtitle"), obj.getString("content"), R.drawable.taupe);
                        maj.add(bigPicture);
                        break;
                    case 3:
                        CardMediumRightObject mediumRight = new CardMediumRightObject(obj.getString("title"), obj.getString("subtitle"), R.drawable.taupe);
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

    public static void makeSnack(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}