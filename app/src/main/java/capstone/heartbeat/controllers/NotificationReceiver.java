package capstone.heartbeat.controllers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import capstone.heartbeat.MainActivity;
import capstone.heartbeat.R;

/**
 * Created by Lenevo on 2/10/2018.
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeating_intent = new Intent(context, MainActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_heartbeat_red);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_heartbeat_white)
                .setLargeIcon(bitmap)
                .setContentTitle("Heart Beat Daily Reminder")
                .setContentText("Check on your today's plans and create new plans for tomorrow!")
                .setAutoCancel(true);

        notificationManager.notify(100, builder.build());
    }
}
