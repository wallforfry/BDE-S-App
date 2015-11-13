package fr.wallforfry.bdesapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.wallforfry.bdesapp.MainActivity;
import fr.wallforfry.bdesapp.R;
import fr.wallforfry.bdesapp.SettingsActivity;

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
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

      //permet de récupérer les préférences
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String pref = sp.getString("example_text", "YourName");
        TextView text = (TextView) rootView.findViewById(R.id.textView2);
        text.setText(pref);

        return rootView;
    }

    public static String getTitle(){
        return title;
    }

}