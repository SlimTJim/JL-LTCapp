package io.github.learnteachcodeseoul.learnteachcodeapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EventDBHandler eDBHandler;
    ArrayList<Event> eventArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eDBHandler = new EventDBHandler(this,null,null,2);
        eventArrayList = eDBHandler.getAllFutureEvents();
        final ArrayAdapter eventAdapter = new EventAdapter(this, eventArrayList);
        final ListView eventListView = (ListView) findViewById(R.id.eventList);
        eventListView.setAdapter(eventAdapter);

        setNotification();

        eventListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Event event = (Event) parent.getItemAtPosition(position);
                        Intent intent= new Intent(MainActivity.this,ShowEventInfo.class);
                        intent.putExtra("EventInfo",(Serializable) event);
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_slide_in,R.anim.fade_out);
                    }
                }
        );

        eventListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        final Event event = (Event) parent.getItemAtPosition(position);
                        AlertDialog.Builder adb=new AlertDialog.Builder(MainActivity.this);
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


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddEvent.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_past_events) {
            Intent i = new Intent(this,PastEventList.class);
            startActivity(i);
            overridePendingTransition(R.anim.right_slide_in_drawer,R.anim.fade_out_drawer);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setAlert(){
//        long alertTime=new GregorianCalendar().getTimeInMillis()+1000;

        Calendar alertTime = Calendar.getInstance();
        alertTime.setTimeInMillis(System.currentTimeMillis());
        alertTime.set(Calendar.HOUR_OF_DAY, 20);
        alertTime.set(Calendar.MINUTE, 21);
        alertTime.set(Calendar.SECOND, 0);

        if (alertTime.getTimeInMillis()<= System.currentTimeMillis())
            return;

        Intent alertIntent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,alertIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,alertTime.getTimeInMillis(), pendingIntent);
    }

    private boolean isAlertTime(String date) throws ParseException {
        Format formatter = new SimpleDateFormat("MMMM", Locale.US);
        DateFormat inputDF = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeInMillis(System.currentTimeMillis());
        Calendar past = Calendar.getInstance();
        Calendar eventDay = Calendar.getInstance();
        Date date1 = inputDF.parse(date);
        past.setTime(date1);
        eventDay.setTime(date1);
        past.add(Calendar.DAY_OF_MONTH,-2);

        if (currentTime.after(past) && currentTime.before(eventDay))
            return true;
        return false;
    }

    private void setNotification(){
        try {
            if (!eventArrayList.isEmpty() && isAlertTime(eventArrayList.get(0).getDate()))
                setAlert();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}
