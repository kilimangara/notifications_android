package com.example.asus.notififcations;

import android.app.Application;

/**
 * Created by Asus on 05.09.2016.
 */
public class MyApplication extends Application {
    private static boolean isVisible;

    public static boolean isActivityVisible(){
        return isVisible;
    }

    public static void activityResumed(){
        isVisible = true;
    }

    public static void activityPaused(){
        isVisible = false;
    }

}
