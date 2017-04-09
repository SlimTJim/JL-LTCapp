package io.github.learnteachcodeseoul.learnteachcodeapp;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class PastEventList extends AppCompatActivity {

    EventDBHandler eDBHandler;
    ArrayList<Event> eventArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_event_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#20B2AA")));



        eDBHandler = new EventDBHandler(this,null,null,2);

        eventArrayList = eDBHandler.getAllPastEvents();
        final ArrayAdapter eventAdapter = new EventAdapter(this, eventArrayList);
        final ListView eventListView = (ListView) findViewById(R.id.pastEventList);
        eventListView.setAdapter(eventAdapter);

        eventListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        final Event event = (Event) parent.getItemAtPosition(position);
                        AlertDialog.Builder adb=new AlertDialog.Builder(PastEventList.this);
                        adb.setTitle("Delete?");
                        adb.setMessage("Are you sure you want to delete " + event.getName());
                        adb.setNegativeButton("Cancel", null);
                        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                eDBHandler.deleteEvent(event);
                                eventAdapter.remove(event);
                            }});
                        adb.show();
                        return true;
                    }
                });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition  (R.anim.right_slide_in,R.anim.fade_out);
            return true;
        }
        return false;
    }

}
