package fr.wallforfry.bdesapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import java.util.ArrayList;
import java.util.List;

import fr.wallforfry.bdesapp.Adapter.AnnalesAdapter;
import fr.wallforfry.bdesapp.Object.AnnalesObject;
import fr.wallforfry.bdesapp.R;

/**
 * Created by wallerand on 11/11/2015.
 */

public class AnnalesFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static String title = "Annales";

    private RecyclerView recyclerView;
    private List<AnnalesObject> annalesList = new ArrayList<>();
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AnnalesFragment newInstance(int sectionNumber) {
        AnnalesFragment fragment = new AnnalesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public AnnalesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_annales, container, false);

        ajouterAnnales();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.annalesRecyclerView);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
       // recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        recyclerView.setAdapter(new AnnalesAdapter(annalesList));

        final WebView webView = (WebView) rootView.findViewById(R.id.webView);
        //webView.setWebViewClient(new annalesWebView());
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);

        webView.setWebViewClient( new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                webView.loadUrl("javascript:(function() { " +
                        "document.getElementsById('left-menu')[0].style.width=\"0px\";" +
                        "})()");
            }
        });

/*
        final String[] html = {null};
        final String mime = "text/html";
        final String encoding = "utf-8";

        Thread task = new Thread(
                    new Runnable() {
                        public void run() {
                            try {
                                String url = "http://bde.esiee.fr/annales/login";
                                Connection.Response response = Jsoup.connect(url).method(Connection.Method.GET).execute();
                               //Document document = Jsoup.connect(url).get();

                    /*response = Jsoup.connect(url)
                    .cookies(response.cookies())
                    .data("action", "/annales/login_check")
                    .data("username", "delevacw")
                    .data("password", "F5jxbqwKT")
                    .method(Connection.Method.POST)
                    .followRedirects(true)
                    .execute();*/
        /*
                                Document doc = Jsoup.connect(url).get();
                                Elements token = doc.select("input[type=hidden]");
                                String tokenValue = token.attr("value").toString();

                                System.out.println(tokenValue);

                               // response = Jsoup.connect(url +"_check").method(Connection.Method.GET).execute();
                                Document document = Jsoup.connect(url)
                                        .cookies(response.cookies())
                                        .header(":host", "bde.esiee.fr")
                                        .header(":vesion", "HTTP/1.1")
                                        .header("origin", "https://bde.esiee.fr")
                                        .header("accept-encoding", "gzip, deflate")
                                        .header("accept-language", "fr-FR,fr;q=0.8,en-US;q=0.6,en;q=0.4")
                                        .header(":path", "/annales/login_check")
                                        .header("upgrade-insecure-requests", "1")
                                        .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36")
                                        .header("content-type", "application/x-www-form-urlencoded")
                                        .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,* / *k ;q=0.8")
                                        .header("cache-control", "max-age=0")
                                        .header("referer", "https://bde.esiee.fr/annales/login")
                                        .header(":scheme", "https")
                                        .header(":method", "POST")
                                        .data("_csrf_token", tokenValue)
                                        .data("_username", "delevacw")
                                        .data("_password", "F5jxbqwKT")
                                        .ignoreHttpErrors(true)
                                        .ignoreContentType(true)
                                        .post();

                                Document retour = Jsoup.connect("http://bde.esiee.fr/annales/documents").get();
                                //Document document = response.parse();
                                Elements content = retour.select("div.content");
                                //Elements content = document.select("body");
                                html[0] = content.toString();

                                System.out.println("Retour " + html[0]);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

            task.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                webView.loadData(html[0], mime, encoding);
            }
        }, 5000);

        */
        webView.loadUrl("http://bde.esiee.fr/annales/documents");


        return rootView;
    }

    public static String getTitle(){
        return title;
    }

    private void ajouterAnnales() {
        annalesList.add(new AnnalesObject("E1"));
        annalesList.add(new AnnalesObject("E2"));
        annalesList.add(new AnnalesObject("E3"));
        annalesList.add(new AnnalesObject("E4"));
        annalesList.add(new AnnalesObject("E5"));


    }

    private class annalesWebView extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}