package com.example.asus.notififcations.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import model.Item;

/**
 * Created by Asus on 26.08.2016.
 */
public class CurrentTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Item> list=new ArrayList<>();
    private static final int TYPE_TASK=0;
    private static final int TYPE_SEPARETOR=1;

    public Item getItem(int position){
        return list.get(position);
    }

    public void addItem(Item item){
        list.add(item);
        notifyItemInserted(getItemCount()-1);
    }
    public void addItem(int index, Item item){
        list.add(index, item);
        notifyItemInserted(index);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

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
            return TYPE_SEPARETOR;
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
