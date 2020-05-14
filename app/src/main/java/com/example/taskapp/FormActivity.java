package com.example.taskapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.taskapp.models.Task;
import com.example.taskapp.ui.home.TaskAdapter;

public class FormActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editDesc;
    private Task task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        if (getSupportActionBar() !=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Новая Задача");
        }
        editTitle = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDes);
        Button button = findViewById(R.id.save);
        if (getIntent().getSerializableExtra("ss") != null) {
            task = (Task) getIntent().getSerializableExtra("ss");
            editTitle.setText(task.getTitle());
            editDesc.setText(task.getDesc());
            button.setVisibility(View.GONE);
//          App.getInstance().getDatabase().taskDao().updateSalaryByIdList();
        }
//        if (task != null) {
//            editTitle.setHint("enter text");
//            editDesc.setHint("enter text");
//        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString().trim();
                String desc = editDesc.getText().toString().trim();
                Task task = new Task(title, desc);
                App.getInstance().getDatabase().taskDao().insert(task);
                finish();
            }
        });

    }



//    public void onClick(View view) {
//        String title = editTitle.getText().toString().trim();
//        String desc = editDesc.getText().toString().trim();
//        Task task = new Task(title, desc);
//        App.getInstance().getDatabase().taskDao().insert(task);
//        finish();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("des","des");
    }
}
