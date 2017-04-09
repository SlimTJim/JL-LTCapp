package io.github.learnteachcodeseoul.learnteachcodeapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ShowEventInfo extends AppCompatActivity implements TimeFragment.OnTimeFragmentInteractionListener,
        LocationFragment.OnLocationFragmentInteractionListener{

    Event event;
    TextView eventNameInfo;
    TextView eventDateInfo;
    TextView eventTimeInfo;
    TextView eventDetailInfo;
    TextView eventlocationInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#20B2AA")));
        Bundle data = this.getIntent().getExtras();
        if (data != null)
            event= (Event)data.getSerializable("EventInfo");


        eventNameInfo = (TextView) findViewById(R.id.eventNameInfo);
        eventDateInfo = (TextView) findViewById(R.id.eventDateInfo);
        eventTimeInfo =(TextView) findViewById(R.id.eventTimeInfo);
        eventlocationInfo = (TextView) findViewById(R.id.eventLocationInfo);
        eventDetailInfo = (TextView) findViewById(R.id.eventDetailInfo);





        eventNameInfo.setText(event.getName());
        try {
            eventDateInfo.setText(getDay(event.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        eventTimeInfo.setText(event.getStartTime() + " - " +event.getEndTime());

        eventlocationInfo.setText(event.getLocation());
        eventDetailInfo.setText(event.getDetail());
    }



    @Override
    public void onTimeFragmentInteraction() {
    }
    @Override
    public void onLocationFragmentInteraction(Uri uri) {

    }


    private String getDay(String date) throws ParseException {
        Format formatter = new SimpleDateFormat("MMMM", Locale.US);
        DateFormat inputDF = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        Calendar cal = Calendar.getInstance();
        Date date1 = inputDF.parse(date);
        cal.setTime(date1);
        Integer day = cal.get(Calendar.DAY_OF_MONTH);

        return cal.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.US)+", "+ formatter.format(date1)+" "+day.toString();
    }


}
