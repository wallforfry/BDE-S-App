package fr.wallforfry.bdesapp.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import fr.wallforfry.bdesapp.R;

/**
 * Created by wallerand on 27/12/2015.
 */
public class QrCodeFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static String title = "Scanner";

    static String scanContent = null;
    static String scanFormat;

    static TextView scan_content;

    static Boolean open = false;
    private View view = null;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static QrCodeFragment newInstance(int sectionNumber) {
        QrCodeFragment fragment = new QrCodeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public QrCodeFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.fragment_qr_code, container, false);
        view = rootView;

        TextView scan_format = (TextView) view.findViewById(R.id.scan_format);
        scan_content = (TextView) view.findViewById(R.id.scan_content);

// nous affichons le résultat dans nos TextView

        scan_format.setText("Format : " + scanFormat);


        Button scan_button = (Button) rootView.findViewById(R.id.scan_button);
        //final IntentIntegrator integrator = new IntentIntegrator(getActivity()); // `this` is the current Activity
        //final IntentIntegrator integrator = new IntentIntegratorSupportV4(this); // `this` is the current Activity
        final IntentIntegrator integrator = new IntentIntegrator(getActivity()).forSupportFragment(this);
        integrator.setPrompt("Scanner un code");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setOrientationLocked(false);
        integrator.setBarcodeImageEnabled(true);

        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open = false;
                integrator.initiateScan();
            }
        });

        config();

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
// nous utilisons la classe IntentIntegrator et sa fonction parseActivityResult pour parser le résultat du scan
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {

// nous récupérons le contenu du code barre
            scanContent = scanningResult.getContents();

// nous récupérons le format du code barre
            scanFormat = scanningResult.getFormatName();

            TextView scan_format = (TextView) view.findViewById(R.id.scan_format);
            scan_content = (TextView) view.findViewById(R.id.scan_content);

// nous affichons le résultat dans nos TextView

            scan_format.setText("Format : " + scanFormat);
            config();

        }
        else{
            Toast toast = Toast.makeText(this.getActivity(),
                    "Aucune donnée reçu!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public static String getTitle(){
        return title;
    }

    public void openNavigator(String url){

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        this.getContext().startActivity(i);

    }

    public Boolean enigme(String text){
        if(text.length()>=7) {
            String rslt = text.substring(0, 7);
            if (rslt.equals("ENIGME:")) {
                return true;
            } else {
                return false;
            }
        }
        else{
            return false;
        }
    }

    public void config(){
        if(scanContent != null) {
            if (Patterns.WEB_URL.matcher(scanContent).matches() && open == false) {
                open = true;
                openNavigator(scanContent);
            }
            else if(enigme(scanContent) == true){
                scan_content.setText(scanContent.substring(8));
            }
            else{
                scan_content.setText(scanContent);
            }
        }
    }
}