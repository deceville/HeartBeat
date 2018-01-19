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

public class PlansDatabase  {

    private static final String DATABASE_NAME="HeartBeatDB";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_TABLE ="Plans";
    private static final String DATABASE_TABLE2 ="Activity";
    private static final String DATABASE_TABLE3 ="Users";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_PLANID = "plan_id";
    public static final String KEY_ACTIVITYID = "activity_id";
    public static final String KEY_TITLE ="title";
    public static final String KEY_DATE = "date";
    public static final String KEY_FREETIME = "free_time";
    public static final String KEY_MINUTES = "minutes";
    public static final String KEY_CALORIES = "calories";
    public static final String KEY_ACTIVITY = "activities";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_BIRTH ="birth";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_CHL = "chl";
    public static final String KEY_HDL = "hdl";
    public static final String KEY_SBP = "sbp";
    public static final String KEY_DBP = "dbp";
    public static final String KEY_SMOKE ="smoke";
    public static final String KEY_BPTR = "bptr";
    public static final String KEY_SLEEP = "sleep";
    public static final String KEY_ACT = "act";
    public static final String KEY_DIABETES1 = "diab1";
    public static final String KEY_DIABETES2 = "diab2";
    public static final String KEY_CHF = "chf";
    public static final String KEY_HA = "ha";
    public static final String KEY_VHD = "vhd";
    public static final String KEY_RA = "ra";
    public static final String KEY_RHA = "rha";
    public static final String KEY_CKD ="ckd";
    public static final String KEY_FHCVD = "fhcvd";
    public static final String KEY_HEARTATTACK = "heart_attack";
    public static final String KEY_STROKE = "stroke";


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
                    + " TEXT NOT NULL, " + KEY_DATE
                    + " TEXT NOT NULL, " + KEY_CALORIES
                    + " NUMERIC NOT NULL, " + KEY_MINUTES + " INTEGER NOT NULL, " + KEY_FREETIME
                    + " INTEGER NOT NULL )");
            db.execSQL  ("CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE2 + "( " + KEY_ROWID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TITLE
                    + " TEXT NOT NULL, " + KEY_ACTIVITY + " TEXT NOT NULL )");
            db.execSQL  ("CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE3 + "( " + KEY_ROWID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_USERNAME
                    + " TEXT NOT NULL, " + KEY_NAME + " TEXT NOT NULL, "+KEY_EMAIL
                    + " TEXT NOT NULL, " + KEY_PASSWORD + " TEXT NOT NULL, "+KEY_BIRTH
                    + " DATE NOT NULL, " + KEY_GENDER + " TEXT NOT NULL, "+KEY_CHL
                    + " INTEGER NOT NULL, " + KEY_HDL + " INTEGER NOT NULL, "+KEY_SBP
                    + " INTEGER NOT NULL, " + KEY_DBP + " INTEGER NOT NULL, "+KEY_SMOKE
                    + " TEXT NOT NULL, " + KEY_BPTR + " TEXT NOT NULL, "+KEY_SLEEP
                    + " TEXT NOT NULL, " + KEY_ACT + " TEXT NOT NULL, "+KEY_DIABETES1
                    + " TEXT NOT NULL, " + KEY_DIABETES2 + " TEXT NOT NULL, "+KEY_CHF
                    + " TEXT NOT NULL, " + KEY_HA + " TEXT NOT NULL, "+KEY_VHD
                    + " TEXT NOT NULL, " + KEY_RA + " TEXT NOT NULL, "+KEY_RHA
                    + " TEXT NOT NULL, " + KEY_CKD + " TEXT NOT NULL, "+KEY_FHCVD
                    + " TEXT NOT NULL, " + KEY_HEARTATTACK
                    + " TEXT NOT NULL, " + KEY_STROKE+" TEXT NOT NULL )");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE2);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE3);
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

    public long createEntry1(String title, String date, double calorie, int minutes, int freetime){
        ContentValues con = new ContentValues();
        con.put(KEY_TITLE,title);
        con.put(KEY_DATE,date);
        con.put(KEY_CALORIES,calorie);
        con.put(KEY_MINUTES,minutes);
        con.put(KEY_FREETIME,freetime);
        return ourDatabase.insert(DATABASE_TABLE,null,con);

    }

    public boolean createEntry2(List<String> activity,String PlanName){
        ContentValues con = new ContentValues();
        for (String a:activity) {
            con.put(KEY_TITLE,PlanName);
            con.put(KEY_ACTIVITY,a);
            ourDatabase.insert(DATABASE_TABLE2,null,con);
        }
        return true;
    }

    public long insertUser(String username,String name,String email,String password){
        ContentValues con = new ContentValues();
            con.put(KEY_USERNAME,username);
            con.put(KEY_NAME,name);
            con.put(KEY_EMAIL,email);
            con.put(KEY_PASSWORD,password);
        System.out.println("success");
            return ourDatabase.insert(DATABASE_TABLE3,null,con);


    }


    public boolean checkUser(String email,String pass){
        String[] columns = new String[] {KEY_ROWID,KEY_EMAIL,KEY_PASSWORD};
        Cursor c = ourDatabase.query(DATABASE_TABLE3, columns, null, null, null, null,
                null);

        boolean exist = false;

        int user = c.getColumnIndex(KEY_EMAIL);
        int password = c.getColumnIndex(KEY_PASSWORD);

        while (c.moveToNext()){
            System.out.println(c.getString(user)+":"+c.getString(password));
            if (email.equals(c.getString(user))){
                if (pass.equals(c.getString(password))){

                    exist = true;
                    return exist;
                }
            }
        }
        return exist;
    }
    public String getData() {
//TODO Auto-generated method stub
        String[] columns = new String[] {KEY_ROWID,KEY_TITLE, KEY_ACTIVITY};
        Cursor c = ourDatabase.query(DATABASE_TABLE2, columns, null, null, null, null,
                null);
        String result = "";

        int iRow = c.getColumnIndex(KEY_ROWID);
        int iTitle = c.getColumnIndex (KEY_TITLE);
        int iDate = c.getColumnIndex(KEY_ACTIVITY);


        for (c.moveToFirst();!c.isAfterLast () ;c.moveToNext()) {
            result = result + c.getString (iTitle) + "" + c.getString (iDate) +  "\n";
        }
        return result;
    }

    public List<String> getTitle(){
        String[] columns = new String[] {KEY_ROWID,KEY_TITLE};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null,
                null);

        List<String> titles = new ArrayList<>();
        int title = c.getColumnIndex(KEY_TITLE);
        for (c.moveToFirst();!c.isAfterLast () ;c.moveToNext()) {
            titles.add(c.getString(title));
        }
        return titles;
    }

    public List<List<String>> getActivities(List<String> planName){
        List<List<String>> Plans = new ArrayList<>();
        for (String plan:planName
                ) {
            Cursor c = ourDatabase.rawQuery("SELECT "+KEY_ACTIVITY+" FROM "+DATABASE_TABLE2+" WHERE  "+KEY_TITLE+" = '"+plan+"';",null);
            List<String> activity = new ArrayList<>();
            int title = c.getColumnIndex(PlansDatabase.KEY_ACTIVITY);

            while (c.moveToNext()) {
                String acts = c.getString(title);
                activity.add(acts);
            }
            Plans.add(activity);

        }

        return Plans;
    }





}