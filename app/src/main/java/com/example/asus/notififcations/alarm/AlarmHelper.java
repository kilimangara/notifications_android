package com.example.asus.notififcations.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.asus.notififcations.model.ModelTask;

/**
 * Created by Asus on 05.09.2016.
 */
public class AlarmHelper {
    private static AlarmHelper instance;
    private Context context;
    private AlarmManager alarmManager;

    public static AlarmHelper getInstance(){
        if(instance == null){
            instance = new AlarmHelper();
        }
        return instance;
    }
    public void init(Context context){
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

    }
    public void setAlarm(ModelTask task){
        Intent intent = new Intent(context, AlarmReciever.class);
        intent.putExtra("title", task.getTitle());
        intent.putExtra("timeStamp", task.getTime_stamp());
        intent.putExtra("color", task.getPriorityColor());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), (int) task.getTime_stamp(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, task.getDate(), pendingIntent);
    }

    public void removeAlarm(long timeStamp){
        Intent intent = new Intent(context, AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), (int) timeStamp,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);



    }

}
