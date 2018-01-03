package capstone.heartbeat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by torre on 8/15/2017.
 */

public class Activity {
    public int id;
    public String Activities;
    public double METS;
    public String Intensity;
    public String Equipment;

    public Activity(int id,String Activities,double METS,String Intensity,String Equipment){
        this.id = id;
        this.Activities=Activities;
        this.METS = METS;
        this.Intensity = Intensity;
        this.Equipment = Equipment;
    }

    public Activity(){}
}
