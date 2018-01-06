package capstone.heartbeat.controllers;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by Lenevo on 1/5/2018.
 */

public class RecordCursorAdapter extends CursorAdapter {
    public RecordCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    public RecordCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public RecordCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
