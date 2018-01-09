package capstone.heartbeat.controllers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lenevo on 1/9/2018.
 */

public class PlansDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="ActivityListDB.sqlite";
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_TABLE ="Plans";
    private static final String DATABASE_TABLE2 ="PlanActivities";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_TITLE ="title";
    public static final String KEY_DATE = "date";
    public static final String KEY_FREETIME = "free_time";
    public static final String KEY_MINUTES = "minutes";
    public static final String KEY_CALORIES = "calories";
    private static final String KEY_ACTIVITY = "activities";




    public PlansDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL  ("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TITLE
                + " TEXT NOT NULL, " + KEY_DATE + " TEXT NOT NULL, "+KEY_CALORIES
                + " NUMERIC NOT NULL," + KEY_MINUTES + "INTEGER NOT NULL, " + KEY_FREETIME
                + "INTEGER NOT NULL)");

        db.execSQL  ("CREATE TABLE " + DATABASE_TABLE2 + " (" + KEY_ROWID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TITLE
                + " TEXT NOT NULL, " + KEY_ACTIVITY + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }
}
