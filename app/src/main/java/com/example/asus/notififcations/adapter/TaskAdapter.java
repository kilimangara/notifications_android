package com.example.asus.notififcations.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.asus.notififcations.fragment.TaskFragment;
import com.example.asus.notififcations.model.Item;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Asus on 30.08.2016.
 */
public abstract class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Item> list;
    TaskFragment taskFragment;
    public TaskAdapter(TaskFragment taskFragment){
        this.taskFragment=taskFragment;
        list=new ArrayList<>();

    }


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

    public void removeItem(int location){
        if(location >= 0 && location <= getItemCount()-1 ){
            list.remove(location);
            notifyItemRemoved(location);
        }
    }

    public void removeAllItems(){
        list = new ArrayList<>();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView date;
        CircleImageView priority;

        public TaskViewHolder(View itemView, TextView title, TextView date, CircleImageView priority) {
            super(itemView);
            this.title=title;
            this.date=date;
            this.priority=priority;
        }


    }

    public TaskFragment getTaskFragment(){
        return taskFragment;
    }
}
