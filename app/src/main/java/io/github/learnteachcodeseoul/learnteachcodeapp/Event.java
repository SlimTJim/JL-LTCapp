package io.github.learnteachcodeseoul.learnteachcodeapp;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Event implements Serializable{

    private int id;
    private String name;
    private String date;
    private String startTime;
    private String endTime;
    private String detail;
    private String location;

    public Event(){

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDetail() {
        return detail;
    }

    public String getLocation() {
        return location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
