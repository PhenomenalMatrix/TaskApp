package com.example.taskapp.ui.firestore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.example.taskapp.models.Task;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirestoreAdapter extends RecyclerView.Adapter<FirestoreAdapter.ViewHolderFirestore> {

    private ArrayList<Task> list;

    public FirestoreAdapter(ArrayList<Task> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolderFirestore onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_firestore, parent,false);
        return new ViewHolderFirestore(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFirestore holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderFirestore extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private TextView textDest;

        public ViewHolderFirestore(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
            textDest = itemView.findViewById(R.id.text_descrip);

        }


        public void bind(Task task) {
            textTitle.setText(task.getTitle());
            textDest.setText(task.getDesc());
        }
    }
}
