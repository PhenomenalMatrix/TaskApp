package com.example.taskapp.ui.onboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.taskapp.MainActivity;
import com.example.taskapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {


    public BoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textTitle = view.findViewById(R.id.textTitle);
        final LottieAnimationView animationView = view.findViewById(R.id.animation_view);
        Button buttonfinish = view.findViewById(R.id.buttonFinish);
        LinearLayout back =  view.findViewById(R.id.fragment_board);
        int pos = getArguments().getInt("pos");
        switch (pos){
            case 0:
//                imageView.setImageResource(R.drawable.first);
                animationView.setAnimation("fir.json");
                textTitle.setText("Privet");
                back.setBackgroundResource(R.drawable.nightf);
                break;
            case 1:
//                imageView.setImageResource(R.drawable.second);
                animationView.setAnimation("sec.json");
                textTitle.setText("Kak dela ?");
                back.setBackgroundResource(R.drawable.nightt);
                break;
            case 2:
                animationView.setAnimation("third.json");
//                imageView.setImageResource(R.drawable.third);
                textTitle.setText("Che delaesh");
                back.setBackgroundResource(R.drawable.nighttr);
                buttonfinish.setVisibility(View.VISIBLE);
                buttonfinish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveIsShown();
                        startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();
                    }
                });
                break;
        }
    }

    private void saveIsShown(){
        SharedPreferences preferences = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("isShown",true).apply();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("des","des");
    }
}
