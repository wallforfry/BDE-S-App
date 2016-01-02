package fr.wallforfry.bdesapp.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.wallforfry.bdesapp.Adapter.AgendaAdapter;
import fr.wallforfry.bdesapp.AsyncTask.GetEvents;
import fr.wallforfry.bdesapp.BDD.DBHelper;
import fr.wallforfry.bdesapp.MainActivity;
import fr.wallforfry.bdesapp.Object.AgendaObject;
import fr.wallforfry.bdesapp.R;

/**
 * Created by wallerand on 11/11/2015.
 */

public class AgendaFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String title = "Agenda";
    private static final String ARG_SECTION_NUMBER = "section_number";

    public View rootView;

    public static CaldroidFragment caldroidFragment;
    public static DBHelper mydb;

    RecyclerView recyclerView;
    AgendaAdapter adapter;

    private List<AgendaObject> selectEvents = new ArrayList<AgendaObject>();
    public static List events = new ArrayList<>();
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

        rootView = inflater.inflate(R.layout.fragment_agenda, container, false);
        mydb = new DBHelper(getActivity());

        initializeCalendar();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        if (MainActivity.isConnected(getActivity())) {
            GetEvents requete = new GetEvents();
            requete.execute();
        }

        adapter = new AgendaAdapter(events);
        recyclerView.setAdapter(adapter);

        rootView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //events = maj;
                adapter.notifyDataSetChanged();
            }
        });
