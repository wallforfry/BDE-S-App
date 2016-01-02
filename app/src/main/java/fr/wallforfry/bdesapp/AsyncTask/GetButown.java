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
import fr.wallforfry.bdesapp.Fragments.AgendaFragment;
import fr.wallforfry.bdesapp.InputStreamOperations;
import fr.wallforfry.bdesapp.MainActivity;

/**
 * Created by wallerand on 01/01/2016.
 */
public class GetButown extends AsyncTask<Void, Integer, Void> {


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Toast.makeText(getApplicationContext(), "Début du traitement asynchrone", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        // Mise à jour de la ProgressBar
        //mProgressBar.setProgress(values[0]);
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        getButown();
            /*int progress;
            for (progress=0;progress<=100;progress++)
            {
                for (int i=0; i<1000000; i++){}
                //la méthode publishProgress met à jour l'interface en invoquant la méthode onProgressUpdate
                publishProgress(progress);
                progress++;
            }*/
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        //Toast.makeText(getApplicationContext(), "Le traitement asynchrone est terminé", Toast.LENGTH_LONG).show();
        MainActivity.butown = MainActivity.loadHeader();
    }

    DBHelper mydb;

    public void getButown() {
        try {
            mydb = MainActivity.mydb; //mydb = à la base de donnée locale

            String myurl = "http://api.wallforfry.fr/getPictures";
            int id = 0;
            String profile = "";
            String couverture = "";
            String date = "";
            String ville = "";

            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();

            /*
             * InputStreamOperations est une classe complémentaire:
             * Elle contient une méthode InputStreamToString.
             */
            String result = InputStreamOperations.InputStreamToString(inputStream);

            mydb.dropButown();

            // On récupère le JSON complet
            JSONObject jsonObject = new JSONObject(result);
            // On récupère le tableau d'objets qui nous concernent
            JSONArray array = new JSONArray(jsonObject.getString("pictures"));
            // Pour tous les objets on récupère les infos
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = new JSONObject(array.getString(i));

                if (AgendaFragment.existNotNull(obj, "id")) {
                    id = obj.getInt("id");
                }
                if (AgendaFragment.existNotNull(obj, "profile")) {
                    profile = obj.getString("profile");
                }
                if (AgendaFragment.existNotNull(obj, "couverture")) {
                    couverture = obj.getString("couverture");
                }
                if (AgendaFragment.existNotNull(obj, "date")) {
                    date = obj.getString("date");
                }
                if (AgendaFragment.existNotNull(obj, "ville")) {
                    ville = obj.getString("ville");
                }

                //ajout en local
                mydb.insertButown(i, id, profile, couverture, date, ville);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}