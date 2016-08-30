package com.example.asus.notififcations.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.example.asus.notififcations.fragment.CurrentTaskFragment;
import com.example.asus.notififcations.fragment.DoneTaskFragment;

/**
 * Created by Asus on 25.08.2016.
 */
public class TabAdapter extends FragmentStatePagerAdapter {
    private int numberofTabs;

    public static final int CURREN_TASK_POSITION=0;
    public static final int DONE_TASK_POSITION=1;

    private CurrentTaskFragment currentTaskFragment;
    private DoneTaskFragment doneTaskFragment;

    public TabAdapter(FragmentManager fm, int numberofTabs) {
        super(fm);
        this.numberofTabs=numberofTabs;
        currentTaskFragment=new CurrentTaskFragment();
        doneTaskFragment=new DoneTaskFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return currentTaskFragment;
            case 1:
                return doneTaskFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numberofTabs;
    }
}
