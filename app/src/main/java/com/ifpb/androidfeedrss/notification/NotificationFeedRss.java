package com.ifpb.androidfeedrss.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class NotificationFeedRss {

    public static void notificar(Context context, int id, Intent intent, int smallIcon, String contentTitle, String contentText){

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(contentTitle);
        builder.setContentText(contentText);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(smallIcon);
        builder.setAutoCancel(true);

        Notification notification = builder.build();
        manager.notify(id, notification);
    }

}
