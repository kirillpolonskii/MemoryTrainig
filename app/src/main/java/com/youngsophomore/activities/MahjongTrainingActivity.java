package com.youngsophomore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
    private boolean isFirstFlipped, isSecondFlipped;
    private ArrayList<ImageButton> flippedTiles;
    private ArrayList<Integer> flippedTilesNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Отображение обратного отсчёта перед тренировкой
        setContentView(R.layout.pretrain_countdown_layout);
        // Загрузка данных из сохранённых настроек
        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int mahjongRememberTime = sharedPreferences.getInt(getString(R.string.saved_mahjong_remember_time_key), 2);
        int mahjongBonesAmount = PrepHelper.Mahjong.sgBtnGroupBonesPosToAmount(
                sharedPreferences.getInt(getString(R.string.saved_mahjong_bones_amount_key), 0));
        int mahjongEqualBonesAmount = PrepHelper.Mahjong.sgBtnGroupEqualBonesPosToAmount(
                sharedPreferences.getInt(getString(R.string.saved_mahjong_equal_bones_amount_key), 0));
        TextView tv_countdown = findViewById(R.id.tv_countdown);
        CountDownTimer countDownTimer = new CountDownTimer(3000 + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(DEBUG_TAG, "onTick: millisUntilFinished = " + millisUntilFinished);
                tv_countdown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                if(mahjongBonesAmount == 12){
                    setContentView(R.layout.activity_mahjong_training_12);
                }
                else{
                    setContentView(R.layout.activity_mahjong_training_24);
                }
                /* Действия
                 * ~~Загрузка данных из сохранённых настроек~~
                 * ~~Формирование массива костей~~
                 * Установка margin у секундомера и дна constraintlayout в зависимости от количества костей
                 * ~~Показ секундомера~~
                 * ~~Заполнение background у костей~~
                 * ~~Показ всех костей mahjongRememberTime секунд~~
                 * ~~Запуск секундомера~~
                 * Обработка нажатий на кости и анимация
                 * ~~Обработка паузы/возобновления~~
                 * Анимация исчезновения костей
                 * Показ диалогового окна или смена фрагмента с результатами тренировки
                 *
                 * */
                Log.d(DEBUG_TAG, "saved mahjongRememberTime = " + mahjongRememberTime +
                        ", mahjongBonesAmount = " + mahjongBonesAmount +
                        ", mahjongEqualBonesAmount = " + mahjongEqualBonesAmount);
                // Формирование матрицы (или массива) костей с загруженными параметрами
                ArrayList<Integer> tilesNum = TrainHelper.Mahjong.generateTiles(mahjongBonesAmount, mahjongEqualBonesAmount);
                Log.d(DEBUG_TAG, tilesNum.toString());
                // Заполнение массива кнопок-костей из массива номеров костей, а также массива background
                ConstraintLayout constraintLayout = findViewById(R.id.mahjong_cnstrnt_layout);
                setIndents(mahjongBonesAmount, constraintLayout);
                //ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) constraintLayout.getLayoutParams();
                //constraintLayout.setPadding();
                ArrayList<ImageButton> btnTiles = new ArrayList<>();
                for(int i = 0; i < constraintLayout.getChildCount(); ++i){
                    btnTiles.add((ImageButton) constraintLayout.getChildAt(i));
                }
                ArrayList<Integer> btnBackgroundResources = tilesNumToBackgroundRes(tilesNum);
                for (int i = 0; i < btnBackgroundResources.size(); ++i){
                    btnTiles.get(i).setImageResource(btnBackgroundResources.get(i));
                }
                // Запуск ещё одного таймера длительностью mahjongRememberTime
                CountDownTimer showCountdownTimer = new CountDownTimer(mahjongRememberTime * 1000 + 100, 1000) {
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
                                    if (flippedTiles.size() < mahjongEqualBonesAmount - 1){
                                        flippedTiles.add((ImageButton) v);
                                        flippedTilesNum.add(tilesNum.get(finalI));
                                    }
                                    else{
                                        for (ImageButton btnTile : btnTiles){
                                            btnTile.setClickable(false);
                                        }
                                        flippedTiles.add((ImageButton) v);
                                        flippedTilesNum.add(tilesNum.get(finalI));
                                        if(isAllTilesEqual()){
                                            // Hide two chosen tiles
                                            Log.d(DEBUG_TAG, "YOU CHOSE CORRECT!!!");
                                        }
                                        else{
                                            // Set background of two chosen tiles to tile_back
                                            Log.d(DEBUG_TAG, "YOU CHOSE WRONG!!!");
                                        }
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                for(ImageButton flippedBtnTile : flippedTiles){
                                                    flippedBtnTile.setImageResource(R.drawable.tile_back);
                                                    flippedBtnTile.setElevation(0);
                                                }
                                                flippedTiles.clear();
                                                flippedTilesNum.clear();
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

    private void setIndents(int mahjongBonesAmount, ConstraintLayout constraintLayout){
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        Log.d(DEBUG_TAG, "displayResolution = " + width + "x" + height);
        int horizontalIndent = 0, verticalIndent = 0;
        boolean isTablet = (width > height);
        if(isTablet){
            Log.d(DEBUG_TAG, "IT'S A TABLET");
            // Set big horiz and small vert indents
            horizontalIndent = getResources().getDimensionPixelSize(R.dimen.m_training_big_horiz_margin);
            verticalIndent = getResources().getDimensionPixelSize(R.dimen.m_training_small_vertical_margin);
        }
        else{
            if (width <= 900){ // For small phones
                Log.d(DEBUG_TAG, "IT'S A SMALL PHONE");
                if(mahjongBonesAmount == 12){
                    // Set small horiz and big vert indents
                    horizontalIndent = getResources().getDimensionPixelSize(R.dimen.m_training_small_horiz_margin);
                    verticalIndent = getResources().getDimensionPixelSize(R.dimen.m_training_small_vertical_margin);
                }
                else{
                    // Set small horiz and small vert indents
                    horizontalIndent = getResources().getDimensionPixelSize(R.dimen.m_training_small_horiz_margin);
                    verticalIndent = getResources().getDimensionPixelSize(R.dimen.m_training_small_vertical_margin);
                }
            }
            else{ // For big phones
                Log.d(DEBUG_TAG, "IT'S A BIG PHONE");
                if(mahjongBonesAmount == 12){
                    // Set small horiz and big vert indents
                    horizontalIndent = getResources().getDimensionPixelSize(R.dimen.m_training_small_horiz_margin);
                    verticalIndent = getResources().getDimensionPixelSize(R.dimen.m_training_big_vertical_margin);
                }
                else{
                    // Set small horiz and small vert indents
                    horizontalIndent = getResources().getDimensionPixelSize(R.dimen.m_training_small_horiz_margin);
                    verticalIndent = getResources().getDimensionPixelSize(R.dimen.m_training_small_vertical_margin);
                }
            }
        }
        constraintLayout.setPaddingRelative(horizontalIndent, verticalIndent, horizontalIndent, verticalIndent);
    }
}