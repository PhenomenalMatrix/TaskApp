package com.example.taskapp.ui.home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.App;
import com.example.taskapp.FormActivity;
import com.example.taskapp.R;
import com.example.taskapp.models.Task;
import com.example.taskapp.ui.OnitemCickListner;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<Task> list;
    private OnitemCickListner onitemCickListner;

    public TaskAdapter(ArrayList<Task> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));
        holder.setIsRecyclable(true);
        if(position %2== 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#E5E5E5"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnitemCickListner(OnitemCickListner onitemCickListner) {
        this.onitemCickListner = onitemCickListner;
    }

    public void update(ArrayList<Task> Task) {
        list = Task;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private TextView textDesc;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc = itemView.findViewById(R.id.textDescrip);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), FormActivity.class);
                    Task task = new Task();
                    task.setTitle(textTitle.getText().toString());
                    task.setDesc(textDesc.getText().toString());
                    intent.putExtra("ss", task);
                    v.getContext().startActivity(intent);



                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());//Here I have to use v.getContext() istead of just cont.
                    alertDialog.setTitle("Delete file?");
                    alertDialog.setMessage("Are you sure you want to delete the file?");
                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    int pos = list.get(getAdapterPosition()).getId();
                                    App.getInstance().getDatabase().taskDao().deleteByIdList(pos);



                                }
                            });
                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            });
                    alertDialog.show();

                    return false;
                }
            });
        }


        public void onBind(Task task) {
            textTitle.setText(task.getTitle());
            textDesc.setText(task.getDesc());
        }
    }

}
