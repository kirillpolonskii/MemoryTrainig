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
import com.youngsophomore.activities.ColorsTrainingActivity;
import com.youngsophomore.activities.DetailsSettingsActivity;
import com.youngsophomore.activities.DetailsTrainingActivity;
import com.youngsophomore.activities.MahjongSettingsActivity;
import com.youngsophomore.activities.MahjongTrainingActivity;
import com.youngsophomore.activities.PhrasesSettingsActivity;
import com.youngsophomore.activities.PhrasesTrainingActivity;
import com.youngsophomore.activities.ShapesSettingsActivity;
import com.youngsophomore.activities.ShapesTrainingActivity;
import com.youngsophomore.activities.WordsSettingsActivity;
import com.youngsophomore.activities.WordsTrainingActivity;

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

    private boolean viewWasMoved;
    private int idBtnWithEvent = 0;
    Button btnWithEvent = null;
    ImageButton btnSettingsWithEvent = null;

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
        btnInfo = findViewById(R.id.btn_info_main_m);
        btnStats = findViewById(R.id.btn_stats);
        segmentedButtonGroup = findViewById(R.id.sg_btn_gr_switch_lang);

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
            case (MotionEvent.ACTION_MOVE):
                Log.d(DEBUG_TAG, "onInterceptTouchEvent. Action was MOVE");
                return super.onInterceptTouchEvent(motionEvent);
            case (MotionEvent.ACTION_UP):
                Log.d(DEBUG_TAG, "onInterceptTouchEvent. Action was UP");
                return super.onInterceptTouchEvent(motionEvent);
            case (MotionEvent.ACTION_CANCEL):
                Log.d(DEBUG_TAG, "onInterceptTouchEvent. Action was CANCEL");
                return false;
            case (MotionEvent.ACTION_BUTTON_PRESS):
                Log.d(DEBUG_TAG, "onInterceptTouchEvent. Action was PRESSED");
                return onTouchEvent(motionEvent);
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
        MyMotionLayout myMotionLayout = findViewById(R.id.mtn_lt_main_m);
        btnMahjongTraining = findViewById(R.id.btn_mhj_training);
        btnColorsTraining = findViewById(R.id.btn_clr_training);
        btnShapesTraining = findViewById(R.id.btn_shp_training);
        btnWordsTraining = findViewById(R.id.btn_wrd_training);
        btnPhrasesTraining = findViewById(R.id.btn_phr_training);
        btnDetailsTraining = findViewById(R.id.btn_det_training);

        btnMahjongSettings = findViewById(R.id.btn_mhj_settings);
        btnColorsSettings = findViewById(R.id.btn_clr_settings);
        btnShapesSettings = findViewById(R.id.btn_shp_settings);
        btnWordsSettings = findViewById(R.id.btn_wrd_settings);
        btnPhrasesSettings = findViewById(R.id.btn_phr_settings);
        btnDetailsSettings = findViewById(R.id.btn_det_settings);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. Action was DOWN");
                if(touchEventInsideTargetView(btnMahjongTraining, motionEvent)) {
                    btnWithEvent = btnMahjongTraining;
                    idBtnWithEvent = R.id.btn_mhj_training;
                    btnWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnColorsTraining, motionEvent)) {
                    btnWithEvent = btnColorsTraining;
                    idBtnWithEvent = R.id.btn_clr_training;
                    btnWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnShapesTraining, motionEvent)) {
                    btnWithEvent = btnShapesTraining;
                    idBtnWithEvent = R.id.btn_shp_training;
                    btnWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnWordsTraining, motionEvent)) {
                    btnWithEvent = btnWordsTraining;
                    idBtnWithEvent = R.id.btn_wrd_training;
                    btnWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnPhrasesTraining, motionEvent)) {
                    btnWithEvent = btnPhrasesTraining;
                    idBtnWithEvent = R.id.btn_phr_training;
                    btnWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnDetailsTraining, motionEvent)) {
                    btnWithEvent = btnDetailsTraining;
                    idBtnWithEvent = R.id.btn_det_training;
                    btnWithEvent.setElevation(0);
                }

                if(touchEventInsideTargetView(btnMahjongSettings, motionEvent)) {
                    btnSettingsWithEvent = btnMahjongSettings;
                    idBtnWithEvent = R.id.btn_mhj_settings;
                    btnSettingsWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnColorsSettings, motionEvent)) {
                    btnSettingsWithEvent = btnColorsSettings;
                    idBtnWithEvent = R.id.btn_clr_settings;
                    btnSettingsWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnShapesSettings, motionEvent)) {
                    btnSettingsWithEvent = btnShapesSettings;
                    idBtnWithEvent = R.id.btn_shp_settings;
                    btnSettingsWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnWordsSettings, motionEvent)) {
                    btnSettingsWithEvent = btnWordsSettings;
                    idBtnWithEvent = R.id.btn_wrd_settings;
                    btnSettingsWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnPhrasesSettings, motionEvent)) {
                    btnSettingsWithEvent = btnPhrasesSettings;
                    idBtnWithEvent = R.id.btn_phr_settings;
                    btnSettingsWithEvent.setElevation(0);
                }
                if(touchEventInsideTargetView(btnDetailsSettings, motionEvent)) {
                    btnSettingsWithEvent = btnDetailsSettings;
                    idBtnWithEvent = R.id.btn_det_settings;
                    btnSettingsWithEvent.setElevation(0);
                }

                return super.onTouchEvent(motionEvent);
            case (MotionEvent.ACTION_MOVE):
                Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. Action was MOVE");
                viewWasMoved = true;
                if(btnWithEvent != null &&
                        (!eventBtnCorrespondsWithState(btnWithEvent, myMotionLayout.getCurrentState()) ||
                        myMotionLayout.getCurrentState() != -1)){
                    myMotionLayout.transitionToState(R.id.base_state);
                }
                return super.onTouchEvent(motionEvent);
            case (MotionEvent.ACTION_UP):
                Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. Action was UP");
                int elevTrainingPx = getResources().getDimensionPixelSize(R.dimen.btn_training_elev);
                int elevSettingsPx = getResources().getDimensionPixelSize(R.dimen.btn_info_elev);
                if(btnWithEvent != null && idBtnWithEvent == R.id.btn_mhj_training) {
                    Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. mahjong training was touched");
                    btnWithEvent.setElevation(elevTrainingPx);
                    if(!viewWasMoved){
                        Intent intent = new Intent(getContext(), MahjongTrainingActivity.class);
                        getContext().startActivity(intent);
                    }
                }
                if(btnWithEvent != null && idBtnWithEvent == R.id.btn_clr_training) {
                    Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. colors training was touched");
                    btnWithEvent.setElevation(elevTrainingPx);
                    if(!viewWasMoved){
                        Intent intent = new Intent(getContext(), ColorsTrainingActivity.class);
                        getContext().startActivity(intent);
                    }
                }
                if(btnWithEvent != null && idBtnWithEvent == R.id.btn_shp_training) {
                    Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. shapes training was touched");
                    btnWithEvent.setElevation(elevTrainingPx);
                    if(!viewWasMoved){
                        Intent intent = new Intent(getContext(), ShapesTrainingActivity.class);
                        getContext().startActivity(intent);
                    }
                }
                if(btnWithEvent != null && idBtnWithEvent == R.id.btn_wrd_training) {
                    Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. words training was touched");
                    btnWithEvent.setElevation(elevTrainingPx);
                    if(!viewWasMoved){
                        Intent intent = new Intent(getContext(), WordsTrainingActivity.class);
                        getContext().startActivity(intent);
                    }
                }
                if(btnWithEvent != null && idBtnWithEvent == R.id.btn_phr_training) {
                    Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. phrases training was touched");
                    btnWithEvent.setElevation(elevTrainingPx);
                    if(!viewWasMoved){
                        Intent intent = new Intent(getContext(), PhrasesTrainingActivity.class);
                        getContext().startActivity(intent);
                    }
                }
                if(btnWithEvent != null && idBtnWithEvent == R.id.btn_det_training) {
                    Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. details training was touched");
                    btnWithEvent.setElevation(elevTrainingPx);
                    if(!viewWasMoved){
                        Intent intent = new Intent(getContext(), DetailsTrainingActivity.class);
                        getContext().startActivity(intent);
                    }
                }

                if(idBtnWithEvent == R.id.btn_mhj_settings) {
                    Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. mahjong settings was clicked");
                    btnSettingsWithEvent.setElevation(elevSettingsPx);
                    Intent intent = new Intent(getContext(), MahjongSettingsActivity.class);
                    getContext().startActivity(intent);
                }
                if(idBtnWithEvent == R.id.btn_clr_settings) {
                    Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. colors settings was clicked");
                    btnSettingsWithEvent.setElevation(elevSettingsPx);
                    Intent intent = new Intent(getContext(), ColorsSettingsActivity.class);
                    getContext().startActivity(intent);
                }
                if(idBtnWithEvent == R.id.btn_shp_settings) {
                    Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. shapes settings was clicked");
                    btnSettingsWithEvent.setElevation(elevSettingsPx);
                    Intent intent = new Intent(getContext(), ShapesSettingsActivity.class);
                    getContext().startActivity(intent);
                }
                if(idBtnWithEvent == R.id.btn_wrd_settings) {
                    Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. words settings was clicked");
                    btnSettingsWithEvent.setElevation(elevSettingsPx);
                    Intent intent = new Intent(getContext(), WordsSettingsActivity.class);
                    getContext().startActivity(intent);
                }
                if(idBtnWithEvent == R.id.btn_phr_settings) {
                    Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. phrases settings was clicked");
                    btnSettingsWithEvent.setElevation(elevSettingsPx);
                    Intent intent = new Intent(getContext(), PhrasesSettingsActivity.class);
                    getContext().startActivity(intent);
                }
                if(idBtnWithEvent == R.id.btn_det_settings) {
                    Log.d(DEBUG_TAG, "onTouchEvent in MyMotionLayout. details settings was clicked");
                    btnSettingsWithEvent.setElevation(elevSettingsPx);
                    Intent intent = new Intent(getContext(), DetailsSettingsActivity.class);
                    getContext().startActivity(intent);
                }
                btnWithEvent = null;
                idBtnWithEvent = 0;
                viewWasMoved = false;
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
                || (curState == -1);
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

