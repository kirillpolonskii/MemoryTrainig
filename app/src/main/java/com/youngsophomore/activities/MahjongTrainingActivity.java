package com.youngsophomore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.youngsophomore.R;
import com.youngsophomore.helpers.PrepHelper;

public class MahjongTrainingActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "Gestures";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahjong_training);
        /* Действия
        * ~~Загрузка данных из сохранённых настроек~~
        * Формирование массива костей
        * Показ секундомера
        * Заполнение GridLayout
        * Показ всех костей n секунд
        * Запуск секундомера
        * Обработка нажатий на кости и анимация
        * Обработка паузы/пуска
        * Анимация исчезновения костей
        * Показ диалогового окна или смена фрагмента с результатами тренировки
        *
        * */

        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int mahjongRememberTime = sharedPreferences.getInt(getString(R.string.saved_mahjong_remember_time_key), 2);
        int mahjongBonesAmount = PrepHelper.Mahjong.sgBtnGroupBonesPosToAmount(
                sharedPreferences.getInt(getString(R.string.saved_mahjong_bones_amount_key), 0));
        int mahjongEqualBonesAmount = PrepHelper.Mahjong.sgBtnGroupEqualBonesPosToAmount(
                sharedPreferences.getInt(getString(R.string.saved_mahjong_equal_bones_amount_key), 0));
        Log.d(DEBUG_TAG, "saved mahjongRememberTime = " + mahjongRememberTime +
                ", mahjongBonesAmount = " + mahjongBonesAmount +
                ", mahjongEqualBonesAmount = " + mahjongEqualBonesAmount);
    }
}