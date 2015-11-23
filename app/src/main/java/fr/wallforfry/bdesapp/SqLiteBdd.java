package fr.wallforfry.bdesapp;

/**
 * Created by wallerand on 23/11/2015.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqLiteBdd extends SQLiteOpenHelper {

    private static final String TABLE_NEWS = "news";
    private static final String COL_ID = "ID";
    private static final String COL_NEWS = "News";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_NEWS + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NEWS + " TEXT NOT NULL);";

    public SqLiteBdd(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on créé la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_NEWS + ";");
        onCreate(db);
    }

}
