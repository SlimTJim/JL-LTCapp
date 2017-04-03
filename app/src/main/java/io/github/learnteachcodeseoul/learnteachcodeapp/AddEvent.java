package io.github.learnteachcodeseoul.learnteachcodeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class AddEvent extends AppCompatActivity {
    EditText eventNameInput;
    EditText dateInput;
    EditText locationInput;
    EditText detailInput;
    Spinner monthSpinner;
    Spinner daySpinner;
    Spinner yearSpinner;
    Spinner startTimeSpinner;
    Spinner endTimeSpinner;

    EventDBHandler eDBHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        eDBHandler = new EventDBHandler(this, null, null, 2);

        eventNameInput = (EditText) findViewById(R.id.eventNameInput);
        locationInput = (EditText) findViewById(R.id.locationInput);
        detailInput = (EditText) findViewById(R.id.detailInput);

        monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        daySpinner = (Spinner) findViewById(R.id.daySpinner);
        yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        startTimeSpinner = (Spinner) findViewById(R.id.startTimeSpinner);
        endTimeSpinner= (Spinner) findViewById(R.id.endTimeSpinner);

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,initDays());
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,initYears());
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,initTime());
        daySpinner.setAdapter(dayAdapter);
        yearSpinner.setAdapter(yearAdapter);
        startTimeSpinner.setAdapter(timeAdapter);
        endTimeSpinner.setAdapter(timeAdapter);



    }

    public void clearName(View view) {
        eventNameInput.setText("");
    }

    public void clearDate(View view) {
        dateInput.setText("");
    }

    public void clearLocation(View view) {
        locationInput.setText("");
    }

    public void clearDetail(View view) {
        detailInput.setText("");
    }


    public void createButtonClicked(View view) {
        Event event = new Event();
        event.setName(eventNameInput.getText().toString());
        event.setDate(extractDate());
        event.setStartTime(startTimeSpinner.getSelectedItem().toString());
        event.setEndTime(endTimeSpinner.getSelectedItem().toString());
        event.setLocation(locationInput.getText().toString());
        event.setDetail(detailInput.getText().toString());
        eDBHandler.addEvent(event);


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void cancelButtonClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public String[] initDays() {
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            if (i<9)
                days[i] = "0"+String.valueOf((Integer) i+1);
            else
                days[i] = String.valueOf((Integer)i + 1);
        }
        return days;
    }

    public String[] initYears() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String [] years ={String.valueOf(year),String.valueOf(year+1)};
        return years;
    }

    public String[] initTime(){
        String[] time = new String[18];
        for (int i = 0; i < 18; i++) {
            time[i] = String.valueOf((Integer)i + 6) + ":00";
        }
        return time;
    }

    public String extractDate(){
        String[] months = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV<","DEC"};
        String month="ERR";

        String selectMonth=monthSpinner.getSelectedItem().toString();
        for (int i = 0;i<12;i++){
            if (months[i].equals(selectMonth)){
                if (i<9)
                    month = "0"+String.valueOf((Integer) i+1);
                else
                    month = String.valueOf((Integer) i+1);
                break;
            }
        }
        String day = daySpinner.getSelectedItem().toString();
        String year = yearSpinner.getSelectedItem().toString();
        return year+"-"+month+"-"+day;
    }

}
