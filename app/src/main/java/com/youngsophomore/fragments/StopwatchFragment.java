package com.youngsophomore.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.youngsophomore.R;

import java.util.Locale;

public class StopwatchFragment extends Fragment {
    private static final String DEBUG_TAG = "Gestures";
    //Number of seconds displayed on the stopwatch.
    private int decisecond = 0;
    //Is the stopwatch running?
    private boolean running;
    private boolean wasRunning;
    AnimatedVectorDrawable pauseResumeAnimationVec;

    public static StopwatchFragment newInstance(String param1, String param2) {
        StopwatchFragment fragment = new StopwatchFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
            //seconds = savedInstanceState.getInt("seconds");
            //running = savedInstanceState.getBoolean("running");
            //wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        runTimer(layout);
        ImageButton btnPauseResume = layout.findViewById(R.id.btn_pause_resume);
        btnPauseResume.setBackgroundResource(R.drawable.anim_pause_resume);
        pauseResumeAnimationVec = (AnimatedVectorDrawable) btnPauseResume.getBackground();

        btnPauseResume.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnPauseResume onTouch. Action was DOWN");
                        v.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnPauseResume onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnPauseResume onTouch. Action was UP");
                        v.setElevation(elevPx);
                        if (running){
                            onClickStop();
                            btnPauseResume.setBackgroundResource(R.drawable.anim_pause_resume);
                            pauseResumeAnimationVec = (AnimatedVectorDrawable) btnPauseResume.getBackground();
                            pauseResumeAnimationVec.start();
                        }
                        else{
                            onClickStart();
                            btnPauseResume.setBackgroundResource(R.drawable.anim_resume_pause);
                            pauseResumeAnimationVec = (AnimatedVectorDrawable) btnPauseResume.getBackground();
                            pauseResumeAnimationVec.start();
                        }


                        //btnPauseResume.setBackground(pauseResumeAnimation.getFrame(1));
                        return true;
                    default:
                        return false;
                }
            }
        });
        return layout;
    }

    @Override
    public void onStart(){
        super.onStart();
        onClickStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("decisecond", decisecond);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    private void onClickStart() {
        running = true;
    }
    private void onClickStop() {
        running = false;
    }

    private void runTimer(View view) {
        final TextView tv_stopwatch = view.findViewById(R.id.tv_stopwatch);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = (decisecond / 10) / 60;
                int secs = (decisecond / 10) % 60;
                int decisecs = decisecond % 10;
                String time = String.format(Locale.getDefault(),
                        "%02d:%02d:%02d", minutes, secs, decisecs);
                tv_stopwatch.setText(time);
                if (running) {
                    ++decisecond;
                }
                handler.postDelayed(this, 100);
            }
        });
    }
}





