package io.github.learnteachcodeseoul.learnteachcodeapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class EventDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "eventDB.db";
    private static final String TABLE_EVENTS = "events";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_EVENTNAME= "eventname";
    private static final String COLUMN_DATE= "date";
    private static final String COLUMN_STARTTIME= "starttime";
    private static final String COLUMN_ENDTIME= "endtime";
    private static final String COLUMN_LOCATION= "location";
    private static final String COLUMN_DETAIL= "detail";



    //We need to pass database information along to superclass
    public EventDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_EVENTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EVENTNAME + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_STARTTIME + " TEXT, " +
                COLUMN_ENDTIME + " TEXT, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_DETAIL + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    //Add a new row to the database
    public void addEvent(Event event){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        values.put(COLUMN_EVENTNAME, event.getName());
        values.put(COLUMN_DATE, event.getDate());
        values.put(COLUMN_STARTTIME, event.getStartTime());
        values.put(COLUMN_ENDTIME, event.getEndTime());
        values.put(COLUMN_LOCATION, event.getLocation());
        values.put(COLUMN_DETAIL, event.getDetail());
        db.insert(TABLE_EVENTS, null, values);
        db.close();

    }
    //Delete a event from the database
    public void deleteEvent(Event event){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_EVENTS, COLUMN_ID + " = ?", new String[] { String.valueOf(event.getId()) });
    }

    public ArrayList<Event> getAllFutureEvents() {
        ArrayList<Event> eventArrayList = new ArrayList<Event>();
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS + " WHERE "+COLUMN_DATE+ "> date('now') ORDER BY date("+COLUMN_DATE+") ASC";
        //" ORDER BY date("+COLUMN_DATE+") ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(Integer.parseInt(cursor.getString(0)));
                event.setName(cursor.getString(1));
                event.setDate(cursor.getString(2));
                event.setStartTime(cursor.getString(3));
                event.setEndTime(cursor.getString(4));
                event.setLocation(cursor.getString(5));
                event.setDetail(cursor.getString(6));
                eventArrayList.add(event);
            } while (cursor.moveToNext());
        }
        return eventArrayList;
    }

    public ArrayList<Event> getAllPastEvents() {
        ArrayList<Event> eventArrayList = new ArrayList<Event>();
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS + " WHERE "+COLUMN_DATE+ "< date('now') ORDER BY date("+COLUMN_DATE+") ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(Integer.parseInt(cursor.getString(0)));
                event.setName(cursor.getString(1));
                event.setDate(cursor.getString(2));
                event.setStartTime(cursor.getString(3));
                event.setEndTime(cursor.getString(4));
                event.setLocation(cursor.getString(5));
                event.setDetail(cursor.getString(6));
                eventArrayList.add(event);
            } while (cursor.moveToNext());
        }
        return eventArrayList;
    }
}