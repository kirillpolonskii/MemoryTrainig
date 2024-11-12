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
    private int decisecond = 0;
    private boolean running;
    private boolean wasRunning;
    AnimatedVectorDrawable pauseResumeAnimationVec;

    public static StopwatchFragment newInstance(String param1, String param2) {
        StopwatchFragment fragment = new StopwatchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
                        v.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
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

    public int getDecisecond() {
        return decisecond;
    }

    public void finishStopwatch(){
        running = false;
    }
}





