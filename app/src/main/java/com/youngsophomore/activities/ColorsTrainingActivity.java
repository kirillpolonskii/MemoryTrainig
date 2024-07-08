package com.youngsophomore.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.youngsophomore.R;
import com.youngsophomore.fragments.StopwatchFragment;
import com.youngsophomore.helpers.TrainHelper;

import java.util.ArrayList;

public class ColorsTrainingActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "Gestures";
    private static final String STOPWATCH_FRAGMENT_TAG = "stopwatch_fragment_tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Отображение обратного отсчёта перед тренировкой
        setContentView(R.layout.pretrain_countdown_layout);
        // Загрузка данных из сохранённых настроек

        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int colorsAmount = sharedPreferences.getInt(getString(R.string.saved_colors_amount_key), 4);
        int distinctColorsAmount = sharedPreferences.getInt(getString(R.string.saved_distinct_colors_amount_key), 2);
        int colorShowTime = sharedPreferences.getInt(getString(R.string.saved_color_show_time_key), 2);

        TextView tv_countdown = findViewById(R.id.tv_countdown);
        CountDownTimer countDownTimer = new CountDownTimer(3000 + 200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(DEBUG_TAG, "onTick: millisUntilFinished = " + millisUntilFinished);
                tv_countdown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                // Запуск секундомера
                Bundle bundle = new Bundle();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.frgt_view, StopwatchFragment.class, bundle, STOPWATCH_FRAGMENT_TAG)
                        .commit();

                ArrayList<Integer> palette = TrainHelper.Colors.generatePalette(distinctColorsAmount);
                ArrayList<Integer> colorSeq = TrainHelper.Colors.generateColors(colorsAmount, palette);

                setContentView(R.layout.activity_colors_training);
                ImageView imageView = findViewById(R.id.iv_color_pos1);
                imageView.setBackgroundColor(getResources().getColor(R.color.purple_200));
                /* Действия
                 * ~~Загрузка данных из сохранённых настроек~~
                 * ~~Формирование массива костей~~
                 * ~~Установка margin у секундомера и дна constraintlayout в зависимости от количества костей~~
                 * ~~Показ секундомера~~
                 * ~~Заполнение background у костей~~
                 * ~~Показ всех костей mahjongRememberTime секунд~~
                 * ~~Запуск секундомера~~
                 * ~~Обработка нажатий на кости~~
                 * ~~Обработка паузы/возобновления~~
                 * Показ диалогового окна или смена фрагмента с результатами тренировки
                 *
                 * */
                Log.d(DEBUG_TAG, "saved colorsAmount = " + colorsAmount +
                        ", distinctColorsAmount = " + distinctColorsAmount +
                        ", colorShowTime = " + colorShowTime);
                // Формирование матрицы (или массива) костей с загруженными параметрами
                // Заполнение массива кнопок-костей из массива номеров костей, а также массива background

                // Запуск ещё одного таймера длительностью mahjongRememberTime


            }
        }.start();



    }
}