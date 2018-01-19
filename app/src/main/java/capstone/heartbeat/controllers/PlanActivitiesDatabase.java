package capstone.heartbeat.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import capstone.heartbeat.models.Activity;

/**
 * Created by Lenevo on 1/9/2018.
 */

public class PlanActivitiesDatabase  {

    private static final String DATABASE_NAME="PlansDB";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_TABLE2 ="PlanActivities";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_TITLE ="title";
    public static final String KEY_DATE = "date";
    public static final String KEY_ACTIVITY = "activities";
    public static List<Activity> activityList;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    private static class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context ) {
            super (context, DATABASE_NAME, null, DATABASE_VERSION);
//TODO Auto-generated constructor stub
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL  ("CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE2 + "( " + KEY_ROWID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TITLE
                    + " TEXT NOT NULL, " + KEY_ACTIVITY + " TEXT NOT NULL )");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE2);
            onCreate(db);
        }
    }

    public PlanActivitiesDatabase(Context context) {
        ourContext = context;
    }

    public PlanActivitiesDatabase open() throws SQLException {
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public  void close(){
        ourHelper.close();
    }

    public long createEntry2(List<Activity> activity){
        ContentValues con = new ContentValues();
        for (Activity a:activity
                ) {
            String title =a.getActivities();

            con.put(KEY_ACTIVITY,title);
        }
        return ourDatabase.insert(DATABASE_TABLE2,null,con);
    }

    public String getData() {
//TODO Auto-generated method stub
        String[] columns = new String[] {KEY_ROWID,KEY_TITLE, KEY_DATE};
        Cursor c = ourDatabase.query(DATABASE_TABLE2, columns, null, null, null, null,
                null);
        String result = "";

        int iRow = c.getColumnIndex(KEY_ROWID);
        int iTitle = c.getColumnIndex (KEY_TITLE);
        int iDate = c.getColumnIndex(KEY_DATE);

        for (c.moveToFirst();!c.isAfterLast () ;c.moveToNext()) {
            result = result + c.getString (iTitle) + "" + c.getString (iDate) + "\n";
        }
        return result;
    }

    public List<String> getTitle(){
        String[] columns = new String[] {KEY_ROWID,KEY_TITLE};
        Cursor c = ourDatabase.query(DATABASE_TABLE2, columns, null, null, null, null,
                null);

        List<String> titles = new ArrayList<>();
        int title = c.getColumnIndex(KEY_TITLE);
        for (c.moveToFirst();!c.isAfterLast () ;c.moveToNext()) {
            titles.add(c.getString(title));
        }
        return titles;
    }

    public List<String> getDate(){
        String[] columns = new String[] {KEY_ROWID,KEY_DATE};
        Cursor c = ourDatabase.query(DATABASE_TABLE2, columns, null, null, null, null,
                null);

        List<String> dates = new ArrayList<>();
        int date = c.getColumnIndex(KEY_DATE);
        for (c.moveToFirst();!c.isAfterLast () ;c.moveToNext()) {
            dates.add(c.getString(date));
        }
        return dates;
    }

    public List<List<String>> getActivities(List<String> planName){
        List<List<String>> Plans = new ArrayList<>();
        for (String plan:planName
             ) {
            String[] column = new String[]{KEY_TITLE,KEY_ACTIVITY};
            Cursor c = ourDatabase.rawQuery("SELECT * FROM "+DATABASE_TABLE2+" WHERE  "+KEY_TITLE+" = "+plan+";",null);
            //Cursor c = ourDatabase.query(DATABASE_TABLE2, column, plan, null, null, null, null);
            List<String> activity = new ArrayList<>();
            int title = c.getColumnIndex(KEY_TITLE);
            for (c.moveToFirst();!c.isAfterLast () ;c.moveToNext()) {
                activity.add(c.getString(title));
            }
          Plans.add(activity);

        }

        return Plans;
    }




}
