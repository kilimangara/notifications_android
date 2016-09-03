package com.example.asus.notififcations.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.asus.notififcations.MainActivity;
import com.example.asus.notififcations.adapter.CurrentTaskAdapter;
import com.example.asus.notififcations.adapter.TaskAdapter;
import com.example.asus.notififcations.model.ModelTask;

/**
 * Created by Asus on 30.08.2016.
 */
public abstract class TaskFragment extends Fragment {
    protected RecyclerView recycleView;
    protected RecyclerView.LayoutManager layoutManager;

    protected TaskAdapter adapter;

    public MainActivity activity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getActivity() != null){
            activity = (MainActivity) getActivity();
        }

        addTaskFromDb();
    }

    public void addTask(ModelTask newTask, boolean saveToDb){
        int position= -1;

        for(int i=0;i< adapter.getItemCount();i++) {
            if (adapter.getItem(i).isTask()) {
                ModelTask task = (ModelTask) adapter.getItem(i);
                if (newTask.getDate() < task.getDate()) {
                    position = i;
                    break;
                }
            }
        }

        if (position != -1) {
            adapter.addItem(position, newTask);
        }else{
            adapter.addItem(newTask);
        }

        if(saveToDb){
            activity.dbHelper.saveTask(newTask);
        }
    }

    public abstract void addTaskFromDb();

    public abstract void moveTask(ModelTask newTask);

}
