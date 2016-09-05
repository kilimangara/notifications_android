package com.example.asus.notififcations.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.notififcations.R;
import com.example.asus.notififcations.adapter.DoneTaskAdapter;
import com.example.asus.notififcations.database.DBHelper;
import com.example.asus.notififcations.model.ModelTask;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoneTaskFragment extends TaskFragment {



    public DoneTaskFragment() {
        // Required empty public constructor
    }

    public interface OnTaskRestoreListener{
        void OnTaskRestore(ModelTask task);
    }
    OnTaskRestoreListener onTaskRestoreListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            onTaskRestoreListener = (OnTaskRestoreListener) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnTaskRestoreListener");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_done_task, container, false);

        adapter = new DoneTaskAdapter(this);

        recycleView = (RecyclerView) rootView.findViewById(R.id.rvDoneTask);

        layoutManager= new LinearLayoutManager(getActivity());

        recycleView.setLayoutManager(layoutManager);

        recycleView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void findTasks(String title) {
        adapter.removeAllItems();
        List<ModelTask> tasks = new ArrayList<>();
        tasks.addAll(activity.dbHelper.query().getTasks( DBHelper.SELECTION_LIKE_TITLE +" AND "
                + DBHelper.SELECTION_STATUS,
                new String[]{"%"+ title +"%",Integer.toString(ModelTask.STATUS_DONE)}, DBHelper.TASK_DATE_COLUMN));
        for(ModelTask task: tasks){
            addTask(task, false);
        }
    }

    @Override
    public void addTaskFromDb() {
        adapter.removeAllItems();
        List<ModelTask> tasks = new ArrayList<>();
        tasks.addAll(activity.dbHelper.query().getTasks(DBHelper.SELECTION_STATUS,
                new String[]{Integer.toString(ModelTask.STATUS_DONE)}, DBHelper.TASK_DATE_COLUMN));
        for(ModelTask task: tasks){
            addTask(task, false);
        }
    }

    @Override
    public void moveTask(ModelTask newTask) {
        if(newTask.getDate() != 0){
            alarmHelper.setAlarm(newTask);
        }
        onTaskRestoreListener.OnTaskRestore(newTask);
    }
}
