package com.youngsophomore.helpers;

import android.graphics.Color;
import android.util.Log;

import com.youngsophomore.R;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class TrainHelper {
    private static final String DEBUG_TAG = "Gestures";

    public class Mahjong {
        public static ArrayList<Integer> generateTiles(int mahjongBonesAmount, int mahjongEqualBonesAmount) {
            ArrayList<Integer> tiles = new ArrayList<>();
            for (int i = 0; i < mahjongBonesAmount; ++i) {
                tiles.add(0);
            }
            TreeSet<Integer> usedTiles = new TreeSet<>();
            for (int i = 0; i < mahjongBonesAmount / mahjongEqualBonesAmount; ++i) {
                int tileNum = ThreadLocalRandom.current().nextInt(1, 13 + 1);
                while (usedTiles.contains(tileNum)) {
                    tileNum = ThreadLocalRandom.current().nextInt(1, 13 + 1);
                }
                Log.d(DEBUG_TAG, "tileNum = " + tileNum);
                usedTiles.add(tileNum);
                if (i < (mahjongBonesAmount / mahjongEqualBonesAmount) * 10 / 15) {
                    Log.d(DEBUG_TAG, "in if branch");
                    int filledPositions = 0;
                    while (filledPositions < mahjongEqualBonesAmount) {
                        int newPosition = ThreadLocalRandom.current().nextInt(0, mahjongBonesAmount);
                        if (tiles.get(newPosition) == 0) {
                            int prev = tiles.set(newPosition, tileNum);
                            Log.d(DEBUG_TAG, "prev elem = " + prev);
                            ++filledPositions;
                        }
                    }
                } else {
                    Log.d(DEBUG_TAG, "in else branch");
                    int filledPositions = 0;
                    for (int j = 0; j < mahjongBonesAmount && filledPositions < mahjongEqualBonesAmount; ++j) {
                        if (tiles.get(j) == 0) {
                            tiles.set(j, tileNum);
                            ++filledPositions;
                        }
                    }
                }
            }
            Log.d(DEBUG_TAG, tiles.toString());
            return tiles;
        }
    }
    public class Colors{
        public static ArrayList<Integer> generatePalette(int distinctColorsAmount){
            ArrayList<Integer> palette = new ArrayList<>();
            for(int i = 0; i < distinctColorsAmount; ++i){
                int colorInd = ThreadLocalRandom.current().nextInt(1, 15 + 1);
                int colorRes = indToColor(colorInd);
                while (palette.contains(colorRes)){
                    colorInd = ThreadLocalRandom.current().nextInt(1, 15 + 1);
                    colorRes = indToColor(colorInd);
                }
                palette.add(colorRes);
            }
            return palette;
        }

        public static ArrayList<Integer> generateColors(int colorsAmount, ArrayList<Integer> palette){
            ArrayList<Integer> colors = new ArrayList<>();
            for(int i = 0; i < colorsAmount; ++i){
                int colorInd = ThreadLocalRandom.current().nextInt(0, palette.size());
                colors.add(palette.get(colorInd));
            }
            Log.d(DEBUG_TAG, colors.toString());
            return colors;
        }

        private static int indToColor(int index){
            switch (index){
                case (1):
                    return R.color.c_training_color_1;
                case (2):
                    return R.color.c_training_color_2;
                case (3):
                    return R.color.c_training_color_3;
                case (4):
                    return R.color.c_training_color_4;
                case (5):
                    return R.color.c_training_color_5;
                case (6):
                    return R.color.c_training_color_6;
                case (7):
                    return R.color.c_training_color_7;
                case (8):
                    return R.color.c_training_color_8;
                case (9):
                    return R.color.c_training_color_9;
                case (10):
                    return R.color.c_training_color_10;
                case (11):
                    return R.color.c_training_color_11;
                case (12):
                    return R.color.c_training_color_12;
                case (13):
                    return R.color.c_training_color_13;
                case (14):
                    return R.color.c_training_color_14;
                case (15):
                    return R.color.c_training_color_15;
                default:
                    return R.color.white;
            }
        }
    }
}
