package com.example.asus.notififcations;

import java.text.SimpleDateFormat;

/**
 * Created by Asus on 26.08.2016.
 */
public class Utils {
    public static String getDate(long date){
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd.MM.yy");
        return dateFormat.format(date);
    }

    public static String getTime(long time){
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm");
        return dateFormat.format(time);
    }

    public static String getFullDate(long date){
        SimpleDateFormat s= new SimpleDateFormat("dd.MM.yy HH:mm");
        return s.format(date);
    }
}
