package fr.wallforfry.bdesapp.ViewHolder;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import fr.wallforfry.bdesapp.Object.CardPictureOnlyObject;
import fr.wallforfry.bdesapp.R;

/**
 * Created by wallerand on 15/11/2015.
 */
public class CardPictureOnlyViewHolder extends RecyclerView.ViewHolder{

    private TextView title;
    private TextView subtitle;
    private ImageView imageView;
    private TextView partager;

    private String valueTitle;
    private String valueSubtitle;

    private View view;

    //itemView est la vue correspondante à 1 cellule
    public CardPictureOnlyViewHolder(final View itemView) {
        super(itemView);

        view = itemView;

        //c'est ici que l'on fait nos findView

        title = (TextView) itemView.findViewById(R.id.title);
        subtitle = (TextView) itemView.findViewById(R.id.subtitle);
        imageView = (ImageView) itemView.findViewById(R.id.image);
        partager = (TextView) itemView.findViewById((R.id.partager));

        partager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri bmpUri = getLocalBitmapUri(imageView);
                if (bmpUri != null) {
                    // Construct a ShareIntent with link to image
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Regardes ce que j'ai trouvé, une nouvelle photo de Benjamin Butown : \n\n" + valueTitle + "\n" + valueSubtitle);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                    shareIntent.setType("image/*");
                    // Launch sharing dialog for image
                    view.getContext().startActivity(Intent.createChooser(shareIntent, "Partager cette image !"));


                }
            }
        });
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(CardPictureOnlyObject myObject){
        title.setText(myObject.getTitle());
        valueTitle = myObject.getTitle();
        subtitle.setText(myObject.getSubtitle());
        valueSubtitle = myObject.getSubtitle();
        view.findViewById(R.id.progressBarPictureOnly).setVisibility(View.VISIBLE);
        Picasso.with(imageView.getContext()).load(myObject.getImageUrl()).centerCrop().fit().into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                view.findViewById(R.id.progressBarPictureOnly).setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                imageView.setImageResource(R.drawable.problem);
            }
        });
        //imageView.setImageResource(myObject.getImageUrl());
    }

    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "Benjamin_Butown_" + valueTitle + "_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

}