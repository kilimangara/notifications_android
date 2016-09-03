package com.example.asus.notififcations.model;

import com.example.asus.notififcations.R;

import java.util.Date;

/**
 * Created by Asus on 26.08.2016.
 */
public class ModelTask implements Item {

    public static final int PRIORITY_LOW=0;
    public static final int PRIORITY_NORMAL=1;
    public static final int PRIORITY_HIGH=2;

    public static final String[] PRIORITY_LEVELS = {"Low priority", "Normal priority", "High priority"};

    public static final int STATUS_OVEDUE=0;
    public static final int STATUS_CURRENT=1;
    public static final int STATUS_DONE=2;


    private String title;
    private long date;
    private int priority;
    private int status;
    private long time_stamp;

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

    public long getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }

    public ModelTask(){
        this.status= -1;
        this.time_stamp = new Date().getTime();

    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPriority() {

        return priority;
    }

    public int getStatus() {
        return status;
    }

    public ModelTask(String title, long date, int priority, int status, long time_stamp){
        this.title=title;
        this.date=date;
        this.priority=priority;
        this.status=status;
        this.time_stamp=time_stamp;

    }


    public int getPriorityColor(){
        switch(getPriority()){
            case PRIORITY_LOW:
                if(getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVEDUE){
                    return R.color.prirority_low;
                }
                else{
                    return R.color.prirority_low_selected;
                }
            case PRIORITY_NORMAL:
                if(getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVEDUE){
                    return R.color.prirority_normal;
                }
                else{
                    return R.color.prirority_normal_selected;
                }
            case PRIORITY_HIGH:
                if(getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVEDUE){
                    return R.color.prirority_high;
                }
                else{
                    return R.color.prirority_high_selected;
                }
            default: return 0;
        }
    }

    @Override
    public boolean isTask() {
        return true;
    }


}
