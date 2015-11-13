package fr.wallforfry.bdesapp.Fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fr.wallforfry.bdesapp.R;

/**
 * Created by wallerand on 11/11/2015.
 */

public class JeuxFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static String title = "Jeux";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static JeuxFragment newInstance(int sectionNumber) {
        JeuxFragment fragment = new JeuxFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public JeuxFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_jeux, container, false);

        FloatingActionButton fab_game_1 = (FloatingActionButton) rootView.findViewById(R.id.game_1_button);
        fab_game_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                               Intent i;
                try {
                    i = getActivity().getPackageManager().getLaunchIntentForPackage(getString(R.string.game_1_package));
                    if (i == null)
                        throw new PackageManager.NameNotFoundException();
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    // affiche que l'appli n'es pas présente sur le téléphone et ouvre le play store
                    playStore(getString(R.string.game_1_package));
                }
            }
        });

        return rootView;
    }

    public static String getTitle(){
        return title;
    }

    public void playStore(String appPackageName) {      //ouvre le market avec le package cible

        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}