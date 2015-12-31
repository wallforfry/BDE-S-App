package fr.wallforfry.bdesapp.ViewHolder;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import fr.wallforfry.bdesapp.Fragments.NewsFragment;
import fr.wallforfry.bdesapp.MainActivity;
import fr.wallforfry.bdesapp.Object.CardBigPictureObject;
import fr.wallforfry.bdesapp.R;

/**
 * Created by wallerand on 15/11/2015.
 */
public class CardBigPictureViewHolder extends RecyclerView.ViewHolder{

    private TextView contenu;
    private TextView title;
    private TextView subtitle;
    private ImageView imageView;
    private ImageButton expend;
    private TextView ouvrir;
    private TextView partager;
    private CardBigPictureObject myObject;

    private String valueTitle;
    private String valueSubtitle;
    private String valueText;
    private String actionOuvrir;
    private String actionPartager;

    private View view;

    //itemView est la vue correspondante à 1 cellule
    public CardBigPictureViewHolder(final View itemView) {
        super(itemView);

        view = itemView;

        //c'est ici que l'on fait nos findView

        title = (TextView) itemView.findViewById(R.id.title);
        subtitle = (TextView) itemView.findViewById(R.id.subtitle);
        contenu = (TextView) itemView.findViewById(R.id.text);
        imageView = (ImageView) itemView.findViewById(R.id.image);
        ouvrir = (TextView) itemView.findViewById(R.id.ouvrir);
        partager = (TextView) itemView.findViewById(R.id.partager);
        expend = (ImageButton) itemView.findViewById(R.id.expend);


        expend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!myObject.getOpen()) {
                    moreContent(myObject);
                } else {
                    lessContent(myObject);
                }
            }
        });

        ouvrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = actionOuvrir;
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    v.getContext().startActivity(i);
                }
                catch (android.content.ActivityNotFoundException anfe) {
                    MainActivity.makeSnack(NewsFragment.rootView, "Oups, pas de lien disponible..");
                }

            }
        });

        partager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareTextUrl(valueTitle, valueSubtitle, valueText, actionOuvrir);

            }
        });
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(CardBigPictureObject myObject){
        this.myObject = myObject;
        title.setText(myObject.getTitle());
        valueTitle = myObject.getTitle();
        subtitle.setText(myObject.getSubtitle());
        valueSubtitle = myObject.getSubtitle();
        valueText = myObject.getText();

        actionOuvrir = myObject.getActionOuvrir();
        actionPartager = myObject.getActionPartager();

        if(!myObject.getOpen()){
            lessContent(myObject);
        }else{
            moreContent(myObject);
        }
        view.findViewById(R.id.progressBarBigPicture).setVisibility(View.VISIBLE);
        Picasso.with(imageView.getContext()).load(myObject.getImageUrl()).centerCrop().fit().into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                view.findViewById(R.id.progressBarBigPicture).setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                //imageView.setImageResource(R.drawable.problem);
            }
        });
        //imageView.setImageResource(myObject.getImageUrl());
    }

    public void moreContent(CardBigPictureObject myObject){
        contenu.setText(myObject.getText());
        contenu.setVisibility(View.VISIBLE);
        contenu.setPadding(0, 16, 0, 24);
        expend.setImageResource(R.drawable.ic_expand_more_black_24dp);
        myObject.setOpen(true);
    }

    public void lessContent(CardBigPictureObject myObject){
        contenu.setText("");
        contenu.setVisibility(View.GONE);
        contenu.setPadding(0,8,0,0);
        expend.setImageResource(R.drawable.ic_expand_less_black_24dp);
        myObject.setOpen(false);
    }

    private void shareTextUrl(String subject, String subtitle, String text, String lien) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, subject);
        share.putExtra(Intent.EXTRA_TEXT, "Regardes ce que j'ai trouvé, un nouveau post de Benjamin Butown : \n\n" + subject + "\n" + subtitle + "\n\n" + text + "\n\nRetrouve le post complet sur : " + lien);

        view.getContext().startActivity(Intent.createChooser(share, "Partager le post !"));
    }
}