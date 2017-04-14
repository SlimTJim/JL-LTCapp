package io.github.learnteachcodeseoul.learnteachcodeapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.Locale;

public class DeviceBootReceiver extends BroadcastReceiver {

    Intent alertIntent;
    PendingIntent pendingIntent;
    ArrayList<Event> eventArrayList;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {


                EventDBHandler eDBHandler = new EventDBHandler(context, null, null, 2);
                eventArrayList = eDBHandler.getAllFutureEvents();

            if (!eventArrayList.isEmpty() && isAlertTime(eventArrayList.get(0).getDate())) {
                Calendar alertTime = Calendar.getInstance();
                alertTime.set(Calendar.HOUR_OF_DAY, 12);
                alertTime.set(Calendar.MINUTE, 0);
                alertTime.set(Calendar.SECOND, 0);

                if (alertTime.getTimeInMillis()<= System.currentTimeMillis())
                    return;

                alertIntent = new Intent(context, AlertReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(context, 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                manager.set(AlarmManager.RTC_WAKEUP, alertTime.getTimeInMillis(), pendingIntent);
            }
        }
    }

    private boolean isAlertTime(String date)  {
        DateFormat inputDF = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar currentDay = Calendar.getInstance();
        Calendar past = Calendar.getInstance();
        Calendar eventDay = Calendar.getInstance();
        Date date1 = null;
        try {
            date1 = inputDF.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        past.setTime(date1);
        eventDay.setTime(date1);
        past.add(Calendar.DAY_OF_MONTH,-2);
        if (currentDay.after(past) && currentDay.before(eventDay))
            return true;
        return false;
    }


}
