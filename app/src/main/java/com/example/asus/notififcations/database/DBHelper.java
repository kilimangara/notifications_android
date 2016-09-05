package com.example.asus.notififcations.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.asus.notififcations.model.ModelTask;

/**
 * Created by Asus on 02.09.2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME ="Notifications_database";

    public static final String TASK_TABLE ="tasks_table";

    public static final String TASK_TITLE_COLUMN = "task_title";
    public static final String TASK_DATE_COLUMN = "task_date";
    public static final String TASK_PRIORITY_COLUMN = "task_priority";
    public static final String TASK_STATUS_COLUMN = "task_status";
    public static final String TASK_TIME_STAMP_COLUMN = "task_time_stamp";

    private static final String TASKS_TABLE_CREATE_SCRIPT = "CREATE TABLE "+ TASK_TABLE +" ("+
        BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ TASK_TITLE_COLUMN + " TEXT NOT NULL, "+
        TASK_DATE_COLUMN+ " LONG, "+ TASK_PRIORITY_COLUMN+ " INTEGER, " + TASK_STATUS_COLUMN+ " INTEGER, "+ TASK_TIME_STAMP_COLUMN
            +" LONG);";
    public static final String SELECTION_STATUS = TASK_STATUS_COLUMN + " = ?";
    public static final String SELECTION_TIME_STAMP = TASK_TIME_STAMP_COLUMN + " = ?";
    public static final String SELECTION_LIKE_TITLE = TASK_TITLE_COLUMN + " LIKE ?";

    private DBQueryManager dbQueryManager;
    private  DBUpdateManager dbUpdateManager;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        dbQueryManager = new DBQueryManager(getReadableDatabase());
        dbUpdateManager = new DBUpdateManager(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TASKS_TABLE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+ TASK_TABLE);
        onCreate(db);
    }
    public void saveTask(ModelTask newTask){
        ContentValues newValues = new ContentValues();

        newValues.put(TASK_TITLE_COLUMN, newTask.getTitle());
        newValues.put(TASK_DATE_COLUMN, newTask.getDate());
        newValues.put(TASK_PRIORITY_COLUMN, newTask.getPriority());
        newValues.put(TASK_STATUS_COLUMN, newTask.getStatus());
        newValues.put(TASK_TIME_STAMP_COLUMN, newTask.getTime_stamp());

        getWritableDatabase().insert(TASK_TABLE, null, newValues);

    }

    public DBQueryManager query(){
        return dbQueryManager;
    }
    public DBUpdateManager update(){
        return dbUpdateManager;
    }

    public void removeTask(long timeStamp){
        getWritableDatabase().delete(TASK_TABLE, SELECTION_TIME_STAMP, new String[]{Long.toString(timeStamp)});

    }


}
