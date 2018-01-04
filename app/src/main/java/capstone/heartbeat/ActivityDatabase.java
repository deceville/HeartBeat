package capstone.heartbeat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by torre on 1/2/2018.
 */

public class ActivityDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME="ActivityListDB.sqlite";
    private static final int DATABASE_VERSION=1;
    private static final String ID = "id";
    private static  final String ACTIVITIES ="Activities";
    private static final String METS ="METS";
    private static final String INTENSITY ="Intensity";
    private static final String EQUIPMENT = "Equipment";
    private static final String ACTIVITY_TABLE ="ActivityList";

    public ActivityDatabase(Context context, String name, String storageDirectory, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, storageDirectory, factory, version);
    }

    public ActivityDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public ArrayList<Activity>getActivities(){
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {ActivityDatabase.ACTIVITIES,ActivityDatabase.METS,ActivityDatabase.INTENSITY,ActivityDatabase.EQUIPMENT};
        Cursor cursor = db.query(ActivityDatabase.ACTIVITY_TABLE,columns,null,null,null,null,null);
        ArrayList<Activity> activityList = new ArrayList<>();

        while(cursor.moveToNext()){
            Activity acts = new Activity();
            acts.Activities = cursor.getString(cursor.getColumnIndex(ActivityDatabase.ACTIVITIES));
            acts.METS = cursor.getDouble(cursor.getColumnIndex(ActivityDatabase.METS));
            acts.Intensity = cursor.getString(cursor.getColumnIndex(ActivityDatabase.INTENSITY));
            acts.Equipment = cursor.getString(cursor.getColumnIndex(ActivityDatabase.EQUIPMENT));
            activityList.add(acts);
        }
        return activityList;
    }

    public ArrayList<Activity>getName(){
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {ActivityDatabase.ACTIVITIES,ActivityDatabase.METS,ActivityDatabase.INTENSITY,ActivityDatabase.EQUIPMENT};
        Cursor cursor = db.query(ActivityDatabase.ACTIVITY_TABLE,columns,null,null,null,null,null);
        ArrayList<Activity> activityList = new ArrayList<>();

        while(cursor.moveToNext()){
            Activity acts = new Activity();
            acts.Activities = cursor.getString(cursor.getColumnIndex(ActivityDatabase.ACTIVITIES));
        }
        return activityList;
    }
}
