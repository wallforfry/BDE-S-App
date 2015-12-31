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
import fr.wallforfry.bdesapp.Object.AgendaObject;

/**
 * Created by wallerand on 30/12/2015.
 */
public class GetEvents extends AsyncTask<Void, Integer, Void> {


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

        getEvents();
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
        AgendaFragment.displayEvents();
        AgendaFragment.caldroidFragment.refreshView();
    }

    DBHelper mydb;

    public void getEvents() {
        List maj_events = new ArrayList<>();
        try {
            mydb = AgendaFragment.mydb; //mydb = à la base de donnée locale

            //String myurl = "https://www.googleapis.com/calendar/v3/calendars/developer-calendar@google.com/events?key=AIzaSyCdt0M6eNnyXXj5gUICkxvHcDFA18H7r8o";
            String myurl = "https://www.googleapis.com/calendar/v3/calendars/3egm9h4jo5e7niu117dnv1hhutkoknj3@import.calendar.google.com/events?key=AIzaSyCdt0M6eNnyXXj5gUICkxvHcDFA18H7r8o";
            String id = "";
            String summary = "";
            String description = "";
            String start = "";
            String end = "";
            String location = "";


            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();

            /*
             * InputStreamOperations est une classe complémentaire:
             * Elle contient une méthode InputStreamToString.
             */
            String result = InputStreamOperations.InputStreamToString(inputStream);
            mydb.dropEvents();
            // On récupère le JSON complet
            JSONObject jsonObject = new JSONObject(result);
            // On récupère le tableau d'objets qui nous concernent
            JSONArray array = new JSONArray(jsonObject.getString("items"));
            // Pour tous les objets on récupère les infos
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = new JSONObject(array.getString(i));

                if (AgendaFragment.existNotNull(obj, "id")) {
                    id = obj.getString("id");
                }
                if (AgendaFragment.existNotNull(obj, "summary")) {
                    summary = obj.getString("summary");
                }
                if (AgendaFragment.existNotNull(obj, "description")) {
                    description = obj.getString("description");
                }
                if (AgendaFragment.existNotNull(obj, "start")) {
                    start = new JSONObject(obj.getString("start")).getString("dateTime");
                }
                if (AgendaFragment.existNotNull(obj, "end")) {
                    end = new JSONObject(obj.getString("end")).getString("dateTime");
                }
                if (AgendaFragment.existNotNull(obj, "location")) {
                    location = obj.getString("location");
                }

                //ajout en local
                mydb.insertEvent(i, id, summary, description, start, end, location);
                AgendaObject event = new AgendaObject(summary, description, AgendaFragment.stringToDate(start), AgendaFragment.stringToDate(end), location);
                AgendaFragment.events.add(event);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}