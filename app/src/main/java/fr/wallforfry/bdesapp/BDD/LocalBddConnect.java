package fr.wallforfry.bdesapp.BDD;

import android.content.Context;
import android.database.Cursor;
import android.renderscript.Sampler;

import java.util.ArrayList;
import java.util.List;

import fr.wallforfry.bdesapp.Object.CardBigPictureObject;
import fr.wallforfry.bdesapp.Object.CardGameObject;
import fr.wallforfry.bdesapp.Object.CardMediumRightObject;
import fr.wallforfry.bdesapp.Object.CardPictureOnlyObject;
import fr.wallforfry.bdesapp.R;

/**
 * Created by wallerand on 25/11/2015.
 */
public class LocalBddConnect {

    int id_To_Update = 0;


    public List getLocalNews(Context context){

        DBHelper mydb ;

        mydb = new DBHelper(context);

        int Value = 1; //id de la recherche

        Cursor rs = mydb.getData(Value);
        id_To_Update = Value;
        rs.moveToFirst();

        List localList = new ArrayList<>();

        int type = rs.getInt(rs.getColumnIndex(DBHelper.NEWS_COLUMN_TYPE));
        String title = rs.getString(rs.getColumnIndex(DBHelper.NEWS_COLUMN_TITLE));
        String subtitle = rs.getString(rs.getColumnIndex(DBHelper.NEWS_COLUMN_SUBTITLE));
        String content = rs.getString(rs.getColumnIndex(DBHelper.NEWS_COLUMN_CONTENT));
        String picture = rs.getString(rs.getColumnIndex(DBHelper.NEWS_COLUMN_PICTURE));
        String action1 = rs.getString(rs.getColumnIndex(DBHelper.NEWS_COLUMN_ACTION1));
        String action2 = rs.getString(rs.getColumnIndex(DBHelper.NEWS_COLUMN_ACTION2));

        switch (type) {
            case 0:
                CardGameObject game = new CardGameObject(title, R.drawable.taupe, subtitle);
                localList.add(game);
                break;
            case 1:
                CardPictureOnlyObject pictureOnly = new CardPictureOnlyObject(title, subtitle, R.drawable.taupe);
                localList.add(pictureOnly);
                break;
            case 2:
                CardBigPictureObject bigPicture = new CardBigPictureObject(title, subtitle, content, R.drawable.taupe);
                localList.add(bigPicture);
                break;
            case 3:
                CardMediumRightObject mediumRight = new CardMediumRightObject(title, subtitle, R.drawable.taupe);
                localList.add(mediumRight);
                break;
        }
        return localList;
    }
}
