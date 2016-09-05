package com.example.asus.notififcations.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.notififcations.R;
import com.example.asus.notififcations.adapter.CurrentTaskAdapter;
import com.example.asus.notififcations.database.DBHelper;
import com.example.asus.notififcations.model.ModelTask;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentTaskFragment extends TaskFragment {

    public interface OnTaskDoneListener{
        void onTaskDone(ModelTask task);
    }
    OnTaskDoneListener onTaskDoneListener;
    public CurrentTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            onTaskDoneListener = (OnTaskDoneListener) activity;
        }
        catch(ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnTaskDonListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_current_task,container, false);

        adapter= new CurrentTaskAdapter(this);

        recycleView = (RecyclerView) rootView.findViewById(R.id.rvCurrentTasks);

        layoutManager= new LinearLayoutManager(getActivity());

        recycleView.setLayoutManager(layoutManager);

        recycleView.setAdapter(adapter);


        return rootView;
    }


    @Override
    public void findTasks(String title) {
        adapter.removeAllItems();
        List<ModelTask> tasks = new ArrayList<>();


        tasks.addAll(activity.dbHelper.query().getTasks(DBHelper.SELECTION_LIKE_TITLE + " AND "
                + DBHelper.SELECTION_STATUS + " OR " +
                DBHelper.SELECTION_STATUS, new String[]{"%"+ title +"%",Integer.toString(ModelTask.STATUS_CURRENT),
                Integer.toString(ModelTask.STATUS_OVEDUE)}, DBHelper.TASK_DATE_COLUMN));
        for(ModelTask task: tasks){
            addTask(task, false);
        }
    }

    @Override
    public void addTaskFromDb() {
        adapter.removeAllItems();
        List<ModelTask> tasks = new ArrayList<>();
        tasks.addAll(activity.dbHelper.query().getTasks(DBHelper.SELECTION_STATUS + " OR "+
                DBHelper.SELECTION_STATUS, new String[]{Integer.toString(ModelTask.STATUS_CURRENT),
                Integer.toString(ModelTask.STATUS_OVEDUE)}, DBHelper.TASK_DATE_COLUMN));
        for(ModelTask task: tasks){
            addTask(task, false);
        }

    }

    @Override
    public void moveTask(ModelTask newTask) {
        alarmHelper.removeAlarm(newTask.getTime_stamp());
        onTaskDoneListener.onTaskDone(newTask);
    }
}
