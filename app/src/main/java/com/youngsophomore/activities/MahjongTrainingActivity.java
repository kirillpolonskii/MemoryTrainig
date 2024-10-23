package com.youngsophomore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.youngsophomore.R;
import com.youngsophomore.fragments.StopwatchFragment;
import com.youngsophomore.helpers.PrepHelper;
import com.youngsophomore.helpers.TrainHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

@SuppressLint("MissingInflatedId")
public class MahjongTrainingActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "Gestures";
    private static final String STOPWATCH_FRAGMENT_TAG = "stopwatch_fragment_tag";
    private ArrayList<ImageButton> flippedTiles;
    private ArrayList<Integer> flippedTilesNum;
    private int removedTilesCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Отображение обратного отсчёта перед тренировкой
        setContentView(R.layout.pretrain_countdown_layout);
        // Загрузка данных из сохранённых настроек
        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int mahjongRememberTime = sharedPreferences.getInt(getString(R.string.saved_mahjong_remember_time_key), 2);
        int mahjongTilesAmount = PrepHelper.Mahjong.sgBtnGroupTilesPosToAmount(
                sharedPreferences.getInt(getString(R.string.saved_mahjong_tiles_amount_key), 0));
        int mahjongEqualTilesAmount = PrepHelper.Mahjong.sgBtnGroupEqualTilesPosToAmount(
                sharedPreferences.getInt(getString(R.string.saved_mahjong_equal_tiles_amount_key), 0));
        TextView tv_countdown = findViewById(R.id.tv_countdown);
        CountDownTimer countDownTimer = new CountDownTimer(3000 + 200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(DEBUG_TAG, "onTick: millisUntilFinished = " + millisUntilFinished);
                tv_countdown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                if(mahjongTilesAmount == 12){
                    setContentView(R.layout.activity_mahjong_training_12);
                }
                else{
                    setContentView(R.layout.activity_mahjong_training_24);
                }
                Log.d(DEBUG_TAG, "saved mahjongRememberTime = " + mahjongRememberTime +
                        ", mahjongTilesAmount = " + mahjongTilesAmount +
                        ", mahjongEqualTilesAmount = " + mahjongEqualTilesAmount);
                // Формирование матрицы (или массива) костей с загруженными параметрами
                ArrayList<Integer> tilesNum = TrainHelper.Mahjong.generateTiles(mahjongTilesAmount, mahjongEqualTilesAmount);
                Log.d(DEBUG_TAG, tilesNum.toString());
                // Заполнение массива кнопок-костей из массива номеров костей, а также массива background
                ConstraintLayout constraintLayout = findViewById(R.id.mahjong_cnstrnt_layout_chld);
                Guideline guidelineTop = findViewById(R.id.guideline_top);
                Guideline guidelineBottom = findViewById(R.id.guideline_bottom);
                Guideline guidelineLeft = findViewById(R.id.guideline_left);
                Guideline guidelineRight = findViewById(R.id.guideline_right);
                setGuidelines(mahjongTilesAmount, guidelineTop, guidelineBottom, guidelineLeft, guidelineRight);
                ArrayList<ImageButton> btnTiles = new ArrayList<>();
                for(int i = 0; i < constraintLayout.getChildCount(); ++i){
                    btnTiles.add((ImageButton) constraintLayout.getChildAt(i));
                }
                ArrayList<Integer> btnBackgroundResources = tilesNumToBackgroundRes(tilesNum);
                for (int i = 0; i < btnBackgroundResources.size(); ++i){
                    btnTiles.get(i).setImageResource(btnBackgroundResources.get(i));
                }
                CountDownTimer showCountdownTimer = new CountDownTimer(mahjongRememberTime * 1000 + 200, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {}
                    @Override
                    public void onFinish() {
                        // Запуск секундомера
                        Bundle bundle = new Bundle();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .add(R.id.frgt_view, StopwatchFragment.class, bundle, STOPWATCH_FRAGMENT_TAG)
                                .commit();
                        // В таймере: смена background кнопок на tile_back
                        for (int i = 0; i < btnBackgroundResources.size(); ++i){
                            btnTiles.get(i).setImageResource(R.drawable.tile_back);
                        }
                        flippedTiles = new ArrayList<>();
                        flippedTilesNum = new ArrayList<>();
                        // Обработка нажатий кнопок
                        for (int i = 0; i < btnBackgroundResources.size(); ++i){
                            int finalI = i;
                            btnTiles.get(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    btnTiles.get(finalI).setImageResource(btnBackgroundResources.get(finalI));
                                    v.setClickable(false);
                                    int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_info_elev);
                                    v.setElevation(elevPx);
                                    if (flippedTiles.size() < mahjongEqualTilesAmount - 1){
                                        flippedTiles.add((ImageButton) v);
                                        flippedTilesNum.add(tilesNum.get(finalI));
                                    }
                                    else{
                                        for (ImageButton btnTile : btnTiles){
                                            btnTile.setClickable(false);
                                        }
                                        flippedTiles.add((ImageButton) v);
                                        flippedTilesNum.add(tilesNum.get(finalI));
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                if(isAllTilesEqual()){
                                                    // Hide mahjongEqualTilesAmount chosen tiles
                                                    Log.d(DEBUG_TAG, "YOU CHOSE CORRECT!!!");
                                                    for(ImageButton flippedBtnTile : flippedTiles){
                                                        flippedBtnTile.setClickable(false);
                                                        flippedBtnTile.setVisibility(View.INVISIBLE);
                                                        ++removedTilesCount;
                                                    }
                                                    if (removedTilesCount == mahjongTilesAmount){
                                                        // TODO: Here launch dialog with results
                                                        Log.d(DEBUG_TAG, "ALL TILES REMOVED");


                                                    }

                                                }
                                                else{
                                                    // TODO: Here add 1 to mistake count
                                                    Log.d(DEBUG_TAG, "YOU CHOSE WRONG!!!");
                                                    for(ImageButton flippedBtnTile : flippedTiles){
                                                        flippedBtnTile.setImageResource(R.drawable.tile_back);
                                                        flippedBtnTile.setElevation(0);

                                                    }
                                                    Log.d(DEBUG_TAG, "btnTiles.size = " + btnTiles.size());
                                                }
                                                flippedTiles.clear();
                                                flippedTilesNum.clear();

                                                // Set background of mahjongEqualTilesAmount chosen tiles to tile_back
                                                for (ImageButton btnTile : btnTiles){
                                                    btnTile.setClickable(true);
                                                }
                                            }
                                        }, 800);

                                    }
                                }
                            });
                        }

                    }
                }.start();

            }
        }.start();


    }

    public void fillLayoutWithTiles(){}

    private void runPretrainCountdown(TextView tv_countdown) {

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                tv_countdown.setText("3");
                handler.postDelayed(this, 1000);
                tv_countdown.setText("2");
                handler.postDelayed(this, 1000);
                tv_countdown.setText("1");
                handler.postDelayed(this, 1000);
            }
        });
    }

    private ArrayList<Integer> tilesNumToBackgroundRes(ArrayList<Integer> tilesNum){
        ArrayList<Integer> tilesBackgroundResources = new ArrayList<>();
        for (Integer tileNum : tilesNum){
            switch(tileNum){
                case (1):
                    tilesBackgroundResources.add(R.drawable.tile_1);
                    break;
                case (2):
                    tilesBackgroundResources.add(R.drawable.tile_2);
                    break;
                case (3):
                    tilesBackgroundResources.add(R.drawable.tile_3);
                    break;
                case (4):
                    tilesBackgroundResources.add(R.drawable.tile_4);
                    break;
                case (5):
                    tilesBackgroundResources.add(R.drawable.tile_5);
                    break;
                case (6):
                    tilesBackgroundResources.add(R.drawable.tile_6);
                    break;
                case (7):
                    tilesBackgroundResources.add(R.drawable.tile_7);
                    break;
                case (8):
                    tilesBackgroundResources.add(R.drawable.tile_8);
                    break;
                case (9):
                    tilesBackgroundResources.add(R.drawable.tile_9);
                    break;
                case (10):
                    tilesBackgroundResources.add(R.drawable.tile_10);
                    break;
                case (11):
                    tilesBackgroundResources.add(R.drawable.tile_11);
                    break;
                case (12):
                    tilesBackgroundResources.add(R.drawable.tile_12);
                    break;
                case (13):
                    tilesBackgroundResources.add(R.drawable.tile_13);
                    break;
            }
        }
        return tilesBackgroundResources;
    }

    private boolean isAllTilesEqual(){
        boolean correctAnswer = true;
        for(int i = 0; i < flippedTilesNum.size() - 1; ++i){
            if (flippedTilesNum.get(i).intValue() != flippedTilesNum.get(i + 1).intValue()){
                correctAnswer = false;
                break;
            }
        }
        return correctAnswer;
    }

    private void setGuidelines(int mahjongTilesAmount,
                               Guideline guidelineTop, Guideline guidelineBottom,
                               Guideline guidelineLeft, Guideline guidelineRight){
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        Log.d(DEBUG_TAG, "displayResolution = " + width + "x" + height);
        if (width <= 900){ // For small phones
            Log.d(DEBUG_TAG, "IT'S A SMALL PHONE");
            if(mahjongTilesAmount == 12){
                // Set big vert and small horiz indents
                guidelineTop.setGuidelinePercent(
                        Float.parseFloat(getResources().getString(R.string.m_training_big_top_gl)));
                guidelineBottom.setGuidelinePercent(
                        1.0f - Float.parseFloat(getResources().getString(R.string.m_training_big_top_gl)));
                guidelineLeft.setGuidelinePercent(
                        Float.parseFloat(getResources().getString(R.string.m_training_small_left_gl)));
                guidelineRight.setGuidelinePercent(
                        1.0f - Float.parseFloat(getResources().getString(R.string.m_training_small_left_gl)));
            }
            else{
                // Set small vert and small horiz indents
                guidelineTop.setGuidelinePercent(
                        Float.parseFloat(getResources().getString(R.string.m_training_small_top_gl)));
                guidelineBottom.setGuidelinePercent(
                        Float.parseFloat(getResources().getString(R.string.m_training_small_bottom_gl)));
                guidelineLeft.setGuidelinePercent(
                        Float.parseFloat(getResources().getString(R.string.m_training_small_left_gl)));
                guidelineRight.setGuidelinePercent(
                        1.0f - Float.parseFloat(getResources().getString(R.string.m_training_small_left_gl)));
            }
        }
        else{ // For big phones
            Log.d(DEBUG_TAG, "IT'S A BIG PHONE");
            if(mahjongTilesAmount == 12){
                // Set big vert and small horiz indents
                guidelineTop.setGuidelinePercent(
                        Float.parseFloat(getResources().getString(R.string.m_training_big_top_gl)));
                guidelineBottom.setGuidelinePercent(
                        1.0f - Float.parseFloat(getResources().getString(R.string.m_training_big_top_gl)));
                guidelineLeft.setGuidelinePercent(
                        Float.parseFloat(getResources().getString(R.string.m_training_small_left_gl)));
                guidelineRight.setGuidelinePercent(
                        1.0f - Float.parseFloat(getResources().getString(R.string.m_training_small_left_gl)));
            }
            else{
                // Set small vert and small horiz indents
                guidelineTop.setGuidelinePercent(
                        Float.parseFloat(getResources().getString(R.string.m_training_small_top_gl)));
                guidelineBottom.setGuidelinePercent(
                        Float.parseFloat(getResources().getString(R.string.m_training_big_bottom_gl)));
                guidelineLeft.setGuidelinePercent(
                        Float.parseFloat(getResources().getString(R.string.m_training_small_left_gl)));
                guidelineRight.setGuidelinePercent(
                        1.0f - Float.parseFloat(getResources().getString(R.string.m_training_small_left_gl)));
            }
        }
    }
}