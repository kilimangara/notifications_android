package com.example.asus.notififcations.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.asus.notififcations.MainActivity;
import com.example.asus.notififcations.R;

import java.util.ArrayList;
import java.util.List;

import com.example.asus.notififcations.Utils;
import com.example.asus.notififcations.model.Item;
import com.example.asus.notififcations.model.ModelTask;

/**
 * Created by Asus on 26.08.2016.
 */
public class CurrentTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String MYLOGS="mylogs";
    List<Item> list=new ArrayList<>();
    private static final int TYPE_TASK=0;
    private static final int TYPE_SEPARATOR=1;

    public Item getItem(int position){
        return list.get(position);
    }

    public void addItem(Item item){
        list.add(item);
        Log.d(MYLOGS, list.toString());
        notifyItemInserted(getItemCount()-1);
    }
    public void addItem(int index, Item item){
        list.add(index, item);
        Log.d(MYLOGS, list.toString());
        notifyItemInserted(index);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType){
            case TYPE_TASK:
                View v= LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.model_task, parent, false);
                TextView title = (TextView) v.findViewById(R.id.tvTaskTitle);
                TextView date= (TextView) v.findViewById(R.id.tvTaskDate);
                Log.d(MYLOGS, "Вывел на экран");
                return new TaskViewHolder(v,title, date);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item =list.get(position);

        if(item.isTask()){
            holder.itemView.setEnabled(true);
            ModelTask task= (ModelTask) item;
            TaskViewHolder taskViewHolder= (TaskViewHolder) holder;

            taskViewHolder.title.setText(task.getTitle());
            Log.d(MYLOGS, "хз че сделал");
            if(task.getDate() !=0){
                taskViewHolder.date.setText(Utils.getFullDate(task.getDate()));
            }

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    @Override
    public int getItemViewType(int position) {
        if(getItem(position).isTask()){
            return TYPE_TASK;
        }
        else{
            return TYPE_SEPARATOR;
        }
    }

    private class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView date;

        public TaskViewHolder(View itemView, TextView title, TextView date) {
            super(itemView);
            this.title=title;
            this.date=date;
        }


    }
}
