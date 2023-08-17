package com.youngsophomore.viewgroups;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.youngsophomore.R;
import com.youngsophomore.activities.ColorsSettingsActivity;
import com.youngsophomore.activities.MahjongSettingsActivity;
import com.youngsophomore.activities.ShapesSettingsActivity;

public class MyMotionLayout extends MotionLayout implements MotionLayout.TransitionListener {
    private static final String DEBUG_TAG = "Gestures";

    private ImageButton btnInfo;
    private SegmentedButtonGroup segmentedButtonGroup;
    private ImageButton btnStats;
    private Button btnMahjongTraining;
    private Button btnColorsTraining;
    private Button btnShapesTraining;
    private Button btnWordsTraining;
    private Button btnPhrasesTraining;
    private Button btnDetailsTraining;
    private ImageButton btnMahjongSettings;
    private ImageButton btnColorsSettings;
    private ImageButton btnShapesSettings;
    private ImageButton btnWordsSettings;
    private ImageButton btnPhrasesSettings;
    private ImageButton btnDetailsSettings;
    private final int idBtnMahjongTraining = R.id.btn_mahjong_training;
    private final int idBtnColorsTraining = R.id.btn_colors_training;
    private final int idBtnShapesTraining = R.id.btn_shapes_training;
    private final int idBtnWordsTraining = R.id.btn_words_training;
    private final int idBtnPhrasesTraining = R.id.btn_phrases_training;
    private final int idBtnDetailsTraining = R.id.btn_details_training;
    private final int idBtnMahjongSettings = R.id.btn_mahjong_settings;
    private final int idBtnColorsSettings = R.id.btn_colors_settings;
    private final int idBtnShapesSettings = R.id.btn_shapes_settings;
    private final int idBtnWordsSettings = R.id.btn_words_settings;
    private final int idBtnPhrasesSettings = R.id.btn_phrases_settings;
    private final int idBtnDetailsSettings = R.id.btn_details_settings;

    private boolean viewWasMoved;
    private int idBtnWithEvent = 0;
    Button btnWithEvent;
    ImageButton btnSettingsWithEvent;

    public MyMotionLayout(@NonNull Context context) {
        super(context);
        Log.d(DEBUG_TAG, "In MyMotionLayout(Context context)");
    }