/*
        rootView.findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", 3, 2013);
                dialogCaldroidFragment.show(getActivity().getSupportFragmentManager(), "TAG");
            }
        });
*/
        adapter.notifyDataSetChanged();

        return rootView;
    }

    public static void addEvent(Date date){
        caldroidFragment.setBackgroundResourceForDate(R.drawable.app_logo, date);
        caldroidFragment.setTextColorForDate(R.color.colorAccent, date);
    }

    public static String getTitle(){
        return title;
    }

    public void initializeCalendar() {

            caldroidFragment = new CaldroidFragment();
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putInt(CaldroidFragment.THEME_RESOURCE, R.style.CaldroidDefault);
            caldroidFragment.setArguments(args);

            final CaldroidListener listener = new CaldroidListener() {
                SimpleDateFormat formatter = new SimpleDateFormat("'Le 'dd-MM-yyyy' à 'HH:mm");

                @Override
                public void onSelectDate(Date adate, View view) {
                    //Toast.makeText(getActivity().getApplicationContext(), formatter.format(date),Toast.LENGTH_SHORT).show();
                    //Cursor rs = mydb.getDataEvent(mydb.numberOfRowsEvents() - 1);
                    Cursor rs = mydb.getEventWithDate(dateToStringShort(adate));
                    if(rs != null && rs.getCount()>0) {

                        selectEvents.clear();
                        for(int i = 0;i<rs.getCount();i++) {
                            rs.moveToNext();
                            int id = rs.getInt(rs.getColumnIndex(DBHelper.AGENDA_COLUMN_ID));
                            String iddb = rs.getString(rs.getColumnIndex(DBHelper.AGENDA_COLUMN_IDDB));
                            String summary = rs.getString(rs.getColumnIndex(DBHelper.AGENDA_COLUMN_SUMMARY));
                            String description = rs.getString(rs.getColumnIndex(DBHelper.AGENDA_COLUMN_DESCRIPTION));
                            String date = rs.getString(rs.getColumnIndex(DBHelper.AGENDA_COLUMN_DATE));
                            String hour = rs.getString(rs.getColumnIndex(DBHelper.AGENDA_COLUMN_HOUR));
                            String start = rs.getString(rs.getColumnIndex(DBHelper.AGENDA_COLUMN_START));
                            String end = rs.getString(rs.getColumnIndex(DBHelper.AGENDA_COLUMN_END));
                            String location = rs.getString(rs.getColumnIndex(DBHelper.AGENDA_COLUMN_LOCATION));
                            selectEvents.add(new AgendaObject(id, iddb, summary, description, date, hour, start, end, location));
                        }

                       makeEventDialog(0, rs.getCount());
                    }
                    else{
                        MainActivity.makeSnack(rootView, "Pas d'évenement ce jour");
                    }

                }

                @Override
                public void onChangeMonth(int month, int year) {
                    String text = "month: " + month + " year: " + year;
                    Toast.makeText(getActivity().getApplicationContext(), text,
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLongClickDate(Date date, View view) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Long click " + formatter.format(date),
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCaldroidViewCreated() {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Caldroid view is created",
                            Toast.LENGTH_SHORT).show();
                    displayEvents();
                }

            };
            caldroidFragment.setCaldroidListener(listener);

            FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
            t.replace(R.id.calendar, caldroidFragment);
            t.commit();
    }

    public static void displayEvents(){
        for(int i=0; i<mydb.numberOfRowsEvents();i++) {
            Cursor rs = mydb.getDataEvent(i);
            rs.moveToFirst();

            int id = rs.getInt(rs.getColumnIndex(DBHelper.AGENDA_COLUMN_ID));
            String iddb = rs.getString(rs.getColumnIndex(DBHelper.AGENDA_COLUMN_IDDB));
            String summary = rs.getString(rs.getColumnIndex(DBHelper.AGENDA_COLUMN_SUMMARY));
            String description = rs.getString(rs.getColumnIndex(DBHelper.AGENDA_COLUMN_DESCRIPTION));
            String date = rs.getString(rs.getColumnIndex(DBHelper.AGENDA_COLUMN_DATE));
            String hour = rs.getString(rs.getColumnIndex(DBHelper.AGENDA_COLUMN_HOUR));
            String start = rs.getString(rs.getColumnIndex(DBHelper.AGENDA_COLUMN_START));
            String end = rs.getString(rs.getColumnIndex(DBHelper.AGENDA_COLUMN_END));
            String location = rs.getString(rs.getColumnIndex(DBHelper.AGENDA_COLUMN_LOCATION));

            AgendaFragment.addEvent(AgendaFragment.stringToDate(start));
        }
    }

    private void makeEventDialog(final int event, final int max){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setMessage("Description : " + description + "\nLieu : " + location + "\nDate : " + date + "\nHeure : " + hour).setTitle(summary);
        AgendaObject obj = selectEvents.get(event);
        String description = "";
        if(!obj.getDescription().equals("")){
            description = "Description : "+obj.getDescription()+"\n";
        }
        String location = "";
        if(!obj.getLocation().equals("")){
            location = "Lieu : "+obj.getLocation()+"\n";
        }
        String date = "Date : "+obj.getDate()+"\n";
        String hour = "Heure : "+obj.getHour()+"\n";

        builder.setTitle(obj.getSummary()).setMessage(description+location+date+hour+"\n"+(event+1)+"/"+max);
        if (event < max - 1) {
            builder.setPositiveButton("Suivant", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    int suivant = event + 1;
                    if (event + 1 > max - 1) {
                        suivant = max - 1;
                    }
                    makeEventDialog(suivant, max);
                }
            });
        }
        if (event > 0) {
            builder.setNegativeButton("Précédent", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    int precedent = event - 1;
                    if (event - 1 < 0) {
                        precedent = 0;
                    }
                    makeEventDialog(precedent, max);
                }
            });
        }
        builder.setNeutralButton("Retour", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static Boolean existNotNull(JSONObject obj, String name){
        if(obj.has(name) && !obj.isNull(name)){
            return true;
        }
        else{
            return false;
        }
    }

    public static Date stringToDate(String aDate) {
        String aFormat = "yyyy-MM-dd'T'HH:mm:ssZZZZZ";
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = null;
        try {
            stringDate = simpledateformat.parse(aDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return stringDate;

    }

    public static String dateToString(Date date) {

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
        //SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String datetime = dateformat.format(date);

        return datetime;
    }

    public static String dateToStringShort(Date date) {

        //SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String datetime = dateformat.format(date);

        return datetime;
    }

    public static String dateToStringHourShort(Date date) {

        //SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
        SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm");
        String datetime = dateformat.format(date);
        return datetime;
    }
}
