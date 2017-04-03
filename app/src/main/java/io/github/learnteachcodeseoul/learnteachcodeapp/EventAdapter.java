package io.github.learnteachcodeseoul.learnteachcodeapp;



import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

class EventAdapter extends ArrayAdapter<Event> {
    public EventAdapter(@NonNull Context context, ArrayList<Event> eventArrayList) {
        super(context, R.layout.event_row, eventArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View eventView = myInflater.inflate(R.layout.event_row, parent, false);


        Event singleEvent = getItem(position);
        TextView monthText = (TextView) eventView.findViewById(R.id.monthText);
        TextView dateText = (TextView) eventView.findViewById(R.id.dateText);
        TextView dayText = (TextView) eventView.findViewById(R.id.dayText);
        TextView eventNameText = (TextView) eventView.findViewById(R.id.eventNameText);
        TextView timeText = (TextView) eventView.findViewById(R.id.timeText);
        TextView locationText = (TextView) eventView.findViewById(R.id.locationText);


        String date = singleEvent.getDate();


        eventNameText.setText(singleEvent.getName());

        try {
            monthText.setText(getMonth(date));
            dateText.setText(getDate(date));
            dayText.setText(getDay(date));
        } catch (ParseException e) {
            monthText.setText("ERR");
            dateText.setText("ERR");
            dayText.setText("ERR");
        }
        timeText.setText(singleEvent.getStartTime() + " - " + singleEvent.getEndTime());
        locationText.setText(singleEvent.getLocation());


        return eventView;

    }

    private String getMonth(String date) throws ParseException {
        Format formatter = new SimpleDateFormat("MMM",Locale.US);
        DateFormat inputDF = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        Date date1 = inputDF.parse(date);

        return formatter.format(date1).toUpperCase();
    }

    private String getDate(String date) throws ParseException {
        DateFormat inputDF = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar cal = Calendar.getInstance();
        Date date1 = inputDF.parse(date);
        cal.setTime(date1);
        Integer day = cal.get(Calendar.DAY_OF_MONTH);
        return day.toString();
    }

    private String getDay(String date) throws ParseException {

        DateFormat inputDF = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        Calendar cal = Calendar.getInstance();
        Date date1 = inputDF.parse(date);
        cal.setTime(date1);
        String day = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US);
        return day;
    }


}