    public MyMotionLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(DEBUG_TAG, "In MyMotionLayout(Context context, AttributeSet attrs)");
    }

    public MyMotionLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(DEBUG_TAG, "In MyMotionLayout(Context context, AttributeSet attrs, defStyleAttr)");
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent){
        int action = motionEvent.getAction();
        btnInfo = findViewById(R.id.btn_info);
        btnStats = findViewById(R.id.btn_stats);
        segmentedButtonGroup = findViewById(R.id.sgbtn_enru);
        btnMahjongSettings = findViewById(R.id.btn_mahjong_settings);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(DEBUG_TAG, "onInterceptTouchEvent. Action was DOWN");
                if(btnStats != null && touchEventInsideTargetView(btnStats, motionEvent) ||
                    btnInfo != null && touchEventInsideTargetView(btnInfo, motionEvent) ||
                    segmentedButtonGroup != null && touchEventInsideTargetView(segmentedButtonGroup, motionEvent)){
                    Log.d(DEBUG_TAG, "onInterceptTouchEvent. Action was DOWN. return false");

                    return false;

                }
                else{
                    Log.d(DEBUG_TAG, "onInterceptTouchEvent. Event will be passed further");
                }
                return super.onTouchEvent(motionEvent);
                //return false;
            case (MotionEvent.ACTION_MOVE):
                Log.d(DEBUG_TAG, "onInterceptTouchEvent. Action was MOVE");
                return super.onInterceptTouchEvent(motionEvent);
                //return false;
            case (MotionEvent.ACTION_UP):
                Log.d(DEBUG_TAG, "onInterceptTouchEvent. Action was UP");
                return super.onInterceptTouchEvent(motionEvent);
                //return false;
            case (MotionEvent.ACTION_CANCEL):
                Log.d(DEBUG_TAG, "onInterceptTouchEvent. Action was CANCEL");
                return false;
            case (MotionEvent.ACTION_BUTTON_PRESS):
                Log.d(DEBUG_TAG, "onInterceptTouchEvent. Action was PRESSED");
                return onTouchEvent(motionEvent);
            //return true;
            case (MotionEvent.ACTION_OUTSIDE):
                Log.d(DEBUG_TAG, "onInterceptTouchEvent. Movement occurred outside bounds " +
                        "of current screen element");
                return false;
            default:
                return false;
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        int action = motionEvent.getAction();
        MyMotionLayout myMotionLayout = findViewById(R.id.motion_layout);
        btnMahjongTraining = findViewById(R.id.btn_mahjong_training);
        btnColorsTraining = findViewById(R.id.btn_colors_training);
        btnShapesTraining = findViewById(R.id.btn_shapes_training);
        btnWordsTraining = findViewById(R.id.btn_words_training);
        btnPhrasesTraining = findViewById(R.id.btn_phrases_training);
        btnDetailsTraining = findViewById(R.id.btn_details_training);

        btnMahjongSettings = findViewById(R.id.btn_mahjong_settings);
        btnColorsSettings = findViewById(R.id.btn_colors_settings);
        btnShapesSettings = findViewById(R.id.btn_shapes_settings);
        btnWordsSettings = findViewById(R.id.btn_words_settings);
        btnPhrasesSettings = findViewById(R.id.btn_phrases_settings);
        btnDetailsSettings = findViewById(R.id.btn_details_settings);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. Action was DOWN");
                if(touchEventInsideTargetView(btnMahjongTraining, motionEvent)) {
                    btnWithEvent = btnMahjongTraining;
                    idBtnWithEvent = idBtnMahjongTraining;
                    btnWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnColorsTraining, motionEvent)) {
                    btnWithEvent = btnColorsTraining;
                    idBtnWithEvent = R.id.btn_colors_training;
                    btnWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnShapesTraining, motionEvent)) {
                    btnWithEvent = btnShapesTraining;
                    idBtnWithEvent = R.id.btn_shapes_training;
                    btnWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnWordsTraining, motionEvent)) {
                    btnWithEvent = btnWordsTraining;
                    idBtnWithEvent = R.id.btn_words_training;
                    btnWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnPhrasesTraining, motionEvent)) {
                    btnWithEvent = btnPhrasesTraining;
                    idBtnWithEvent = R.id.btn_phrases_training;
                    btnWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnDetailsTraining, motionEvent)) {
                    btnWithEvent = btnDetailsTraining;
                    idBtnWithEvent = R.id.btn_details_training;
                    btnWithEvent.setElevation(0);
                }

                if(touchEventInsideTargetView(btnMahjongSettings, motionEvent)) {
                    btnSettingsWithEvent = btnMahjongSettings;
                    idBtnWithEvent = R.id.btn_mahjong_settings;
                    btnSettingsWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnColorsSettings, motionEvent)) {
                    btnSettingsWithEvent = btnColorsSettings;
                    idBtnWithEvent = R.id.btn_colors_settings;
                    btnSettingsWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnShapesSettings, motionEvent)) {
                    btnSettingsWithEvent = btnShapesSettings;
                    idBtnWithEvent = R.id.btn_shapes_settings;
                    btnSettingsWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnWordsSettings, motionEvent)) {
                    btnSettingsWithEvent = btnWordsSettings;
                    idBtnWithEvent = R.id.btn_words_settings;
                    btnSettingsWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnPhrasesSettings, motionEvent)) {
                    btnSettingsWithEvent = btnPhrasesSettings;
                    idBtnWithEvent = R.id.btn_phrases_settings;
                    btnSettingsWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnDetailsSettings, motionEvent)) {
                    btnSettingsWithEvent = btnDetailsSettings;
                    idBtnWithEvent = R.id.btn_details_settings;
                    btnSettingsWithEvent.setElevation(0);
                }

                return super.onTouchEvent(motionEvent);
            case (MotionEvent.ACTION_MOVE):
                Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. Action was MOVE");
                viewWasMoved = true;
                if(!eventBtnCorrespondsWithState(btnWithEvent, myMotionLayout.getCurrentState()) ||
                        myMotionLayout.getCurrentState() != -1){ // попробовать здесь &&
                    myMotionLayout.transitionToState(R.id.base_state);
                }
                return super.onTouchEvent(motionEvent);
            case (MotionEvent.ACTION_UP):
                Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. Action was UP");
                if(!viewWasMoved){
                    int elevSettingsPx = getResources().getDimensionPixelSize(R.dimen.btn_info_elev);
                    if(idBtnWithEvent == idBtnMahjongTraining) {
                        Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. mahjong training was clicked");

                    }
                    if(idBtnWithEvent == R.id.btn_colors_training) {
                        Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. colors training was clicked");
                    }
                    if(idBtnWithEvent == R.id.btn_shapes_training) {
                        Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. shapes training was clicked");
                    }
                    if(idBtnWithEvent == R.id.btn_words_training) {
                        Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. words training was clicked");
                    }
                    if(idBtnWithEvent == R.id.btn_phrases_training) {
                        Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. phrases training was clicked");
                    }
                    if(idBtnWithEvent == R.id.btn_details_training) {
                        Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. details training was clicked");
                    }

                    if(idBtnWithEvent == R.id.btn_mahjong_settings) {
                        Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. mahjong settings was clicked");
                        btnSettingsWithEvent.setElevation(elevSettingsPx);
                        Intent intent = new Intent(getContext(), MahjongSettingsActivity.class);
                        getContext().startActivity(intent);
                    }
                    if(idBtnWithEvent == R.id.btn_colors_settings) {
                        Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. colors settings was clicked");
                        btnSettingsWithEvent.setElevation(elevSettingsPx);
                        Intent intent = new Intent(getContext(), ColorsSettingsActivity.class);
                        getContext().startActivity(intent);
                    }
                    if(idBtnWithEvent == R.id.btn_shapes_settings) {
                        Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. shapes settings was clicked");
                        btnSettingsWithEvent.setElevation(elevSettingsPx);
                        Intent intent = new Intent(getContext(), ShapesSettingsActivity.class);
                        getContext().startActivity(intent);
                    }
                    if(idBtnWithEvent == R.id.btn_words_settings) {
                        Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. words settings was clicked");
                        btnSettingsWithEvent.setElevation(elevSettingsPx);
                    }
                    if(idBtnWithEvent == R.id.btn_phrases_settings) {
                        Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. phrases settings was clicked");
                        btnSettingsWithEvent.setElevation(elevSettingsPx);
                    }
                    if(idBtnWithEvent == R.id.btn_details_settings) {
                        Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. details settings was clicked");
                        btnSettingsWithEvent.setElevation(elevSettingsPx);
                    }

                }
                viewWasMoved = false;
                int elevTrainingPx = getResources().getDimensionPixelSize(R.dimen.btn_training_elev);
                btnWithEvent.setElevation(elevTrainingPx);

                return super.onTouchEvent(motionEvent);
            case (MotionEvent.ACTION_CANCEL):
                Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. Action was CANCEL");
                return false;
            case (MotionEvent.ACTION_OUTSIDE):
                Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. Movement occurred outside bounds " +
                        "of current screen element");
                return false;
            default:
                return false;
        }
    }

    private boolean eventBtnCorrespondsWithState(Button eventBtn, int curState){
        return (eventBtn == btnMahjongTraining && curState == R.id.mahjong_end) ||
                (eventBtn == btnColorsTraining && curState == R.id.colors_end) ||
                (eventBtn == btnShapesTraining && curState == R.id.shapes_end) ||
                (eventBtn == btnWordsTraining && curState == R.id.words_end) ||
                (eventBtn == btnPhrasesTraining && curState == R.id.phrases_end) ||
                (eventBtn == btnDetailsTraining && curState == R.id.details_end)
                || (curState == -1)
                ;
    }


    private boolean touchEventInsideTargetView(View v, MotionEvent ev) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];
        float evX = ev.getRawX();
        float evY = ev.getRawY();
        int viewWidth = v.getWidth();
        int viewHeight = v.getHeight();
//        Log.d(DEBUG_TAG, "touchEventInsideTargetView -- view: " + Arrays.toString(location) +
//                                v.getWidth() + " " + v.getHeight());
//        Log.d(DEBUG_TAG, "touchEventInsideTargetView -- event: " +
//                                ev.getX() + " " + ev.getY() + " " + ev.getRawX() + " " + ev.getRawY());
        return evX >= viewX && evX <= viewX + viewWidth &&
                evY >= viewY && evY <= viewY + viewHeight;

    }


    @Override
    public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
        Log.d(DEBUG_TAG, "onTransitionStarted.");
    }

    @Override
    public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
        Log.d(DEBUG_TAG, "onTransitionChange.");
    }

    @Override
    public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
        Log.d(DEBUG_TAG, "onTransitionCompleted. Is state == mahjong_end" + (currentId == R.id.mahjong_end));
    }

    @Override
    public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {
        Log.d(DEBUG_TAG, "onTransitionTrigger.");
    }
}

