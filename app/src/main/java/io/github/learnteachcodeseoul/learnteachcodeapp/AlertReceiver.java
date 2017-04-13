package io.github.learnteachcodeseoul.learnteachcodeapp;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        createNotification(context,"You have an Upcoming Event!","Learn Teach Code","LTC");
    }

    private void createNotification(Context context, String msg, String msgText,String alert ) {
        PendingIntent notificatioIntent = PendingIntent.getActivity(context,0,
                new Intent(context, MainActivity.class),0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_ltc)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(msg)
                .setTicker(alert)
                .setContentText(msgText);

        mBuilder.setContentIntent(notificatioIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1,mBuilder.build());
    }
}
