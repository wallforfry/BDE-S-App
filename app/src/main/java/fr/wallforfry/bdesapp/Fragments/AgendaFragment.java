package fr.wallforfry.bdesapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.marcohc.robotocalendar.RobotoCalendarView;
import com.marcohc.robotocalendar.RobotoCalendarView.RobotoCalendarListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import fr.wallforfry.bdesapp.Adapter.AgendaAdapter;
import fr.wallforfry.bdesapp.InputStreamOperations;
import fr.wallforfry.bdesapp.Object.AgendaObject;
import fr.wallforfry.bdesapp.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by wallerand on 11/11/2015.
 */

public class AgendaFragment extends Fragment implements RobotoCalendarListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String title = "Agenda";
    private static final String ARG_SECTION_NUMBER = "section_number";

    private View view;

    private RobotoCalendarView robotoCalendarView;
    private int currentMonthIndex;
    private Calendar currentCalendar;

    RecyclerView recyclerView;
    AgendaAdapter adapter;

    public List events = new ArrayList<>();
    public List maj = new ArrayList<>();

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AgendaFragment newInstance(int sectionNumber) {
        AgendaFragment fragment = new AgendaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public AgendaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_agenda, container, false);
        view = rootView;

        //RobotoCalendarStart
        // Gets the calendar from the view
        robotoCalendarView = (RobotoCalendarView) rootView.findViewById(R.id.robotoCalendarPicker);

        // Set listener, in this case, the same activity
        robotoCalendarView.setRobotoCalendarListener(this);

        // Initialize the RobotoCalendarPicker with the current index and date
        currentMonthIndex = 0;
        currentCalendar = Calendar.getInstance(Locale.getDefault());

        // Mark current day
        robotoCalendarView.markDayAsCurrentDay(currentCalendar.getTime());
        //RobotoCalendarEnd
        initializeCalendar();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        events.add(new AgendaObject("test","description"));

        recyclerView.setLayoutManager(gridLayoutManager);
        Thread task = null;
        if (task == null) {
            task = new Thread(
                    new Runnable() {
                        public void run() {
                            getEvents();
                    }
                    });
            task.start();
        } else {
            task.run();
        }
        adapter = new AgendaAdapter(events);
        recyclerView.setAdapter(adapter);
        rootView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //events = maj;
                //events.add(new AgendaObject("nope", "description"));
                adapter.notifyDataSetChanged();
            }
        });

        adapter.notifyDataSetChanged();
        return rootView;
    }

    @Override
    public void onDateSelected(Date date) {

        // Mark calendar day
        robotoCalendarView.markDayAsSelectedDay(date);

        // Mark that day with random colors
        final Random random = new Random(System.currentTimeMillis());
        final int style = random.nextInt(3);
        switch (style) {
            case 0:
                robotoCalendarView.markFirstUnderlineWithStyle(RobotoCalendarView.BLUE_COLOR, date);
                break;
            case 1:
                robotoCalendarView.markSecondUnderlineWithStyle(RobotoCalendarView.GREEN_COLOR, date);
                break;
            case 2:
                robotoCalendarView.markFirstUnderlineWithStyle(RobotoCalendarView.RED_COLOR, date);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRightButtonClick() {
        currentMonthIndex++;
        updateCalendar();
    }

    @Override
    public void onLeftButtonClick() {
        currentMonthIndex--;
        updateCalendar();
    }

    private void updateCalendar() {
        currentCalendar = Calendar.getInstance(Locale.getDefault());
        currentCalendar.add(Calendar.MONTH, currentMonthIndex);
        robotoCalendarView.initializeCalendar(currentCalendar);
    }

    public static String getTitle(){
        return title;
    }

    public void getEvents() {

        List maj_events = new ArrayList<>();

        try {

            //DBHelper mydb = NewsFragment.dbShare; //mydb = à la base de donnée locale

            //String myurl = "https://www.googleapis.com/calendar/v3/calendars/developer-calendar@google.com/events?key=AIzaSyCdt0M6eNnyXXj5gUICkxvHcDFA18H7r8o";
            String myurl = "https://www.googleapis.com/calendar/v3/calendars/3egm9h4jo5e7niu117dnv1hhutkoknj3@import.calendar.google.com/events?key=AIzaSyCdt0M6eNnyXXj5gUICkxvHcDFA18H7r8o";

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
            JSONArray array = new JSONArray(jsonObject.getString("items"));
            // Pour tous les objets on récupère les infos
            for (int i = 0; i < array.length(); i++) {
            //for (int i = 0; i < 4; i++) {
                // On récupère un objet JSON du tableau
                JSONObject obj = new JSONObject(array.getString(i));


                //ajout en local
                //mydb.updateNews(i, obj.getInt("id"), obj.getInt("type"),obj.getString("title"),obj.getString("subtitle"),obj.getString("content"),obj.getString("picture"),obj.getString("action1"),obj.getString("action2"),obj.getString("date"));

                //AgendaObject event = new AgendaObject(obj.getString("summary"), obj.getString("description"));
                AgendaObject event = new AgendaObject(obj.getString("summary"), "Pas de description");
                events.add(event);
            }
            //JSONObject obj = new JSONObject(array.getString(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // On retourne la liste des personnes
       // return maj_events;
    }

    public void initializeCalendar() {

    }

}