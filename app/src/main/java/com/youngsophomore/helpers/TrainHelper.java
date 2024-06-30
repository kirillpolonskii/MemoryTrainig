package com.youngsophomore.helpers;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class TrainHelper {
    private static final String DEBUG_TAG = "Gestures";

    public class Mahjong{
        public static ArrayList<Integer> generateTiles(int mahjongBonesAmount, int mahjongEqualBonesAmount){
            ArrayList<Integer> tiles = new ArrayList<>();
            for(int i = 0; i < mahjongBonesAmount; ++i){
                tiles.add(0);
            }
            TreeSet<Integer> usedTiles = new TreeSet<>();
            for (int i = 0; i < mahjongBonesAmount / mahjongEqualBonesAmount; ++i){
                int tileNum = ThreadLocalRandom.current().nextInt(1, 13 + 1);
                while (usedTiles.contains(tileNum)){
                    tileNum = ThreadLocalRandom.current().nextInt(1, 13 + 1);
                }
                Log.d(DEBUG_TAG, "tileNum = " + tileNum);
                usedTiles.add(tileNum);
                if (i < (mahjongBonesAmount / mahjongEqualBonesAmount) * 10 / 15){
                    Log.d(DEBUG_TAG, "in if branch");
                    int filledPositions = 0;
                    while(filledPositions < mahjongEqualBonesAmount){
                        int newPosition = ThreadLocalRandom.current().nextInt(0, mahjongBonesAmount);
                        if(tiles.get(newPosition) == 0){
                            int prev = tiles.set(newPosition, tileNum);
                            Log.d(DEBUG_TAG, "prev elem = " + prev);
                            ++filledPositions;
                        }
                    }
                }
                else{
                    Log.d(DEBUG_TAG, "in else branch");
                    int filledPositions = 0;
                    for(int j = 0; j < mahjongBonesAmount && filledPositions < mahjongEqualBonesAmount; ++j){
                        if(tiles.get(j) == 0){
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
}
