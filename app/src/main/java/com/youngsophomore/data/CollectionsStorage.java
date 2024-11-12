package com.youngsophomore.data;
/*
* Класс оборачивает операции записи коллекций в текстовые файлы
* */

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.ArrayMap;
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
    private static final String DEBUG_TAG = "Gestures";


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
        // add title to collection in shared preferences
        String strPhrasesCollectionsTitles =
                sharedPreferences.getString(phrasesCollectionsTitlesKey, "");
        strPhrasesCollectionsTitles += title + ",";
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(phrasesCollectionsTitlesKey, strPhrasesCollectionsTitles);
        editor.apply();
        // write newCollection in title.txt
        try {
            File filePath = new File(context.getExternalFilesDir(null).getAbsolutePath() + "/phrases");
            
            String fileName = "/" + title + ".txt";
            File outFile = new File(filePath, fileName);
            FileOutputStream fos = new FileOutputStream(outFile);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            
            for(String phrase : newCollection){
                osw.write(phrase);
                osw.write("|");
            }
            osw.flush();
            osw.close();
            fos.close();
        }
        catch (IOException e) {
            
        }
    }

    public static void deletePhrasesCollection(String title,
                                               String phrasesCollectionsTitlesKey,
                                               String savedPhrasesCollectionPositionKey,
                                               SharedPreferences sharedPreferences,
                                               Context context){
        // delete title from strPhrasesCollectionsTitles in SharedPreferences
        String strPhrasesCollectionsTitles = sharedPreferences.getString(phrasesCollectionsTitlesKey, "");
        strPhrasesCollectionsTitles = strPhrasesCollectionsTitles.replace(title + ",", "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(phrasesCollectionsTitlesKey, strPhrasesCollectionsTitles);
        editor.putInt(savedPhrasesCollectionPositionKey, 0);
        editor.apply();
        try {
            File filePath = new File(context.getExternalFilesDir(null).getAbsolutePath() + "/phrases");
            
            String fileName = "/" + title + ".txt";
            File deleteFile = new File(filePath, fileName);
            if(deleteFile.delete()){
                
            }
            else{
                
            }
        }
        catch (NullPointerException e) {
            
        }
    }

    public static ArrayList<String> getPhrasesCollection(
            String title,
            String pathName){
        ArrayList<String> phrasesCollection = new ArrayList<>();
        try {
            File filePath = new File(pathName);
            
            String fileName = "/" + title + ".txt";
            File inFile = new File(filePath, fileName);
            
            Scanner scanner = new Scanner(inFile);
            scanner.useDelimiter("\\|");
            while (scanner.hasNext()){
                phrasesCollection.add(scanner.next());
            }
        }
        catch (IOException e) {
            
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
        // add a folder named "title"
        // write answers to questionNNN in title/questionNNN.txt
        try {
            File questionsDir = new File(context.getExternalFilesDir(null).getAbsolutePath()
                    + "/details" + "/" + title);
            if (!questionsDir.exists() && questionsDir.mkdir()) {
                
            }
            
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
                String fileName = "/" + questionNum + ".txt";
                File outFile = new File(questionsDir, fileName);

                FileOutputStream fos = new FileOutputStream(outFile);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                
                osw.write(newCollection.get(i).getQuestionText());
                osw.write("\n");
                osw.write(newCollection.get(i).getAnswersInOneString(false));
                osw.flush();
                osw.close();
                fos.close();
            }


        }
        catch (IOException e) {
            
        }
    }

    public static ArrayList<Question> getQuestionsCollection(String questionDir){
        File questionsDir = new File(questionDir);
        File[] fullQuestionsFiles = questionsDir.listFiles();
        int questionsAmount = Math.min(fullQuestionsFiles.length, 10);
        ArrayList<Integer> questionNums = TrainHelper.getRandomNumsInRange(questionsAmount, 0, fullQuestionsFiles.length);
        ArrayList<File> questionsFiles = new ArrayList<>();
        for (int i = 0; i < 10 && i < fullQuestionsFiles.length; ++i){
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
        File questionsDir = new File(context.getExternalFilesDir(null).getAbsolutePath()
                + "/details" + "/" + title);
        if (questionsDir.isDirectory())
            for (File child : questionsDir.listFiles())
                child.delete();

        questionsDir.delete();
        
    }

}
