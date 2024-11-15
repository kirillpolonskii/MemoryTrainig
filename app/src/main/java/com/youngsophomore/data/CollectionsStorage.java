package com.youngsophomore.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.youngsophomore.helpers.TrainHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CollectionsStorage {
    public static final String DEBUG_TAG = "DEBUG_TAG";
    public static ArrayList<String> getCollectionsTitles(SharedPreferences sharedPreferences, String key){
        String strWordsCollectionsTitles = sharedPreferences.getString(key, "");
        String[] splittedWordsCollectionsTitles = strWordsCollectionsTitles.split(",");
        ArrayList<String> newCollectionArray = new ArrayList<>(Arrays.asList(splittedWordsCollectionsTitles));
        newCollectionArray.remove(0);
        return newCollectionArray;
    }

    public static void saveWordsCollection(
            String title,
            String newCollection,
            String strWordsCollectionsTitles,
            SharedPreferences sharedPreferences,
            String wordsCollectionsTitlesKey){
        while (newCollection.charAt(0) == ' '){
            newCollection = newCollection.substring(1);
        }
        while(newCollection.contains("  ")){
            newCollection = newCollection.replace("  ", " ");
        }
        strWordsCollectionsTitles += title + ",";
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(wordsCollectionsTitlesKey, strWordsCollectionsTitles);
        editor.putString(title, newCollection);

        editor.apply();
    }

    public static void deleteWordsCollection(String title,
                                             String wordsCollectionsTitlesKey,
                                             String savedWordsCollectionPositionKey,
                                             SharedPreferences sharedPreferences){
        // delete title from strWordsCollectionsTitles in SharedPreferences
        String strWordsCollectionsTitles = sharedPreferences.getString(wordsCollectionsTitlesKey, "");
        strWordsCollectionsTitles = strWordsCollectionsTitles.replace(title + ",", "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(wordsCollectionsTitlesKey, strWordsCollectionsTitles);
        // delete words collection
        editor.remove(title);
        editor.putInt(savedWordsCollectionPositionKey, 0);
        editor.apply();

    }

    public static void savePhrasesCollection(String title,
                                             ArrayList<String> newCollection,
                                             String phrasesCollectionsTitlesKey,
                                             SharedPreferences sharedPreferences,
                                             Context context){
        // Add title to collection in shared preferences
        String strPhrasesCollectionsTitles =
                sharedPreferences.getString(phrasesCollectionsTitlesKey, "");
        strPhrasesCollectionsTitles += title + ",";
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(phrasesCollectionsTitlesKey, strPhrasesCollectionsTitles);
        editor.apply();
        try {
            File phrasesDir = new File(context.getFilesDir(), "phrases");
            Log.d(DEBUG_TAG, "phrasesDir was created = " + phrasesDir.mkdirs());
            String fileName = title + ".txt";
            File outFile = new File(phrasesDir, fileName);
            FileOutputStream fos = new FileOutputStream(outFile);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            for(String phrase : newCollection){
                osw.write(phrase);
                osw.write("|");
            }
            osw.close();
            fos.close();
        }
        catch (IOException e) {
            String err = (e.getMessage() == null) ? "saving phrases failed" : e.getMessage();
            Log.d(DEBUG_TAG, err);
        }
    }

    public static void deletePhrasesCollection(String title,
                                               String phrasesCollectionsTitlesKey,
                                               String savedPhrasesCollectionPositionKey,
                                               SharedPreferences sharedPreferences,
                                               Context context){
        // Delete title from strPhrasesCollectionsTitles in SharedPreferences
        String strPhrasesCollectionsTitles = sharedPreferences.getString(phrasesCollectionsTitlesKey, "");
        strPhrasesCollectionsTitles = strPhrasesCollectionsTitles.replace(title + ",", "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(phrasesCollectionsTitlesKey, strPhrasesCollectionsTitles);
        editor.putInt(savedPhrasesCollectionPositionKey, 0);
        editor.apply();
        try {
            File phrasesDir = new File(context.getFilesDir(), "phrases");
            String fileName = title + ".txt";
            File outFile = new File(phrasesDir, fileName);
            outFile.delete();
        }
        catch (NullPointerException e) {
            String err = (e.getMessage() == null) ? "deleting phrases failed" : e.getMessage();
            Log.d(DEBUG_TAG, err);
        }
    }

    public static ArrayList<String> getPhrasesCollection(
            String title,
            Context context){
        ArrayList<String> phrasesCollection = new ArrayList<>();
        try {
            File phrasesDir = new File(context.getFilesDir(), "phrases");
            String fileName = title + ".txt";
            File inFile = new File(phrasesDir, fileName);
            
            Scanner scanner = new Scanner(inFile);
            scanner.useDelimiter("\\|");
            while (scanner.hasNext()){
                phrasesCollection.add(scanner.next());
            }
        }
        catch (IOException e) {
            String err = (e.getMessage() == null) ? "reading phrases failed" : e.getMessage();
            Log.d(DEBUG_TAG, err);
        }
        return phrasesCollection;
    }

    public static void saveQuestionsCollections(String title, Uri imageUri,
                                                ArrayList<Question> newCollection,
                                                String questionsCollectionsTitlesKey,
                                                SharedPreferences sharedPreferences,
                                                Context context){
        // write uri in title/imageUri or in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(title, imageUri.toString());
        // add title to collection in shared preferences
        String strQuestionsCollectionsTitles =
                sharedPreferences.getString(questionsCollectionsTitlesKey, "");
        strQuestionsCollectionsTitles += title + ",";
        editor.putString(questionsCollectionsTitlesKey, strQuestionsCollectionsTitles);
        editor.apply();
        // write answers to questionNNN in title/questionNNN.txt
        try {
            File questionsDir = new File(context.getFilesDir(), "details" + "/" + title);
            Log.d(DEBUG_TAG, "questionsDir was created = " + questionsDir.mkdirs());
            for(int i = 0; i < newCollection.size(); ++i){
                String questionNum = "question";
                if(i < 10){
                    questionNum += "00" + i;
                }
                else if(i < 100){
                    questionNum += "0" + i;
                }
                else{
                    questionNum += String.valueOf(i);
                }
                String fileName = questionNum + ".txt";
                File outFile = new File(questionsDir, fileName);

                FileOutputStream fos = new FileOutputStream(outFile);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                osw.write(newCollection.get(i).getQuestionText());
                osw.write("\n");
                osw.write(newCollection.get(i).getAnswersInOneString(false));
                osw.close();
                fos.close();
            }
        }
        catch (IOException e) {
            String err = (e.getMessage() == null) ? "saving questions failed" : e.getMessage();
            Log.d(DEBUG_TAG, err);
        }
    }

    public static ArrayList<Question> getQuestionsCollection(String title, Context context){
        File questionsDir = new File(context.getFilesDir(),
                "details" + "/" + title);
        File[] fullQuestionsFiles = questionsDir.listFiles();
        int questionsAmount = Math.min(fullQuestionsFiles.length, 10);
        ArrayList<Integer> questionNums = TrainHelper.getRandomNumsInRange(questionsAmount, 0, fullQuestionsFiles.length);
        ArrayList<File> questionsFiles = new ArrayList<>();
        for (int i = 0; i < questionsAmount; ++i){
            questionsFiles.add(fullQuestionsFiles[questionNums.get(i)]);
        }
        ArrayList<Question> questions = new ArrayList<>();
        for (int i = 0; i < questionsFiles.size(); ++i){
            try{
                Scanner scanner = new Scanner(questionsFiles.get(i));
                scanner.useDelimiter("\n");
                String curQuestionText = scanner.next();
                String curAnswersInOneStr = scanner.next();
                Question curQuestion = new Question(curQuestionText, curAnswersInOneStr);
                curQuestion.parseAnswersFromString();

                questions.add(curQuestion);
            }
            catch (Exception e){
                String err = (e.getMessage() == null) ? "reading questions failed" : e.getMessage();
                Log.d(DEBUG_TAG, err);
            }

        }
        return questions;
    }

    public static void deleteQuestionsCollection(String title,
                                                 String questionsCollectionsTitleKey,
                                                 String savedQuestionsCollectionPositionKey,
                                                 SharedPreferences sharedPreferences,
                                                 Context context){
        String strQuestionsCollectionsTitles = sharedPreferences.getString(questionsCollectionsTitleKey, "");
        strQuestionsCollectionsTitles = strQuestionsCollectionsTitles.replace(title + ",", "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(questionsCollectionsTitleKey, strQuestionsCollectionsTitles);
        editor.putInt(savedQuestionsCollectionPositionKey, 0);
        editor.apply();
        File questionsDir = new File(context.getFilesDir(), "details" + "/" + title);
        if (questionsDir.isDirectory())
            for (File child : questionsDir.listFiles())
                child.delete();

        questionsDir.delete();
        
    }

}
