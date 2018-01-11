package capstone.heartbeat.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import capstone.heartbeat.models.Activity;

/**
 * Created by Lenevo on 1/9/2018.
 */

public class PlansDatabase  {

    private static final String DATABASE_NAME="PlansDB";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE ="Plans";
    private static final String DATABASE_TABLE2 ="PlanActivities";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_TITLE ="title";
    public static final String KEY_DATE = "date";
    public static final String KEY_FREETIME = "free_time";
    public static final String KEY_MINUTES = "minutes";
    public static final String KEY_CALORIES = "calories";
    private static final String KEY_ACTIVITY = "activities";
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
            db.execSQL  ("CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + "( " + KEY_ROWID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TITLE
                    + " TEXT NOT NULL, " + KEY_DATE + " TEXT NOT NULL, "+KEY_CALORIES
                    + " NUMERIC NOT NULL, " + KEY_MINUTES + " INTEGER NOT NULL, " + KEY_FREETIME
                    + " INTEGER NOT NULL)");

            /*db.execSQL  ("CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE2 + "( " + KEY_ROWID
                    + " INTEGER PRIMARY KEY, " + KEY_TITLE
                    + " TEXT NOT NULL, " + KEY_ACTIVITY + " TEXT NOT NULL)");*/
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public PlansDatabase(Context context) {
        ourContext = context;
    }

    public PlansDatabase open() throws SQLException {
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public  void close(){
        ourHelper.close();
    }

    public long createEntry1(String title, String date, double calorie, int minutes, int freetime,List<Activity> activities){
        ContentValues con = new ContentValues();
        con.put(KEY_TITLE,title);
        con.put(KEY_DATE,date);
        con.put(KEY_CALORIES,calorie);
        con.put(KEY_MINUTES,minutes);
        con.put(KEY_FREETIME,freetime);
        return ourDatabase.insert(DATABASE_TABLE,null,con);

        /*ContentValues cv = new ContentValues();

        for (Activity a:activities
             ) {
            String ti =a.getActivities();
            con.put(KEY_TITLE,title);
            con.put(KEY_ACTIVITY,ti);
        }
        ourDatabase.insert(DATABASE_TABLE,null,cv);*/
    }

    public long createEntry2(String id,List<Activity> activity){
        ContentValues con = new ContentValues();
        for (Activity a:activity
             ) {
            String title =a.getActivities();
            con.put(KEY_ROWID,id);
            con.put(KEY_ACTIVITY,title);
        }
        return ourDatabase.insert(DATABASE_TABLE,null,con);
    }

    public String getData() {
//TODO Auto-generated method stub
        String[] columns = new String[] {KEY_ROWID,KEY_TITLE, KEY_DATE, KEY_CALORIES, KEY_MINUTES,KEY_FREETIME};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null,
                null);
        String result = "";

        int iRow = c.getColumnIndex(KEY_ROWID);
        int iTitle = c.getColumnIndex (KEY_TITLE);
        int iDate = c.getColumnIndex(KEY_DATE);
        int iCalorie = c.getColumnIndex(KEY_CALORIES);
        int iMinutes = c.getColumnIndex(KEY_MINUTES);
        int iFreetime = c.getColumnIndex(KEY_FREETIME);

        for (c.moveToFirst();!c.isAfterLast () ;c.moveToNext()) {
            result = result + c.getInt (iRow) + "" + c.getString (iTitle) + "" + c.getString (iDate) + "" +
                    c.getDouble (iCalorie) + c.getInt(iMinutes) + c.getInt(iFreetime) + "\n";
        }
        return result;
    }




}
