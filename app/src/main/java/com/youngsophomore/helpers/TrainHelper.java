package com.youngsophomore.helpers;

import android.content.SharedPreferences;
import android.util.Log;
import android.util.Pair;

import com.youngsophomore.R;
import com.youngsophomore.data.Question;
import com.youngsophomore.data.StatParam;
import com.youngsophomore.data.Training;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class TrainHelper {
    private static final String DEBUG_TAG = "Gestures";
    public static ArrayList<Integer> getRandomIndicesPerm(int start, int end){ // exclusive
        ArrayList<Integer> randPerm = new ArrayList<>();
        for(int i = 0; i < end - start; ++i){
            randPerm.add(start + i);
        }
        Collections.shuffle(randPerm);
        return randPerm;
    }
    public static ArrayList<Integer> getRandomNumsInRange(int numbersAmount, int start, int end){ // exclusive
        ArrayList<Integer> randNums = new ArrayList<>();
        for (int i = 0; i < numbersAmount; ++i){
            int curNum = ThreadLocalRandom.current().nextInt(start, end);
            if (i == 0){
                randNums.add(curNum);
                continue;
            }
            while (randNums.contains(curNum)){
                curNum = ThreadLocalRandom.current().nextInt(start, end);
            }
            randNums.add(curNum);
        }
        return randNums;
    }

    public static String getStatParamKey(Training training, StatParam statParam, int... definingParams){
        String key = training.name() + "_" + statParam.name();
        for (int i = 0; i < definingParams.length; ++i){
            key += "_" + definingParams[i];
        }
        return key;
    }

    public static String getStatParamKey(Training training, StatParam statParam, String definingParam){
        return training.name() + "_" + statParam.name() + "_" + definingParam;
    }

    public static void updateStatParams(SharedPreferences sharedPreferences,
                                        Pair<String, Integer>... paramsKV){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(int i = 0; i < paramsKV.length; ++i){
            int oldVal = sharedPreferences.getInt(paramsKV[i].first, 0);
            editor.putInt(paramsKV[i].first, oldVal + paramsKV[i].second);
        }
        editor.apply();
    }

    public class Mahjong {
        public static ArrayList<Integer> generateTiles(int mahjongBonesAmount, int mahjongEqualBonesAmount) {
            ArrayList<Integer> tiles = new ArrayList<>();
            for (int i = 0; i < mahjongBonesAmount; ++i) {
                tiles.add(0);
            }
            for(int i = 0; i < mahjongBonesAmount / mahjongEqualBonesAmount; ++i){
                int tileNum = ThreadLocalRandom.current().nextInt(1, 13 + 1);
                while (tiles.contains(tileNum)) {
                    tileNum = ThreadLocalRandom.current().nextInt(1, 13 + 1);
                }
                int firstNotFilledPos = tiles.indexOf(0);
                tiles.set(firstNotFilledPos, tileNum);
                for(int j = 0; j < mahjongEqualBonesAmount - 1; ++j){
                    int newPosition = ThreadLocalRandom.current().nextInt(firstNotFilledPos + 1, mahjongBonesAmount);
                    while (tiles.get(newPosition) != 0) {
                        newPosition = ThreadLocalRandom.current().nextInt(firstNotFilledPos + 1, mahjongBonesAmount);
                    }
                    tiles.set(newPosition, tileNum);
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

    public class Shapes{
        public static ArrayList<Integer> generateShapesSet(int distinctShapesAmount){
            ArrayList<Integer> shapesSet = new ArrayList<>();
            for(int i = 0; i < distinctShapesAmount; ++i){
                int shapeInd = ThreadLocalRandom.current().nextInt(1, 9 + 1);
                int shapeRes = indToShape(shapeInd);
                while (shapesSet.contains(shapeRes)){
                    shapeInd = ThreadLocalRandom.current().nextInt(1, 9 + 1);
                    shapeRes = indToShape(shapeInd);
                }
                shapesSet.add(shapeRes);
            }
            return shapesSet;
        }

        public static ArrayList<Integer> generateShapesSequence(int shapesAmount, ArrayList<Integer> shapesSet){
            ArrayList<Integer> shapesSeq = new ArrayList<>();
            for(int i = 0; i < shapesAmount; ++i){
                int shapeInd = ThreadLocalRandom.current().nextInt(0, shapesSet.size());
                shapesSeq.add(shapesSet.get(shapeInd));
            }
            Log.d(DEBUG_TAG, shapesSet.toString());
            return shapesSeq;
        }

        private static int indToShape(int index){
            switch (index){
                case (1):
                    return R.drawable.shape_1;
                case (2):
                    return R.drawable.shape_2;
                case (3):
                    return R.drawable.shape_3;
                case (4):
                    return R.drawable.shape_4;
                case (5):
                    return R.drawable.shape_5;
                case (6):
                    return R.drawable.shape_6;
                case (7):
                    return R.drawable.shape_7;
                case (8):
                    return R.drawable.shape_8;
                case (9):
                    return R.drawable.shape_9;
                default:
                    return R.drawable.shape_1;
            }
        }
    }

    public class Words{
        public static final int INIT_PAL_SIZE = 9;
        public static ArrayList<String> generateWordsPalette(
                ArrayList<String> wordsCollection,
                int curWordPos,
                int curPaletteSize){
            ArrayList<String> palette = new ArrayList<>();
            for (int i = 0; i < curPaletteSize; ++i){
                palette.add("");
            }
            if(curWordPos + curPaletteSize < wordsCollection.size()){
                int corrWordInd = ThreadLocalRandom.current().nextInt(0, curPaletteSize);
                palette.set(corrWordInd, wordsCollection.get(curWordPos));
                for (int i = 0; i < curPaletteSize; ++i){
                    if (i == corrWordInd) continue;
                    int curWordInd = ThreadLocalRandom.current().nextInt(curWordPos, wordsCollection.size());
                    while (palette.contains(wordsCollection.get(curWordInd))){
                        curWordInd = ThreadLocalRandom.current().nextInt(curWordPos, wordsCollection.size());
                    }
                    palette.set(i, wordsCollection.get(curWordInd));
                }
            }
            else{
                ArrayList<Integer> randIndPerm = new ArrayList<>();
                for (int i = 0; i < curPaletteSize; ++i) randIndPerm.add(curWordPos + i);
                Collections.shuffle(randIndPerm);
                for(int i = 0; i < curPaletteSize; ++i){
                    palette.set(i, wordsCollection.get(randIndPerm.get(i)));
                }
            }

            return palette;
        }
    }

    public class Phrases{
        public static ArrayList<String> generatePhrasesList(ArrayList<String> origCollection, ArrayList<Integer> permut){
            ArrayList<String> phrases = new ArrayList<>();
            for(int i = 0; i < origCollection.size(); ++i){
                phrases.add(origCollection.get(permut.get(i)));
            }
            return phrases;
        }
    }

    public class Details{
        public static ArrayList<Question> parseQuestionsFiles(ArrayList<File> questionsFiles){
            ArrayList<Question> questions = new ArrayList<>();
            for (int i = 0; i < questionsFiles.size(); ++i){
                Log.d(DEBUG_TAG, "File " + questionsFiles.get(i));
                try{
                    Scanner scanner = new Scanner(questionsFiles.get(i));
                    scanner.useDelimiter("\n");
                    String curQuestionText = scanner.next();
                    String curAnswersInOneStr = scanner.next();
                    Question curQuestion = new Question(curQuestionText, curAnswersInOneStr);
                    curQuestion.parseAnswersFromString();
                    Log.d(DEBUG_TAG, curQuestion.getAnswers().toString());
                    questions.add(curQuestion);
                }
                catch (Exception e){
                    Log.d(DEBUG_TAG, "in TrainHelper: " + e.toString());
                }

            }
            return questions;
        }
    }
}
