package com.youngsophomore.data;
/*
* Класс оборачивает операции записи коллекций в текстовые файлы
* */

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.ArrayMap;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class CollectionsStorage {
    private static final String DEBUG_TAG = "Gestures";
    private static ArrayList<String> wordsCollectionsTitles;
    private static ArrayList<ArrayList<String>> wordsCollections;
    private static ArrayList<String> phrasesCollectionsTitles;
    private static ArrayList<ArrayList<String>> phrasesCollections;

    private static ArrayList<String> questionsCollectionsTitles; // collection named after image
    private static ArrayList<ArrayList<Question>> questionsCollections;
    private static ArrayMap<String, Uri> imageNamesForUri;


//    static {
//        Log.d(DEBUG_TAG, "in CollectionsStorage: static worked");
//        wordsCollectionsTitles = new ArrayList<>();
//        wordsCollectionsTitles.add("FirstWordsCollection");
//        wordsCollections = new ArrayList<>();
//
//        phrasesCollectionsTitles = new ArrayList<>();
//        phrasesCollectionsTitles.add("FirstPhrasesCollection");
//        phrasesCollections = new ArrayList<>();
//
//        questionsCollectionsTitles = new ArrayList<>();
//        questionsCollectionsTitles.add("Image1");
//        questionsCollections = new ArrayList<>();
//        imageNamesForUri = new ArrayMap<>();
//    }

public static ArrayList<String> getCollectionsTitles(SharedPreferences sharedPreferences, String key){
    // get Set<String> from shared preferences
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
        // add title to Set<String> in shared preferences
        strWordsCollectionsTitles += title + ",";
        Log.d(DEBUG_TAG, "in CollectionStorage: strWordsCollectionsTitles = " + strWordsCollectionsTitles);
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

    public static String getWordsCollectionTitle(int position){
        // get collection from shared preferences and get title
        return wordsCollectionsTitles.get(position);
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
            Log.d(DEBUG_TAG, "in CollectionStorage: filePath = " + filePath);
            String fileName = "/" + title + ".txt";
            File outFile = new File(filePath, fileName);
            if (!outFile.exists() && outFile.createNewFile()) {
                Log.d(DEBUG_TAG, "in CollectionStorage: " + outFile.getAbsolutePath() +
                        " did NOT exist and was created");
            }
            else{
                Log.d(DEBUG_TAG, "in CollectionStorage: " + outFile.getAbsolutePath() +
                        " existed or was NOT created");
            }
            FileOutputStream fos = new FileOutputStream(outFile);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Log.d(DEBUG_TAG, "in CollectionStorage: fileName = " + outFile);
            for(String phrase : newCollection){
                osw.write(phrase);
                osw.write("|");
            }
            osw.flush();
            osw.close();
            fos.close();
        }
        catch (IOException e) {
            Log.d(DEBUG_TAG, "in CollectionStorage: File write failed: " + e.toString());
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
            Log.d(DEBUG_TAG, "in CollectionStorage: filePath = " + filePath);
            String fileName = "/" + title + ".txt";
            File deleteFile = new File(filePath, fileName);
            if(deleteFile.delete()){
                Log.d(DEBUG_TAG, "in CollectionStorage: deleted file successfully = ");
            }
            else{
                Log.d(DEBUG_TAG, "in CollectionStorage: deleted file NOT successfully = ");
            }
        }
        catch (NullPointerException e) {
            Log.d(DEBUG_TAG, "in CollectionStorage: File is null: " + e.toString());
        }
    }

    public static String getPhrasesCollectionTitle(int position){
        // get collection of titles from shared preferences and get the title
        return phrasesCollectionsTitles.get(position);
    }

    public static void saveQuestionsCollections(String title, Uri imageUri,
                                                ArrayList<Question> newCollection,
                                                String questionsCollectionsTitlesKey,
                                                SharedPreferences sharedPreferences,
                                                Context context){
        // add a folder named "title"
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
            File questionsDir = new File(context.getExternalFilesDir(null).getAbsolutePath()
                    + "/details" + "/" + title);
            //File phrasesDir = new File(context.getExternalFilesDir(null).getAbsolutePath() + "/phrases");
            if (!questionsDir.exists() && questionsDir.mkdir()) {
                Log.d(DEBUG_TAG, "in CollectionStorage: " + questionsDir + " created");
            }
            Log.d(DEBUG_TAG, "in CollectionStorage: questionsDir = " + questionsDir);
            for(int i = 0; i < newCollection.size(); ++i){
                String questionNum = "question";
                if(i < 10){
                    questionNum += "00" + String.valueOf(i);
                }
                else if(i < 100){
                    questionNum += "0" + String.valueOf(i);
                }
                else{
                    questionNum += String.valueOf(i);
                }
                String fileName = "/" + questionNum + ".txt";
                File outFile = new File(questionsDir, fileName);
                if (!outFile.exists() && outFile.createNewFile()) {
                    Log.d(DEBUG_TAG, "in CollectionStorage: " + outFile.getAbsolutePath() +
                            " did NOT exist and was created");
                }
                else{
                    Log.d(DEBUG_TAG, "in CollectionStorage: " + outFile.getAbsolutePath() +
                            " existed or was NOT created");
                }
                FileOutputStream fos = new FileOutputStream(outFile);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                Log.d(DEBUG_TAG, "in CollectionStorage: fileName = " + outFile);
                osw.write(newCollection.get(i).getQuestionText());
                osw.write("\n");
                osw.write(newCollection.get(i).getAnswersInOneString(false));
                osw.flush();
                osw.close();
                fos.close();
            }


        }
        catch (IOException e) {
            Log.d(DEBUG_TAG, "in CollectionStorage: File write failed: " + e.toString());
        }
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
        //File phrasesDir = new File(context.getExternalFilesDir(null).getAbsolutePath() + "/phrases");
        if (questionsDir.isDirectory())
            for (File child : questionsDir.listFiles())
                child.delete();

        questionsDir.delete();
        Log.d(DEBUG_TAG, "in CollectionStorage: questionsDir = " + questionsDir);
    }

}
