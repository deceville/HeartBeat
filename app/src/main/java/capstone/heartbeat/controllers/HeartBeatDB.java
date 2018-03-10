package capstone.heartbeat.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.Toast;

import capstone.heartbeat.models.Activity;
import capstone.heartbeat.models.Bank;
import capstone.heartbeat.models.Goal;
import capstone.heartbeat.models.Plans;
import capstone.heartbeat.models.Progress;
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
    private static final String DATABASE_TABLE5 ="Bank";
    private static final String DATABASE_TABLE6 ="Progress";
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
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_HEIGHT = "height";

    public static final String KEY_BIRTH ="birth";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_CHL = "chl";
    public static final String KEY_LDL = "ldl";
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
    public static final String KEY_VALUE = "value";
    public static final String KEY_ACTIONS = "actions";
    public static final String KEY_MET = "mets";
    public static final String KEY_ISCALCULATED = "calculated";
    public static final String KEY_TOTAL = "totalWeightLoss";
    public static final String KEY_WEIGHTLOSS = "WeightLoss";
    public static final String KEY_PLANPROGRESS = "progress";
    public static final String KEY_USERID = "userid";

    public static final String KEY_BANKID = "bankid";
    public static final String KEY_HEARTS = "hearts";
    public static final String KEY_COINS = "coins";
    public static final String KEY_TOTALTIME = "timetotal";

    public static final String KEY_DATES = "trans_date";
    public static final String KEY_PROG = "progress";
    public static final String KEY_TYPE = "TYPE";





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
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ KEY_USERID
                    + " INTEGER , " + KEY_TITLE
                    + " TEXT NOT NULL, " + KEY_DATE
                    + " TEXT NOT NULL, " + KEY_CALORIES
                    + " NUMERIC NOT NULL, " + KEY_MINUTES
                    + " INTEGER NOT NULL, " + KEY_FREETIME
                    + " TEXT NOT NULL, " + KEY_TOTAL
                    + " NUMERIC , " +KEY_PLANPROGRESS
                    + " NUMERIC , "+ KEY_COMPLETED
                    + " INTEGER NOT NULL )");
            db.execSQL  ("CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE2 + "( " + KEY_ROWID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TITLE
                    + " TEXT NOT NULL, " + KEY_ACTIVITY
                    + " TEXT NOT NULL, "+KEY_COMPLETED
                    + " TEXT , " +KEY_WEIGHTLOSS
                    + " NUMERIC , "+ KEY_DURATION
                    + " INTEGER )");
            db.execSQL  ("CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE3 + "( " + KEY_ROWID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_USERNAME
                    + " TEXT NOT NULL, " + KEY_NAME + " TEXT NOT NULL, " + KEY_PASSWORD + " TEXT , "+KEY_BIRTH
                    + " TEXT , " + KEY_GENDER + " TEXT , "+KEY_CHL
                    + " INTEGER , " + KEY_HDL + " INTEGER , " + KEY_LDL
                    + " INTEGER , "+KEY_SBP
                    + " INTEGER , " + KEY_DBP + " INTEGER , "+KEY_SMOKE
                    + " INTEGER , " + KEY_BPTR + " INTEGER , "+KEY_SLEEP
                    + " TEXT , " + KEY_ACT + " INTEGER , "+KEY_DIABETES1
                    + " INTEGER , " + KEY_DIABETES2 + " INTEGER , "+KEY_CHF
                    + " INTEGER , " + KEY_HA + " INTEGER , "+KEY_VHD
                    + " INTEGER , " + KEY_RA + " INTEGER , "+KEY_RHA
                    + " INTEGER , " + KEY_CKD + " INTEGER , "+KEY_FHCVD
                    + " INTEGER , " + KEY_HEARTATTACK
                    + " INTEGER , " + KEY_WEIGHT
                    + " INTEGER , " + KEY_ISCALCULATED
                    + " TEXT , "+ KEY_HEIGHT
                    + " NUMERIC , " + KEY_MET
                    + " NUMERIC , " + KEY_STROKE+" INTEGER )");
            db.execSQL  ("CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE4 + "( " + KEY_GOALID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ROWID
                    + " INTEGER NOT NULL, " + KEY_DESCRIPTION + " TEXT NOT NULL, "+KEY_DURATION
                    + " INTEGER NOT NULL, " + KEY_VALUE
                    + " NUMERIC NOT NULL, " + KEY_ACTIONS
                    + " TEXT NOT NULL, " + KEY_COMPLETED+" TEXT NOT NULL )");

            db.execSQL  ("CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE5 + "( " + KEY_BANKID
                    + " INTEGER , " + KEY_HEARTS
                    + " INTEGER , " + KEY_COINS + " INTEGER , "+KEY_TOTALTIME
                    + " INTEGER  )");

            db.execSQL  ("CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE6 + "( " + KEY_ROWID
                    + " INTEGER , " + KEY_USERID
                    + " INTEGER , "+ KEY_DATES
                    + " TEXT , " + KEY_TYPE + " TEXT , "+KEY_PROG
                    + " NUMERIC  )");


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE2);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE3);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE4);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE5);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE6);
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

    public long newProgress(int user,String date,double pr,String type){
        ContentValues cv = new ContentValues();
        cv.put(KEY_USERID,user);
        cv.put(KEY_DATES,date);
        cv.put(KEY_PROG,pr);
        cv.put(KEY_TYPE,type);
        return ourDatabase.insert(DATABASE_TABLE6,null,cv);

    }

    public List<Progress> getweightProg(int user, String type){
        String[] col = new String[]{KEY_USERID,KEY_DATES,KEY_PROG,KEY_TYPE};
        Cursor c = ourDatabase.query(DATABASE_TABLE6,col,KEY_TYPE +"= '"+type +"' AND "+KEY_USERID+"="+user,null,null,null,null);
        Progress p = new Progress();
        List<Progress> lp = new ArrayList<>();
        int date = c.getColumnIndex(KEY_DATES);
        int prog = c.getColumnIndex(KEY_PROG);
        int types = c.getColumnIndex(KEY_TYPE);

        while (c.moveToNext()){
            p.setDate(c.getString(date));
            p.setProgress(c.getDouble(prog));
            p.setType(c.getString(types));
            lp.add(p);
            System.out.println(p);
        }
        return lp;


    }

    public long createEntry1(int userID,String title, String date, double calorie, int minutes, int freetime,boolean completed,double totalWeight){
        ContentValues con = new ContentValues();
        con.put(KEY_USERID,userID);
        con.put(KEY_TITLE,title);
        con.put(KEY_DATE,date);
        con.put(KEY_CALORIES,calorie);
        con.put(KEY_MINUTES,minutes);
        con.put(KEY_FREETIME,freetime);
        String complete = Boolean.toString(completed);
        con.put(KEY_COMPLETED,complete);
        con.put(KEY_TOTAL,totalWeight);
        return ourDatabase.insert(DATABASE_TABLE,null,con);

    }

    public boolean createEntry2(List<String> activity,String PlanName,boolean completed,List<Double> weightloss){
        ContentValues con = new ContentValues();

        for (int i=0;i<activity.size();i++){
            con.put(KEY_TITLE,PlanName);
            con.put(KEY_ACTIVITY,activity.get(i));
            String done = Boolean.toString(completed);
            con.put(KEY_COMPLETED,done);
            con.put(KEY_DURATION,0);
            con.put(KEY_WEIGHTLOSS,weightloss.get(i));
            ourDatabase.insert(DATABASE_TABLE2,null,con);
        }
        return true;
    }

    public long insertUser(String username,String name,String password){
        ContentValues con = new ContentValues();
            con.put(KEY_USERNAME,username);
            con.put(KEY_NAME,name);
            con.put(KEY_PASSWORD,password);
        System.out.println("success");
            return ourDatabase.insert(DATABASE_TABLE3,null,con);
    }
    public long updateUser(String username,String name,String birth,String sleep,int uid){
        ContentValues con = new ContentValues();
        con.put(KEY_USERNAME,username);
        con.put(KEY_NAME,name);
        con.put(KEY_BIRTH,birth);
        con.put(KEY_SLEEP,sleep);
        return ourDatabase.update(DATABASE_TABLE3,con,KEY_ROWID + " = "+uid,null);
    }

    public long updateWeight(int user,double weight){
        ContentValues cv = new ContentValues();
        cv.put(KEY_WEIGHT,weight);
        return ourDatabase.update(DATABASE_TABLE3,cv,KEY_ROWID + " = "+user,null);
    }

    public long insertUserData(User user,int userID){

        ContentValues con = new ContentValues();
        con.put(KEY_BIRTH,user.birth);
        con.put(KEY_ACT,user.act);
        con.put(KEY_BPTR,user.bptr);
        con.put(KEY_CHF,user.chf);
        con.put(KEY_CHL,user.chl);
        con.put(KEY_LDL,user.ldl);
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
        con.put(KEY_WEIGHT,user.weight);
        con.put(KEY_HEIGHT,user.height);
        con.put(KEY_ISCALCULATED,Boolean.toString(user.isCalculated));
        System.out.println("insert user check: "+userID);
        return ourDatabase.update(DATABASE_TABLE3,con,KEY_ROWID+" = "+ userID,null);
    }

   /* public long insertUserMET(User user,int userID){

        ContentValues con = new ContentValues();
        con.put(KEY_MET,user.birth);
        return ourDatabase.update(DATABASE_TABLE3,con,KEY_ROWID+" = "+ userID,null);
    }*/

    public long insertGoal(int uid, Goal goals){
        ContentValues val = new ContentValues();
        val.put(KEY_ROWID,uid);
        val.put(KEY_DESCRIPTION,goals.getDescription());
        val.put(KEY_DURATION,goals.getDuration());
        val.put(KEY_COMPLETED,goals.completed);
        val.put(KEY_VALUE,goals.getValue());
        val.put(KEY_ACTIONS,goals.getAction());

        return ourDatabase.insert(DATABASE_TABLE4,null,val);
    }

    public long updatePlan(String title,double progress){

        ContentValues cv = new ContentValues();
        cv.put(KEY_PLANPROGRESS,progress);

        return ourDatabase.update(DATABASE_TABLE,cv,KEY_TITLE + " = '" + title+"';",null);

    }

    public long updatePlan(String title,String act, boolean completed){

        ContentValues cv = new ContentValues();
        cv.put(KEY_COMPLETED,Boolean.toString(completed));

        return ourDatabase.update(DATABASE_TABLE2,cv,KEY_ACTIVITY + " = '" + act +"' AND " + KEY_TITLE + " = '" + title+"';",null);

    }

    public long updatePlan(double totalWeight,String title){

        ContentValues cv = new ContentValues();
        cv.put(KEY_TOTAL,totalWeight);

        return ourDatabase.update(DATABASE_TABLE,cv,KEY_TITLE + " = '" + title+"';",null);

    }


    public  long updatePlanList(String title, boolean completed){
        ContentValues cv = new ContentValues();
        cv.put(KEY_COMPLETED,Boolean.toString(completed));

        return ourDatabase.update(DATABASE_TABLE,cv, KEY_TITLE + " = '" + title+"';",null);

    }

    public Plans getPlanProgress(String title){
        String[] col = new String[] {KEY_TITLE,KEY_PLANPROGRESS,KEY_TOTAL,KEY_COMPLETED,KEY_MINUTES};
        Cursor c = ourDatabase.query(DATABASE_TABLE,col,null,null,null,null,null);

        int iTitle = c.getColumnIndex(KEY_TITLE);
        int iProgress = c.getColumnIndex(KEY_PLANPROGRESS);
        int iTotal = c.getColumnIndex(KEY_TOTAL);
        int iCompleted = c.getColumnIndex(KEY_COMPLETED);
        int iMinutes = c.getColumnIndex(KEY_MINUTES);

        Plans dece_plan = new Plans();

        while (c.moveToNext()){
            dece_plan.setTitle(c.getString(iTitle));
            dece_plan.setProgress(c.getDouble(iProgress));
            dece_plan.setTotalWeightLoss(c.getDouble(iTotal));
            if (c.getString(iCompleted).equals("true")){
                dece_plan.setCompleted(true);
            }else dece_plan.setCompleted(false);

            dece_plan.setMinutes(c.getInt(iMinutes));
        }
        return dece_plan;
    }

    public Goal getGoals(int userID){

        String[] col =  new String[] {KEY_ROWID,KEY_GOALID,KEY_DESCRIPTION,KEY_DURATION,KEY_COMPLETED,KEY_VALUE,KEY_ACTIONS};
        Cursor c = ourDatabase.query(DATABASE_TABLE4,col,null,null,null,null,null);

        int iRow = c.getColumnIndex(KEY_ROWID);
        int iGoal = c.getColumnIndex(KEY_GOALID);
        int iDescription = c.getColumnIndex(KEY_DESCRIPTION);
        int iDuration = c.getColumnIndex(KEY_DURATION);
        int iCompleted = c.getColumnIndex(KEY_COMPLETED);
        int iValue = c.getColumnIndex(KEY_VALUE);
        int iActs = c.getColumnIndex(KEY_ACTIONS);

        while (c.moveToNext()){
            if (c.getInt(iRow)==userID){
                Goal goal = new Goal();
                goal.setGoalID(c.getInt(iGoal));
                goal.setDescription(c.getString(iDescription));
                goal.setDuration(c.getString(iDuration));
                boolean com = false;
                if (c.getString(iCompleted).equals(true)){
                    goal.setCompleted(true);
                }else  goal.setCompleted(com);

                goal.setValue(c.getDouble(iValue));
                goal.setAction(c.getString(iActs));

                return goal;
            }
        }
        return null;
    }

    public User getUserAssessData(int userID) {
//TODO Auto-generated method stub
        String[] columns = new String[] {KEY_ROWID,KEY_BIRTH,KEY_ACT,KEY_BPTR,KEY_CHF,KEY_CHL,
        KEY_HDL,KEY_LDL,KEY_SBP,KEY_DBP,KEY_CKD,KEY_RHA,KEY_VHD,KEY_RA,KEY_DIABETES1,KEY_DIABETES2,KEY_FHCVD,KEY_GENDER,
        KEY_SLEEP,KEY_SMOKE,KEY_STROKE,KEY_HEARTATTACK,KEY_WEIGHT,KEY_HEIGHT,KEY_NAME,KEY_USERNAME,KEY_ISCALCULATED};
        int uid = userID;
        System.out.println("UID: "+uid);
        Cursor c = ourDatabase.query(DATABASE_TABLE3, columns,null
                , null, null, null,
                null);


        int iRow = c.getColumnIndex(KEY_ROWID);
        int iBirth = c.getColumnIndex(KEY_BIRTH);
        int iAct = c.getColumnIndex (KEY_ACT);
        int iBptr = c.getColumnIndex(KEY_BPTR);
        int iCHF = c.getColumnIndex(KEY_CHF);
        int iCHL = c.getColumnIndex (KEY_CHL);
        int iLDL = c.getColumnIndex (KEY_LDL);
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
        int iWeight = c.getColumnIndex(KEY_WEIGHT);
        int iHeight = c.getColumnIndex(KEY_HEIGHT);
        int iName = c.getColumnIndex(KEY_NAME);
        int iUsername = c.getColumnIndex(KEY_USERNAME);
        int calc = c.getColumnIndex(KEY_ISCALCULATED);


        User user = new User();
        while (c.moveToNext()) {
            System.out.println(c.getInt(iRow) + " : "+ uid);
            if (c.getInt(iRow) == uid){
            user.setBirth(c.getString(iBirth));
            user.setAct(c.getInt(iAct));
            user.setBptr(c.getInt(iBptr));
            user.setChf(c.getInt(iCHF));
            user.setChl(c.getInt(iCHL));
            user.setLdl(c.getInt(iLDL));
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
            user.setWeight((double)c.getInt(iWeight));
                System.out.println(user.getWeight());
            user.setHeight((double)c.getInt(iHeight));
                System.out.println(user.getChl());
            user.setName(c.getString(iName));
            user.setUsername(c.getString(iUsername));
            user.setCalculated(Boolean.parseBoolean(c.getString(calc)));

            return user;
            }
        }

        return null;
    }

    public long deleteAct(String plan, String activity){

        return ourDatabase.delete(DATABASE_TABLE2,KEY_TITLE+" = '"+plan+"' AND " + KEY_ACTIVITY +"= '"+activity+"';",null);
    }
    public boolean checkUser(String username,String pass){
        String[] columns = new String[] {KEY_ROWID,KEY_USERNAME,KEY_PASSWORD};
        Cursor c = ourDatabase.query(DATABASE_TABLE3, columns, null, null, null, null,
                null);

        boolean exist = false;

        int user = c.getColumnIndex(KEY_USERNAME);
        int password = c.getColumnIndex(KEY_PASSWORD);

        while (c.moveToNext()){
            System.out.println(c.getString(user)+":"+c.getString(password));
            if (username.equals(c.getString(user))){
                if (pass.equals(c.getString(password))){

                    exist = true;
                    return exist;
                }
            }
        }
        return exist;
    }

    public boolean checkUsername(String username){
        String[] columns = new String[] {KEY_ROWID,KEY_USERNAME};
        Cursor c = ourDatabase.query(DATABASE_TABLE3, columns, null, null, null, null,
                null);

        boolean exist = false;

        int user = c.getColumnIndex(KEY_USERNAME);

        while(c.moveToNext()){
            if(username.equals(c.getString(user))){
                exist = true;
            }
        }

        return exist;
    }

    public boolean checkPassword(String password){
        String[] columns = new String[] {KEY_ROWID,KEY_PASSWORD};
        Cursor c = ourDatabase.query(DATABASE_TABLE3, columns, null, null, null, null,
                null);

        boolean exist = false;

        int pass = c.getColumnIndex(KEY_PASSWORD);

        while(c.moveToNext()){
            if(password.equals(c.getString(pass))){
                exist = true;
            }
        }

        return exist;
    }

    public int getUserID(String username){
        String[] columns = new String[] {KEY_ROWID,KEY_USERNAME};
        Cursor c = ourDatabase.query(DATABASE_TABLE3, columns, null, null, null, null,
                null);

        boolean exist = false;
        int id = 0;
        int user = c.getColumnIndex(KEY_USERNAME);
        int userID = c.getColumnIndex(KEY_ROWID);

        while (c.moveToNext()){
            if (username.equals(c.getString(user))){

                id = c.getInt(userID);

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


    public List<Plans> getPlans(int userID){
        String[] columns = new String[] {KEY_USERID,KEY_ROWID,KEY_TITLE,KEY_COMPLETED,KEY_TOTAL,KEY_PLANPROGRESS};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null,
                null);

        List<Plans> titles = new ArrayList<>();
        int user = c.getColumnIndex(KEY_USERID);
        int title = c.getColumnIndex(KEY_TITLE);
        int com = c.getColumnIndex(KEY_COMPLETED);
        int total = c.getColumnIndex(KEY_TOTAL);
        int progress= c.getColumnIndex(KEY_PLANPROGRESS);
       while (c.moveToNext()) {
           System.out.println("id: "+c.getInt(user));
            if (c.getInt(user)==userID){
                Plans plan = new Plans();
                plan.setTitle(c.getString(title));
                System.out.println("dbpl: "+c.getString(title));
                plan.setCompleted(Boolean.parseBoolean(c.getString(com)));
                plan.setTotalWeightLoss(c.getDouble(total));
                plan.setProgress(c.getDouble(progress));

                titles.add(plan);
            }

        }

        return titles;
    }

    public List<String> getTitle(int userID){
        String[] columns = new String[] {KEY_USERID,KEY_ROWID,KEY_TITLE,KEY_COMPLETED};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null,
                null);

        List<String> titles = new ArrayList<>();
        int user = c.getColumnIndex(KEY_USERID);
        int title = c.getColumnIndex(KEY_TITLE);
        int com = c.getColumnIndex(KEY_COMPLETED);
        for (c.moveToFirst();!c.isAfterLast () ;c.moveToNext()) {
            if (!c.getString(com).equals("true") && c.getInt(user)==userID){

                titles.add(c.getString(title));    
            }
            
        }
        return titles;
    }

    public List<List<String>> getActivities(List<String> planName){
        List<List<String>> Plans = new ArrayList<>();
        for (String plan:planName
                ) {
            Cursor c = ourDatabase.rawQuery("SELECT * FROM "+DATABASE_TABLE2+" WHERE  "+KEY_TITLE+" = '"+plan+"';",null);
            List<String> activity = new ArrayList<>();
            List<Double> weight = new ArrayList<>();
            int title = c.getColumnIndex(HeartBeatDB.KEY_ACTIVITY);
            int com = c.getColumnIndex(KEY_COMPLETED);

            while (c.moveToNext()) {

                    String acts = c.getString(title);
                    activity.add(acts);
            }
            Plans.add(activity);

        }

        return Plans;
    }

    public List<Activity> getActivities(String planName){
        List<Activity> Plans = new ArrayList<>();


            Cursor c = ourDatabase.rawQuery("SELECT * FROM "+DATABASE_TABLE2+" WHERE  "+KEY_TITLE+" = '"+planName+"';",null);

            int title = c.getColumnIndex(HeartBeatDB.KEY_ACTIVITY);
            int com = c.getColumnIndex(KEY_COMPLETED);
            int weight = c.getColumnIndex(KEY_WEIGHTLOSS);

            while (c.moveToNext()) {

                    Activity acts = new Activity();
                    acts.setActivities(c.getString(title));
                    acts.setDone(Boolean.parseBoolean(c.getString(com)));
                    acts.setWeightLoss(c.getDouble(weight));
                    Plans.add(acts);

            }
        return Plans;
    }


    public List<Plans> getActivity(List<String> planName){
        List<Plans> Plans = new ArrayList<>();
        Plans myPlan = new Plans();
        for (String plan:planName
                ) {
            Cursor c = ourDatabase.rawQuery("SELECT * FROM "+DATABASE_TABLE2+" WHERE  "+KEY_TITLE+" = '"+plan+"';",null);
            List<Activity> activity = new ArrayList<>();
            List<List<Activity>> acc = new ArrayList<>();
            List<Double> weight = new ArrayList<>();
            myPlan.setTitle(plan);
            Activity act = new Activity();
            int title = c.getColumnIndex(HeartBeatDB.KEY_ACTIVITY);
            int com = c.getColumnIndex(KEY_COMPLETED);
            int weightloss = c.getColumnIndex(KEY_WEIGHTLOSS);

            while (c.moveToNext()) {
                if (!c.getString(com).equals("true")) {
                    String acts = c.getString(title);
                    act.setActivities(acts);
                    act.setWeightLoss(c.getDouble(weightloss));
                    activity.add(act);
                }
            }
            myPlan.setActivities(activity);
            Plans.add(myPlan);

        }

        return Plans;
    }

    public long setInitialPoints(int userID,int hearts,int coins, int time){
        ContentValues con = new ContentValues();
        con.put(KEY_BANKID,userID);
        con.put(KEY_HEARTS,hearts);
        con.put(KEY_COINS,coins);
        con.put(KEY_TOTALTIME,time);
        System.out.println("success");
        return ourDatabase.insert(DATABASE_TABLE5,null,con);
    }

    public long addCoins(int userID,int coins){
        ContentValues con = new ContentValues();
        con.put(KEY_BANKID,userID);
        con.put(KEY_COINS,coins);
        System.out.println("success");
        return ourDatabase.insert(DATABASE_TABLE5,null,con);
    }


    public long updateCoins(int userID,int coins){
        ContentValues con = new ContentValues();
        con.put(KEY_COINS,coins);
        System.out.println("success");
        return ourDatabase.update(DATABASE_TABLE5,con,KEY_BANKID+" = "+userID,null);
    }


    public Bank getPoints(int userID){
        String[] cols = new String[]{KEY_BANKID,KEY_HEARTS,KEY_COINS,KEY_TOTALTIME};
        Cursor c = ourDatabase.query(DATABASE_TABLE5,cols,null,null,null,null,null);
        Bank HBBank = new Bank();
        int hearts= c.getColumnIndex(KEY_HEARTS);
        int coins = c.getColumnIndex(KEY_COINS);
        int time = c.getColumnIndex(KEY_TOTALTIME);
        int id = c.getColumnIndex(KEY_BANKID);
        while (c.moveToNext()){

                HBBank.setHearts(c.getInt(hearts));
                HBBank.setCoins(c.getInt(coins));
                HBBank.setTotaltime(c.getInt(time));

        }
            return HBBank;
    }







}