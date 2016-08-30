package com.example.asus.notififcations.model;

/**
 * Created by Asus on 26.08.2016.
 */
public class ModelTask implements Item {
    private String title;
    private long date;

    public String getTitle() {
        return title;
    }

    public long getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public ModelTask(){

    }

    public ModelTask(String title, long date){
        this.title=title;
        this.date=date;

    }

    @Override
    public boolean isTask() {
        return true;
    }


}
