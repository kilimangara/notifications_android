package com.example.asus.notififcations.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.asus.notififcations.model.ModelTask;

/**
 * Created by Asus on 02.09.2016.
 */
public class DBUpdateManager {

    SQLiteDatabase database;

    DBUpdateManager(SQLiteDatabase database){
        this.database= database;
    }

    public void title(long timeStamp, String title){
        update(DBHelper.TASK_TITLE_COLUMN, timeStamp, title);
    }
    public void date(long timeStamp, long date){
        update(DBHelper.TASK_DATE_COLUMN, timeStamp, date);
    }
    public void priority(long timeStamp, int priority){
        update(DBHelper.TASK_PRIORITY_COLUMN, timeStamp, priority);
    }
    public void status(long timeStamp, int status){
        update(DBHelper.TASK_STATUS_COLUMN, timeStamp, status);
    }

    public void task(ModelTask task){
        title(task.getTime_stamp(), task.getTitle());
        date(task.getTime_stamp(), task.getDate());
        priority(task.getTime_stamp(),task.getPriority());
        status(task.getTime_stamp(), task.getStatus());
    }

    private void update(String column, long key, String value){
        ContentValues contentValues= new ContentValues();

        contentValues.put(column, value);

        database.update(DBHelper.TASK_TABLE, contentValues, DBHelper.TASK_TIME_STAMP_COLUMN + " == " + key, null);
    }
    private void update(String column, long key, long value){
        ContentValues contentValues= new ContentValues();

        contentValues.put(column, value);

        database.update(DBHelper.TASK_TABLE, contentValues, DBHelper.TASK_TIME_STAMP_COLUMN + " == " + key, null);
    }

}
