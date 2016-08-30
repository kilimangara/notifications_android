package com.example.asus.notififcations.fragment;


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
import com.example.asus.notififcations.model.ModelTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentTaskFragment extends Fragment {

    private RecyclerView rvCurrentTasks;
    private RecyclerView.LayoutManager layoutManager;

    private CurrentTaskAdapter adapter;

    public CurrentTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_current_task,container, false);

        adapter= new CurrentTaskAdapter();

        rvCurrentTasks= (RecyclerView) rootView.findViewById(R.id.rvCurrentTasks);

        layoutManager= new LinearLayoutManager(getActivity());

        rvCurrentTasks.setLayoutManager(layoutManager);

        rvCurrentTasks.setAdapter(adapter);


        return rootView;
    }

    public void addTask(ModelTask newTask){
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




    }

}
