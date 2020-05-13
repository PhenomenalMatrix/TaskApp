package com.example.taskapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.App;
import com.example.taskapp.R;
import com.example.taskapp.models.Task;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private TaskAdapter adapter;
    private ArrayList<Task> list = new ArrayList<>();
    Task task;
    int pos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list.addAll(App.getInstance().getDatabase().taskDao().getAll());
        adapter = new TaskAdapter(list);
        recyclerView.setAdapter(adapter);
        loadData();


    }

    private void loadData() {
        App.getInstance().getDatabase().taskDao().getAllLive().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                list.clear();
                list.addAll(tasks);
                adapter.notifyDataSetChanged();
            }
        });
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 100 && resultCode==RESULT_OK && data!=null){
//            task = (Task) data.getSerializableExtra("task");
//            list.add(pos,task);
//            adapter.update(list);
//            adapter.notifyDataSetChanged();
//        }
//    }
}
