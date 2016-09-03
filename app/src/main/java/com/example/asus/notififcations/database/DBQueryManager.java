package com.example.asus.notififcations.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asus.notififcations.model.ModelTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 02.09.2016.
 */
public class DBQueryManager  {
    private SQLiteDatabase database;

    DBQueryManager(SQLiteDatabase database){
        this.database= database;
    }

    public List<ModelTask> getTasks(String selection, String[] selectionArgs, String orderBy){
        List<ModelTask> tasks = new ArrayList<>();

        Cursor c= database.query(DBHelper.TASK_TABLE, null, selection, selectionArgs, null, null, orderBy);

        if(c.moveToFirst()){
            do{
                String title= c.getString(c.getColumnIndex(DBHelper.TASK_TITLE_COLUMN));
                long date= c.getLong(c.getColumnIndex(DBHelper.TASK_DATE_COLUMN));
                int priority = c.getInt(c.getColumnIndex(DBHelper.TASK_PRIORITY_COLUMN));
                int status = c.getInt(c.getColumnIndex(DBHelper.TASK_STATUS_COLUMN));
                long timeStamp= c.getLong(c.getColumnIndex(DBHelper.TASK_TIME_STAMP_COLUMN));

                ModelTask task= new ModelTask(title, date, priority,status, timeStamp);
                tasks.add(task);

            }while(c.moveToNext());
        }
        c.close();
        return tasks;
    }


}
