package com.example.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.taskapp.models.Task;

public class FormActivity extends AppCompatActivity  {

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
        task = (Task) getIntent().getSerializableExtra("task");
        if (task !=null){
            editTitle.setText(task.getTitle());
            editDesc.setText(task.getDesc());
        }

    }


    public void onClick(View view) {
        String title = editTitle.getText().toString().trim();
        String desc = editDesc.getText().toString().trim();
        if (title.isEmpty()){
            editTitle.setError("Введите задачу");
            YoYo.with(Techniques.Shake)
                    .duration(400)
                    .playOn(editTitle);
            return;
        }
        if (desc.isEmpty()){
            editDesc.setError("Введите описание");
            YoYo.with(Techniques.Shake)
                    .duration(400)
                    .playOn(editDesc);
            return;
        }
        if (task !=null){
            task.setTitle(title);
            task.setDesc(desc);
            App.getInstance().getDatabase().taskDao().update(task);
        }else {
            task = new Task();
            task.setTitle(title);
            task.setDesc(desc);
            App.getInstance().getDatabase().taskDao().insert(task);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("des","des");
    }
}
