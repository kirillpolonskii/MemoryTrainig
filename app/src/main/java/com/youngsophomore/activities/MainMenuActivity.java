package com.youngsophomore.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.MotionEventCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.addisonelliott.segmentedbutton.SegmentedButton;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.youngsophomore.R;
import com.youngsophomore.viewgroups.MyMotionLayout;

import java.io.File;

public class MainMenuActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "Gestures";
    private MyMotionLayout myMotionLayout;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Log.d(DEBUG_TAG, "IN onCreate()");
        init(getApplicationContext());

        myMotionLayout = findViewById(R.id.motion_layout);

        ImageButton btnStats = findViewById(R.id.btn_stats);
        btnStats.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnStats onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnStats onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnStats onTouch. Action was UP. open statistics" +
                                ", R.dimen.btn_stats_elev = " + R.dimen.btn_stats_elev +
                                ", elev = " + elevPx);
                        view.setElevation(elevPx);
                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "btnStats onTouch. Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "btnStats onTouch. Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default:
                        return false;
                }
            }
        });
        ImageButton btnInfo = findViewById(R.id.btn_info);
        btnInfo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnInfo onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnInfo onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_info_elev);
                        Log.d(DEBUG_TAG, "btnInfo onTouch. Action was UP. open info" +
                                ", R.dimen.btn_info_elev = " + R.dimen.btn_info_elev +
                                ", elev = " + elevPx);
                        view.setElevation(elevPx);
                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "btnInfo onTouch. Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "btnInfo onTouch. Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default:
                        return false;
                }
            }
        });
        SegmentedButtonGroup sgBtnGroup = findViewById(R.id.sgbtn_enru);
        sgBtnGroup.setPosition(1, false);
        sgBtnGroup.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(final int position) {
                // Handle stuff here
                Log.d(DEBUG_TAG, "setOnPositionChangedListener: position = " + position);
                //sgBtnGroup.setElevation(0);
            }
        });

        SegmentedButton sgBtnEn = sgBtnGroup.getButton(0);
        sgBtnEn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnEn onTouch. Action was DOWN");
                        sgBtnGroup.setElevation(0);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "sgBtnEn onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_elev);
                        Log.d(DEBUG_TAG, "sgBtnEn onTouch. Action was UP. open statistics" +
                                ", R.dimen.sgbtn_elev = " + R.dimen.sgbtn_elev +
                                ", elev = " + elevPx);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        sgBtnGroup.setElevation(elevPx);

                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "sgBtnEn onTouch. Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "sgBtnEn onTouch. Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default:
                        return false;
                }
            }
        });
        SegmentedButton sgBtnRu = sgBtnGroup.getButton(1);
        sgBtnRu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnRu onTouch. Action was DOWN");
                        sgBtnGroup.setElevation(0);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "sgBtnRu onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_elev);
                        Log.d(DEBUG_TAG, "sgBtnRu onTouch. Action was UP. open statistics" +
                                ", R.dimen.sgbtn_elev = " + R.dimen.sgbtn_elev +
                                ", elev = " + elevPx);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        sgBtnGroup.setElevation(elevPx);

                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "sgBtnRu onTouch. Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "sgBtnRu onTouch. Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    @Override
    public void onResume(){
        myMotionLayout.transitionToState(R.id.base_state);
        super.onResume();
    }

    private void init(Context context){
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        boolean firstLaunch =
                sharedPreferences.getBoolean(getString(R.string.first_launch_key), true);
        if(!firstLaunch){
            return;
        }
        // init for words settings
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.first_launch_key), false);
        String strWordsCollectionsTitles = ",first collection,";
        editor.putString(getString(R.string.words_collections_titles_key),
                strWordsCollectionsTitles);
        String initWordsCollection = getString(R.string.init_words_collection);
        editor.putString(strWordsCollectionsTitles, initWordsCollection);
        //editor.apply();
        // init for phrase settings
        String strPhrasesCollectionsTitles = ",first collection,";
        editor.putString(getString(R.string.phrases_collections_titles_key),
                strPhrasesCollectionsTitles);
        String initPhrasesCollection = getString(R.string.init_phrases_collection);
        editor.putString(strWordsCollectionsTitles, initPhrasesCollection);
        editor.apply();

        // make all necessary directories
        File phrasesDir = new File(getExternalFilesDir(null).getAbsolutePath() + "/phrases");
        if (!phrasesDir.exists() && phrasesDir.mkdir()) {
            Log.d(DEBUG_TAG, "in MainMenuActivity: " + phrasesDir + " created");
        }
        else{
            Log.d(DEBUG_TAG, "in MainMenuActivity: " + phrasesDir + "existed or was NOT created");
        }
        File detailsDir = new File(getExternalFilesDir(null).getAbsolutePath() + "/details");
        if (!detailsDir.exists() && detailsDir.mkdir()) {
            Log.d(DEBUG_TAG, "in MainMenuActivity: " + detailsDir + " created");
        }
        else{
            Log.d(DEBUG_TAG, "in MainMenuActivity: " + detailsDir + "existed or was NOT created");
        }

    }


}