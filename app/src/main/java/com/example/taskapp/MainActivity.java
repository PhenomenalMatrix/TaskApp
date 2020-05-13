package com.example.taskapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;

import com.example.taskapp.ui.OnitemCickListner;
import com.example.taskapp.ui.onboard.OnBoardActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements OnitemCickListner {

    private AppBarConfiguration mAppBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isShown()){
            startActivity(new Intent(this, OnBoardActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //кнопка с письмом
        //кнопка с письмом
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, FormActivity.class),100);
            }
        });
        //что бы шторка появлялась
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View headerLayout = navigationView.getHeaderView(0);
        ImageView headerImageView = headerLayout.findViewById(R.id.imageView);
        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    private  boolean isShown(){
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        return preferences.getBoolean("isShown",true);

    }



    //меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("des","des");
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_quit:
                SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                preferences.edit().putBoolean("isShown", false).apply();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void isOpened(){
//        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
//        preferences.edit().putBoolean("isOp",true).apply();
//    }
//
//    private  boolean isop(){
//        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
//        return preferences.getBoolean("isOp",false);
//
//    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onItemClick(int pos) {

    }

    @Override
    public void onItemLongClick(int pos) {
        Log.d("onf","pos: ");

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == RESULT_OK && requestCode == 100 && data != null){
////            Task task = (Task) data.getSerializableExtra("task");
////            Log.e("TAG", "title: " + task.getTitle());
////            Log.e("TAG", "title: " + task.getDesc());
//            Fragment fragment =  getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
//            if(fragment!=null){
//                fragment.getChildFragmentManager().getFragments().get(0).onActivityResult(requestCode,resultCode,data);
//            }
//        }
//    }

}
