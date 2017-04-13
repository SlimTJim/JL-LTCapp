package io.github.learnteachcodeseoul.learnteachcodeapp;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Locale;

public class AddEvent extends AppCompatActivity {

    Calendar myCalendar;
    Button pickDateButton;
    TextView dateText;
    EditText eventNameInput;
    EditText locationInput;
    EditText detailInput;
    Spinner startTimeSpinner;
    Spinner endTimeSpinner;

    EventDBHandler eDBHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        eDBHandler = new EventDBHandler(this, null, null, 2);
        myCalendar = Calendar.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pickDateButton = (Button) findViewById(R.id.pickDateButton);
        eventNameInput = (EditText) findViewById(R.id.eventNameInput);
        locationInput = (EditText) findViewById(R.id.locationInput);
        detailInput = (EditText) findViewById(R.id.detailInput);
        startTimeSpinner = (Spinner) findViewById(R.id.startTimeSpinner);
        endTimeSpinner = (Spinner) findViewById(R.id.endTimeSpinner);
        dateText=(TextView) findViewById(R.id.dateText);
        dateText.setText("");

        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, initTime());
        startTimeSpinner.setAdapter(timeAdapter);
        endTimeSpinner.setAdapter(timeAdapter);




       final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddEvent.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

    }

        private void updateLabel() {
            String myFormat = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            dateText.setText(sdf.format(myCalendar.getTime()));
        }


    public void createButtonClicked(View view) {
        Event event = new Event();
        event.setName(eventNameInput.getText().toString());
        event.setDate(dateText.getText().toString());
        event.setStartTime(startTimeSpinner.getSelectedItem().toString());
        event.setEndTime(endTimeSpinner.getSelectedItem().toString());
        event.setLocation(locationInput.getText().toString());
        event.setDetail(detailInput.getText().toString());

        if (dateText.getText().toString().equals("")){
            Toast.makeText(AddEvent.this, "Please pick a date!", Toast.LENGTH_SHORT).show();
        }else {
            eDBHandler.addEvent(event);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }


    public String[] initTime(){
        String[] time = new String[18];
        for (int i = 0; i < 18; i++) {
            time[i] = String.valueOf((Integer)i + 6) + ":00";
        }
        return time;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_slide_in,R.anim.fade_out);
        return true;
    }

}
