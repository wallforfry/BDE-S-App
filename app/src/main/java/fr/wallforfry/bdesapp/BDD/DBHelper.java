package fr.wallforfry.bdesapp.BDD;

/**
 * Created by wallerand on 25/11/2015.
 */

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import fr.wallforfry.bdesapp.Fragments.AgendaFragment;
import fr.wallforfry.bdesapp.Fragments.JeuxFragment;
import fr.wallforfry.bdesapp.Object.CardBigPictureObject;
import fr.wallforfry.bdesapp.Object.CardGameObject;
import fr.wallforfry.bdesapp.Object.CardMediumRightObject;
import fr.wallforfry.bdesapp.Object.CardPictureOnlyObject;
import fr.wallforfry.bdesapp.R;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";

    public static final String NEWS_TABLE_NAME = "app_news";
    public static final String NEWS_COLUMN_ID = "id";
    public static final String NEWS_COLUMN_IDDB = "iddb";
    public static final String NEWS_COLUMN_TYPE = "type";
    public static final String NEWS_COLUMN_TITLE = "title";
    public static final String NEWS_COLUMN_SUBTITLE = "subtitle";
    public static final String NEWS_COLUMN_CONTENT = "content";
    public static final String NEWS_COLUMN_PICTURE = "picture";
    public static final String NEWS_COLUMN_ACTION1 = "action1";
    public static final String NEWS_COLUMN_ACTION2 = "action2";
    public static final String NEWS_COLUMN_DATE = "date";

    public static final String GAMES_TABLE_NAME = "app_games";
    public static final String GAMES_COLUMN_ID = "id";
    public static final String GAMES_COLUMN_IDDB = "iddb";
    public static final String GAMES_COLUMN_TYPE = "type";
    public static final String GAMES_COLUMN_TITLE = "title";
    public static final String GAMES_COLUMN_SUBTITLE = "subtitle";
    public static final String GAMES_COLUMN_PICTURE = "picture";
    public static final String GAMES_COLUMN_DATE = "date";

    public static final String AGENDA_TABLE_NAME = "app_agenda";
    public static final String AGENDA_COLUMN_ID = "id";
    public static final String AGENDA_COLUMN_IDDB = "iddb";
    public static final String AGENDA_COLUMN_SUMMARY = "summary";
    public static final String AGENDA_COLUMN_DESCRIPTION = "description";
    public static final String AGENDA_COLUMN_DATE = "date";
    public static final String AGENDA_COLUMN_HOUR = "hour";
    public static final String AGENDA_COLUMN_START = "start";
    public static final String AGENDA_COLUMN_END = "end";
    public static final String AGENDA_COLUMN_LOCATION = "location";

    public static final String BUTOWN_TABLE_NAME = "app_butown";
    public static final String BUTOWN_COLUMN_ID = "id";
    public static final String BUTOWN_COLUMN_IDDB = "iddb";
    public static final String BUTOWN_COLUMN_PROFILE = "profile";
    public static final String BUTOWN_COLUMN_COUVERTURE = "couverture";
    public static final String BUTOWN_COLUMN_DATE = "date";
    public static final String BUTOWN_COLUMN_VILLE = "ville";

    private HashMap hp;
    int id_To_Update = 0;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + NEWS_TABLE_NAME +
                        "(id integer, iddb integer, type integer, title text, subtitle text, content text, picture text, action1 text, action2 text, date text)"
        );

        db.execSQL(
                "create table " + GAMES_TABLE_NAME +
                        "(id integer, iddb integer, type integer, title text, subtitle text, picture text, date text)"
        );
        db.execSQL(
                "create table " + AGENDA_TABLE_NAME +
                        "(id integer, iddb text, summary text, description text, date text, hour text, start text, end text, location text)"
        );
        db.execSQL(
                "create table " + BUTOWN_TABLE_NAME +
                        "(id integer, iddb integer, profile text, couverture text, date text, ville text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + NEWS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GAMES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AGENDA_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BUTOWN_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertNews  (int id, int iddb, int type, String title, String subtitle, String content, String picture,String action1, String action2, String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NEWS_COLUMN_ID, id);
        contentValues.put(NEWS_COLUMN_IDDB, iddb);
        contentValues.put(NEWS_COLUMN_TYPE, type);
        contentValues.put(NEWS_COLUMN_TITLE, title);
        contentValues.put(NEWS_COLUMN_SUBTITLE, subtitle);
        contentValues.put(NEWS_COLUMN_CONTENT, content);
        contentValues.put(NEWS_COLUMN_PICTURE, picture);
        contentValues.put(NEWS_COLUMN_ACTION1, action1);
        contentValues.put(NEWS_COLUMN_ACTION2, action2);
        contentValues.put(NEWS_COLUMN_DATE, date);
        db.insert(NEWS_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ NEWS_TABLE_NAME +" where id="+id+"", null );
        return res;
    }

    public int numberOfRowsNews(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, NEWS_TABLE_NAME);
        return numRows;
    }

    public boolean updateNews (int id, int iddb, int type, String title, String subtitle, String content, String picture,String action1, String action2, String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NEWS_COLUMN_ID, id);
        contentValues.put(NEWS_COLUMN_IDDB, iddb);
        contentValues.put(NEWS_COLUMN_TYPE, type);
        contentValues.put(NEWS_COLUMN_TITLE, title);
        contentValues.put(NEWS_COLUMN_SUBTITLE, subtitle);
        contentValues.put(NEWS_COLUMN_CONTENT, content);
        contentValues.put(NEWS_COLUMN_PICTURE, picture);
        contentValues.put(NEWS_COLUMN_ACTION1, action1);
        contentValues.put(NEWS_COLUMN_ACTION2, action2);
        contentValues.put(NEWS_COLUMN_DATE, date);
        db.update(NEWS_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteNews (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(NEWS_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public void dropNews (){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NEWS_TABLE_NAME, null, null);
    }


    public ArrayList<String> getAllNews()
    {
        ArrayList<String> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + NEWS_TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(NEWS_COLUMN_TITLE)));
            res.moveToNext();
        }
        return array_list;
    }


    public boolean insertGames  (int id, int iddb, int type, String title, String subtitle, String picture, String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GAMES_COLUMN_ID, id);
        contentValues.put(GAMES_COLUMN_IDDB, iddb);
        contentValues.put(GAMES_COLUMN_TYPE, type);
        contentValues.put(GAMES_COLUMN_TITLE, title);
        contentValues.put(GAMES_COLUMN_SUBTITLE, subtitle);
        contentValues.put(GAMES_COLUMN_PICTURE, picture);
        contentValues.put(GAMES_COLUMN_DATE, date);
        db.insert(GAMES_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getDataGames(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ GAMES_TABLE_NAME +" where id="+ id +"", null );
        return res;
    }

    public String getGameSubtitle(Cursor cursor, String index){
        //SQLiteDatabase db = this.getReadableDatabase();
        cursor.moveToFirst();
        String subtitle = "plop";

        subtitle = cursor.getString(cursor.getColumnIndex(index));

        return subtitle;
    }

    public int getGameId(Cursor cursor, String index){
        //SQLiteDatabase db = this.getReadableDatabase();
        cursor.moveToFirst();
        int subtitle = 0;

        subtitle = cursor.getInt(cursor.getColumnIndex(index));

        return subtitle;
    }

    public void gameExist(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + GAMES_TABLE_NAME + " where id=" + id + "", null);
        cursor.getCount();
    }

    public int getRowGame(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("select * from " + GAMES_TABLE_NAME + " where id=" + id + "", null);
        int res = cursor.getColumnIndex("id");
        return res;
    }

    public int numberOfRowsGames(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, GAMES_TABLE_NAME);
        return numRows;
    }

    public boolean updateGames (int id, int iddb, int type, String title, String subtitle, String picture, String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GAMES_COLUMN_ID, id);
        contentValues.put(GAMES_COLUMN_IDDB, iddb);
        contentValues.put(GAMES_COLUMN_TYPE, type);
        contentValues.put(GAMES_COLUMN_TITLE, title);
        contentValues.put(GAMES_COLUMN_SUBTITLE, subtitle);
        contentValues.put(GAMES_COLUMN_PICTURE, picture);
        contentValues.put(GAMES_COLUMN_DATE, date);
        db.update(GAMES_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteGames (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(GAMES_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public void dropGames (){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(GAMES_TABLE_NAME, null, null);
    }

    public ArrayList<String> getAllGames()
    {
        ArrayList<String> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + GAMES_TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(GAMES_COLUMN_TITLE)));
            res.moveToNext();
        }
        return array_list;
    }

    ///////////////////////////////AGENDA////////////////////////////////

    public boolean insertEvent  (int id, String iddb, String summary, String description, String date, String hour, String start, String end, String location)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AGENDA_COLUMN_ID, id);
        contentValues.put(AGENDA_COLUMN_IDDB, iddb);
        contentValues.put(AGENDA_COLUMN_SUMMARY, summary);
        contentValues.put(AGENDA_COLUMN_DESCRIPTION, description);
        contentValues.put(AGENDA_COLUMN_DATE, date);
        contentValues.put(AGENDA_COLUMN_HOUR, hour);
        contentValues.put(AGENDA_COLUMN_START, start);
        contentValues.put(AGENDA_COLUMN_END, end);
        contentValues.put(AGENDA_COLUMN_LOCATION, location);
        db.insert(AGENDA_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getDataEvent(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from " + AGENDA_TABLE_NAME + " where id=" + id + "", null);
        return res;
    }

    public Cursor getEventWithDate(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from " + AGENDA_TABLE_NAME + " where date='" + date + "'", null);
        return res;
    }

    public int numberOfRowsEvents(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, AGENDA_TABLE_NAME);
        return numRows;
    }

    public boolean updateEvent (int id, String iddb, String summary, String description, String date, String hour, String start, String end, String location)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues endValues = new ContentValues();
        endValues.put(AGENDA_COLUMN_ID, id);
        endValues.put(AGENDA_COLUMN_IDDB, iddb);
        endValues.put(AGENDA_COLUMN_SUMMARY, summary);
        endValues.put(AGENDA_COLUMN_DESCRIPTION, description);
        endValues.put(AGENDA_COLUMN_DATE, date);
        endValues.put(AGENDA_COLUMN_HOUR, hour);
        endValues.put(AGENDA_COLUMN_START, start);
        endValues.put(AGENDA_COLUMN_END, end);
        endValues.put(AGENDA_COLUMN_LOCATION, location);
        db.update(AGENDA_TABLE_NAME, endValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteEvent (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(AGENDA_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public void dropEvents (){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AGENDA_TABLE_NAME, null, null);
    }


    public ArrayList<String> getAllEvents()
    {
        ArrayList<String> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + AGENDA_TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(AGENDA_COLUMN_SUMMARY)));
            res.moveToNext();
        }
        return array_list;
    }

    ///////////////////////////////BUTOWN////////////////////////////////

    public boolean insertButown  (int id, int iddb, String profile, String couverture, String date, String ville)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BUTOWN_COLUMN_ID, id);
        contentValues.put(BUTOWN_COLUMN_IDDB, iddb);
        contentValues.put(BUTOWN_COLUMN_PROFILE, profile);
        contentValues.put(BUTOWN_COLUMN_COUVERTURE, couverture);
        contentValues.put(BUTOWN_COLUMN_DATE, date);
        contentValues.put(BUTOWN_COLUMN_VILLE, ville);
        db.insert(BUTOWN_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getDataButown(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from " + BUTOWN_TABLE_NAME + " where id=" + id + "", null);
        return res;
    }

    public int numberOfRowsButown(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, BUTOWN_TABLE_NAME);
        return numRows;
    }

    public boolean updateButown (int id, int iddb, String profile, String couverture, String date, String ville)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues endValues = new ContentValues();
        endValues.put(BUTOWN_COLUMN_ID, id);
        endValues.put(BUTOWN_COLUMN_IDDB, iddb);
        endValues.put(BUTOWN_COLUMN_PROFILE, profile);
        endValues.put(BUTOWN_COLUMN_COUVERTURE, couverture);
        endValues.put(BUTOWN_COLUMN_DATE, date);
        endValues.put(BUTOWN_COLUMN_VILLE, ville);
        db.update(BUTOWN_TABLE_NAME, endValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteButown (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(BUTOWN_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public void dropButown (){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BUTOWN_TABLE_NAME, null, null);
    }
}
