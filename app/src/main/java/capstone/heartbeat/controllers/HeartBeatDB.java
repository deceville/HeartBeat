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
import capstone.heartbeat.models.User;

/**
 * Created by Lenevo on 1/9/2018.
 */

public class HeartBeatDB {

    private static final String DATABASE_NAME="HeartBeatDB";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_TABLE ="Plans";
    private static final String DATABASE_TABLE2 ="Activity";
    private static final String DATABASE_TABLE3 ="Users";
    private static final String DATABASE_TABLE4 ="Goals";
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

    public static final String KEY_GOALID = "goal_id";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_COMPLETED = "completed";




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
                    + " TEXT NOT NULL, " + KEY_PASSWORD + " TEXT , "+KEY_BIRTH
                    + " TEXT , " + KEY_GENDER + " TEXT , "+KEY_CHL
                    + " INTEGER , " + KEY_HDL + " INTEGER , "+KEY_SBP
                    + " INTEGER , " + KEY_DBP + " INTEGER , "+KEY_SMOKE
                    + " TEXT , " + KEY_BPTR + " TEXT , "+KEY_SLEEP
                    + " TEXT , " + KEY_ACT + " TEXT , "+KEY_DIABETES1
                    + " TEXT , " + KEY_DIABETES2 + " TEXT , "+KEY_CHF
                    + " TEXT , " + KEY_HA + " TEXT , "+KEY_VHD
                    + " TEXT , " + KEY_RA + " TEXT , "+KEY_RHA
                    + " TEXT , " + KEY_CKD + " TEXT , "+KEY_FHCVD
                    + " TEXT , " + KEY_HEARTATTACK
                    + " INTEGER , " + KEY_STROKE+" INTEGER )");
            db.execSQL  ("CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE4 + "( " + KEY_GOALID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ROWID
                    + " TEXT NOT NULL, " + KEY_DESCRIPTION + " TEXT NOT NULL, "+KEY_DURATION
                    + " TEXT NOT NULL, " + KEY_COMPLETED+" TEXT NOT NULL )");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE2);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE3);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE4);
            onCreate(db);
        }
    }

    public HeartBeatDB(Context context) {
        ourContext = context;
    }

    public HeartBeatDB open() throws SQLException {
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

    public long insertUserData(User user,int userID){

        ContentValues con = new ContentValues();
        con.put(KEY_BIRTH,user.birth);
        con.put(KEY_ACT,user.act);
        con.put(KEY_BPTR,user.bptr);
        con.put(KEY_CHF,user.chf);
        con.put(KEY_CHL,user.chl);
        con.put(KEY_HDL,user.hdl);
        con.put(KEY_SBP,user.sbp);
        con.put(KEY_DBP,user.dbp);
        con.put(KEY_CKD,user.ckd);
        con.put(KEY_RHA,user.rha);
        con.put(KEY_VHD,user.vhd);
        con.put(KEY_RA,user.ra);
        con.put(KEY_DIABETES1,user.diab1);
        con.put(KEY_DIABETES2,user.diab2);
        con.put(KEY_FHCVD,user.fhcvd);
        con.put(KEY_GENDER,user.gender);
        con.put(KEY_SLEEP,user.sleep);
        con.put(KEY_SMOKE,user.smoke);
        con.put(KEY_STROKE,user.stroke);
        con.put(KEY_HEARTATTACK,user.heart_attack);

        return ourDatabase.update(DATABASE_TABLE3,con,KEY_ROWID+" = "+ user.id,null);
    }

    public User getUserAssessData(int userID) {
//TODO Auto-generated method stub
        String[] columns = new String[] {KEY_BIRTH, KEY_ACT,KEY_ACT,KEY_BPTR,KEY_CHF,KEY_CHL,
        KEY_HDL,KEY_SBP,KEY_DBP,KEY_CKD,KEY_RHA,KEY_VHD,KEY_RA,KEY_DIABETES1,KEY_DIABETES2,KEY_FHCVD,KEY_GENDER,
        KEY_SLEEP,KEY_SMOKE,KEY_STROKE,KEY_HEARTATTACK};
        Cursor c = ourDatabase.query(DATABASE_TABLE3, columns, null, null, null, null,
                null);
        String result = "";

        int iBirth = c.getColumnIndex(KEY_BIRTH);
        int iAct = c.getColumnIndex (KEY_ACT);
        int iBptr = c.getColumnIndex(KEY_BPTR);
        int iCHF = c.getColumnIndex(KEY_CHF);
        int iCHL = c.getColumnIndex (KEY_CHL);
        int iHDL = c.getColumnIndex(KEY_HDL);
        int iSBP = c.getColumnIndex(KEY_SBP);
        int iDBP = c.getColumnIndex (KEY_DBP);
        int iCKD = c.getColumnIndex(KEY_CKD);
        int iRHA = c.getColumnIndex(KEY_RHA);
        int iVHD = c.getColumnIndex (KEY_VHD);
        int iRA = c.getColumnIndex(KEY_RA);
        int iDIAB1 = c.getColumnIndex(KEY_DIABETES1);
        int iDIAB2 = c.getColumnIndex (KEY_DIABETES2);
        int iFHCVD = c.getColumnIndex(KEY_FHCVD);
        int iGENDER = c.getColumnIndex(KEY_GENDER);
        int iSLEEP = c.getColumnIndex (KEY_SLEEP);
        int iSMOKE = c.getColumnIndex(KEY_SMOKE);
        int iSTROKE = c.getColumnIndex(KEY_STROKE);
        int iHEART = c.getColumnIndex (KEY_HEARTATTACK);

        User user = new User();
        user.setBirth(c.getString(iBirth));
        user.setAct(c.getInt(iAct));
        user.setBptr(c.getInt(iBptr));
        user.setChf(c.getInt(iCHF));
        user.setChl(c.getInt(iCHL));
        user.setHdl(c.getInt(iHDL));
        user.setSbp(c.getInt(iSBP));
        user.setDbp(c.getInt(iDBP));
        user.setCkd(c.getInt(iCKD));
        user.setRha(c.getInt(iRHA));
        user.setVhd(c.getInt(iVHD));
        user.setRa(c.getInt(iRA));
        user.setDiab1(c.getInt(iDIAB1));
        user.setDiab2(c.getInt(iDIAB2));
        user.setFhcvd(c.getInt(iFHCVD));
        user.setGender(c.getString(iGENDER));
        user.setSleep(c.getString(iSLEEP));
        user.setSmoke(c.getInt(iSMOKE));
        user.setStroke(c.getInt(iSTROKE));
        user.setHeart_attack(c.getInt(iHEART));

        return user;
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

    public int getUserID(String email){
        String[] columns = new String[] {KEY_ROWID,KEY_EMAIL};
        Cursor c = ourDatabase.query(DATABASE_TABLE3, columns, null, null, null, null,
                null);

        boolean exist = false;
        int id = 0;
        int user = c.getColumnIndex(KEY_EMAIL);
        int userID = c.getColumnIndex(KEY_ROWID);

        while (c.moveToNext()){
            if (email.equals(c.getString(user))){

                id = c.getInt(userID);
                return id;

            }
        }
        return id;
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
            int title = c.getColumnIndex(HeartBeatDB.KEY_ACTIVITY);

            while (c.moveToNext()) {
                String acts = c.getString(title);
                activity.add(acts);
            }
            Plans.add(activity);

        }

        return Plans;
    }





}